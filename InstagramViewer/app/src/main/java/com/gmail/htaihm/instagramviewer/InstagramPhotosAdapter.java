package com.gmail.htaihm.instagramviewer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

class InstagramPhotosAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    public interface ViewAllCommentsListener {

        void onViewAllComments(List<InstagramPhotoComment> comments);
    }
    private ViewAllCommentsListener mViewAllCommentsListener;

    private List<InstagramPhoto> mPhotos;
    private Context mContext;
    private LayoutInflater mInflater;
    public void setViewAllCommentsListener(ViewAllCommentsListener listener) {
        mViewAllCommentsListener = listener;
    }

    public InstagramPhotosAdapter(Context context, ArrayList<InstagramPhoto> photos) {
        mContext = context;
        mPhotos = photos;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        InstagramPhoto photo = (InstagramPhoto) getItem(position);

        if (view == null) {
            view = mInflater.inflate(R.layout.item_photo, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bindPhoto(mContext, photo);

        return view;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_photo_header, parent, false);

            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        InstagramPhoto photo = (InstagramPhoto) getItem(position);
        holder.bindPhoto(mContext, photo);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    public void clear() {
        mPhotos.clear();
    }

    class HeaderViewHolder {
        @Bind(R.id.ivUserProfile) ImageView mIvUserProfile;
        @Bind(R.id.tvUsername) TextView mTvUsername;
        @Bind(R.id.tvRelativeTimestamp) TextView mTvRelativeTimestamp;

        private HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bindPhoto(Context context, final InstagramPhoto photo) {
            Picasso.with(context)
                    .load(photo.getUserProfilePictureUrl())
                    .fit()
                    .transform(UiStyle.createUserProfilePictureTransformation())
                    .into(mIvUserProfile);
            mTvUsername.setText(photo.getUsername());
            mTvRelativeTimestamp.setText(
                    UiStyle.getRelativeTimestampStyled(context, photo.getCreatedTime() * 1000));

        }

    }

    class ViewHolder {
        @Bind(R.id.ivPhoto) ImageView mIvPhoto;
        @Bind(R.id.tvCaption) TextView mTvCaption;
        @Bind(R.id.tvLikeCounts) TextView mTvLikeCounts;
        @Bind(R.id.commentsContainer) LinearLayout mCommentsContainer;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bindPhoto(
                final Context context,
                final InstagramPhoto photo) {
            mIvPhoto.setImageResource(0);
            Picasso.with(context)
                    .load(photo.getImageUrl())
                    .placeholder(R.drawable.white_background)
                    .into(mIvPhoto);
            if (photo.getVideoUrl() != null) {
                mIvPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = InstagramVideoActivity.newIntent(context, photo.getVideoUrl());
                        context.startActivity(i);
                    }
                });
            }

            mTvCaption.setText(UiStyle.getCommentWithUsernameStyled(
                    context, photo.getUsername(), photo.getCaption()));
            mTvLikeCounts.setText(UiStyle.getLikesCountStyled(context, photo.getLikesCount()));

            final List<InstagramPhotoComment> comments = photo.getComments();

            mCommentsContainer.removeAllViews();
            if (!comments.isEmpty()) {
                final TextView allCommentsLink = new TextView(context);
                allCommentsLink.setText(
                        "View all " + comments.size() + " comments");
                allCommentsLink.setTextColor(
                        ContextCompat.getColorStateList(
                                context, R.color.color_state_list_view_all_comments));

                allCommentsLink.setOnClickListener(new View.OnClickListener() {
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
                for (int i = 1; i <= limit; i++) {
                    InstagramPhotoComment comment = comments.get(comments.size() - i);
                    TextView commentView = (TextView) inflater.inflate(
                            R.layout.item_brief_comment, null);
                    commentView.setText(
                            UiStyle.getCommentWithUsernameStyled(
                                    context, comment.getUsername(), comment.getComment()));
                    mCommentsContainer.addView(commentView);
                }
            }
        }
    }
}
