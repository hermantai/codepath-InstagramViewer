package com.gmail.htaihm.instagramviewer;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InstagramPhoto {
    private String mUsername;
    private String mCaption = "hello";
    private String mImageUrl;
    private int mImageHeight;
    private int mLikesCount;
    private String mUserProfilePictureUrl;
    // In Unix time seconds
    private long mCreatedTime;
    private String mVideoUrl;
    private List<InstagramPhotoComment> comments = new ArrayList<>();

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.mImageHeight = imageHeight;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        this.mLikesCount = likesCount;
    }

    public String getUserProfilePictureUrl() {
        return mUserProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        mUserProfilePictureUrl = userProfilePictureUrl;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        mCreatedTime = createdTime;
    }

    public void addComment(InstagramPhotoComment comment) {
        comments.add(comment);
    }

    public List<InstagramPhotoComment> getComments() {
        return comments;
    }

    @Nullable
    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return mCaption;
    }
}
