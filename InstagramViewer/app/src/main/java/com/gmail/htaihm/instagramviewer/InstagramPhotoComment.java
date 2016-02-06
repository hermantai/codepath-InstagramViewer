package com.gmail.htaihm.instagramviewer;

public class InstagramPhotoComment {
    private String mUsername;
    private String mUserProfilePictureUrl;
    private String comment;
    // In Unix time seconds
    private long createdTime;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUserProfilePictureUrl() {
        return mUserProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        mUserProfilePictureUrl = userProfilePictureUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
