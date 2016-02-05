package com.gmail.htaihm.instagramviewer;

public class InstagramPhotoComment {
    private String mUsername;
    private String mUserProfileUrl;
    private String comment;
    // In Unix time seconds
    private long createdTime;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUserProfileUrl() {
        return mUserProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        mUserProfileUrl = userProfileUrl;
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
