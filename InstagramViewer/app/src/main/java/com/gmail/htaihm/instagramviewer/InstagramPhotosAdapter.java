package com.gmail.htaihm.instagramviewer;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public interface ViewAllCommentsListener {
        void onViewAllComments(List<InstagramPhotoComment> comments);
    }

    private ViewAllCommentsListener mViewAllCommentsListener;

    public void setViewAllCommentsListener(ViewAllCommentsListener listener) {
        mViewAllCommentsListener = listener;
    }

    public InstagramPhotosAdapter(Context context, ArrayList<InstagramPhoto> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        InstagramPhoto photo = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bindPhoto(getContext(), photo);

        return view;
    }

    class ViewHolder {
        @Bind(R.id.ivPhoto) ImageView mIvPhoto;
        @Bind(R.id.tvCaption) TextView mTvCaption;
        @Bind(R.id.ivUserProfile) ImageView mIvUserProfile;
        @Bind(R.id.tvUsername) TextView mTvUsername;
        @Bind(R.id.tvRelativeTimestamp) TextView mTvRelativeTimestamp;
        @Bind(R.id.tvLikeCounts) TextView mTvLikeCounts;
        @Bind(R.id.commentsContainer) LinearLayout mCommentsContainer;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bindPhoto(
                Context context,
                InstagramPhoto photo) {
            mTvUsername.setText(photo.getUsername());
            Picasso.with(context)
                    .load(photo.getUserProfilePictureUrl())
                    .fit()
                    .transform(UiStyle.createUserProfilePictureTransformation())
                    .into(mIvUserProfile);
            mTvRelativeTimestamp.setText(
                    DateUtils.getRelativeTimeSpanString(context, photo.getCreatedTime() * 1000));

            mIvPhoto.setImageResource(0);
            Picasso.with(context)
                    .load(photo.getImageUrl())
                    .placeholder(R.drawable.white_background)
                    .into(mIvPhoto);

            mTvCaption.setText(photo.getCaption());
            mTvLikeCounts.setText(String.format("%d likes", photo.getLikesCount()));

            final List<InstagramPhotoComment> comments = photo.getComments();

            mCommentsContainer.removeAllViews();
            if (!comments.isEmpty()) {
                TextView allCommentsLink = new TextView(context);
                allCommentsLink.setText(String.format("View all %d comments", comments.size()));
                allCommentsLink.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (mViewAllCommentsListener != null) {
                            mViewAllCommentsListener.onViewAllComments(comments);
                        }

                    }
                });
                mCommentsContainer.addView(allCommentsLink);

                // Show only the last two comments. Note that comments are sorted by created_time
                int limit = Math.min(2, comments.size());
                LayoutInflater inflater = LayoutInflater.from(context);
                Log.d("TODO", "Have " + comments.size() + " comments for " + photo.getCaption());
                for (int i = 1; i <= limit; i++) {
                    InstagramPhotoComment comment = comments.get(comments.size() - i);
                    TextView commentView = (TextView) inflater.inflate(
                            R.layout.item_brief_comment, null);
                    Log.d("TODO", "comment: " + comment.getComment());

                    commentView.setText(comment.getUsername() + " " + comment.getComment());
                    mCommentsContainer.addView(commentView);
                }
            }
        }
    }
}
