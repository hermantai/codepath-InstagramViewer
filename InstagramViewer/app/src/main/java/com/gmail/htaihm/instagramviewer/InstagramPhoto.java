package com.gmail.htaihm.instagramviewer;

public class InstagramPhoto {
    private String mUsername;
    private String mCaption = "hello";
    private String mImageUrl;
    private int mImageHeight;
    private int mLikesCount;
    private String mUserProfilePictureUrl;
    private long mCreatedTime;

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

    @Override
    public String toString() {
        return mCaption;
    }
}
