package com.gmail.htaihm.instagramviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstagramPhotoCommentListFragment extends DialogFragment {
    private static final String ARG_COMMENTS = "mComments";

    public InstagramPhotoCommentListFragment() {
    }

    public static InstagramPhotoCommentListFragment newInstance(
            List<InstagramPhotoComment> comments) {
        InstagramPhotoCommentListFragment frag = new InstagramPhotoCommentListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_COMMENTS, comments.toArray(
                new InstagramPhotoComment[comments.size()]));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comment_list, container, false);

        List<InstagramPhotoComment> comments = Arrays.asList(
                (InstagramPhotoComment[]) getArguments().getSerializable(ARG_COMMENTS));

        RecyclerView rvComments = ButterKnife.findById(v, R.id.rvComments);
        rvComments.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvComments.setAdapter(new CommentsAdapter(comments));

        return v;
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvCommenterUsername)
        TextView mTvCommenterUsername;

        @Bind(R.id.ivCommenterUserProfile)
        ImageView mIvCommenterUserProfile;

        @Bind(R.id.tvCommenterRelativeTimestamp)
        TextView mTvCommenterRelativeTimestamp;

        @Bind(R.id.tvCommenterComment)
        TextView mTvCommenterComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindComment(InstagramPhotoComment comment) {
            mTvCommenterUsername.setText(comment.getUsername());
            mTvCommenterComment.setText(comment.getComment());
            mTvCommenterRelativeTimestamp.setText(
                    DateUtils.getRelativeTimeSpanString(
                            getActivity(), comment.getCreatedTime() * 1000));
            Picasso.with(getActivity())
                    .load(comment.getUserProfilePictureUrl())
                    .fit()
                    .transform(UiStyle.createUserProfilePictureTransformation())
                    .into(mIvCommenterUserProfile);
        }
    }

    private class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {
        private List<InstagramPhotoComment> mComments;

        private CommentsAdapter(List<InstagramPhotoComment> comments) {
            mComments = comments;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CommentViewHolder(
                    inflater.inflate(R.layout.item_comment, parent, false));
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            InstagramPhotoComment comment = mComments.get(position);
            holder.bindComment(comment);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }
    }
}
