package com.gmail.htaihm.instagramviewer;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
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

    static class ViewHolder {
        @Bind(R.id.ivPhoto) ImageView mIvPhoto;
        @Bind(R.id.tvCaption) TextView mTvCaption;
        @Bind(R.id.ivUserProfile) ImageView mIvUserProfile;
        @Bind(R.id.tvUsername) TextView mTvUsername;
        @Bind(R.id.tvRelativeTimestamp) TextView mTvRelativeTimestamp;
        @Bind(R.id.tvLikeCounts) TextView mTvLikeCounts;

        private ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        private void bindPhoto(Context context, InstagramPhoto photo) {
            mTvUsername.setText(photo.getUsername());
            Picasso.with(context)
                    .load(photo.getUserProfilePictureUrl())
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
        }
    }
}
