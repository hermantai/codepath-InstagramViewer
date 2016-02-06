package com.gmail.htaihm.instagramviewer;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Transformation;

public class UiStyle {
    static Transformation createUserProfilePictureTransformation() {
        return new RoundedTransformationBuilder()
                .oval(true)
                .build();
    }

    static CharSequence getCommentWithUsernameStyled(
            Context context, String username, String comment) {
        final StyleSpan boldSs = new StyleSpan(android.graphics.Typeface.BOLD);
        final ForegroundColorSpan darkBlueFcs = new ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.instagram_blue));
        Spannable s = new SpannableString(username + " " + comment);
        s.setSpan(boldSs, 0, username.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        s.setSpan(darkBlueFcs, 0, username.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }

    static CharSequence getLikesCountStyled(Context context, int likesCount) {
        final StyleSpan boldSs = new StyleSpan(android.graphics.Typeface.BOLD);
        final ForegroundColorSpan instagramBlueFcs = new ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.instagram_blue));
        Spannable s = new SpannableString("❤︎ " + likesCount + " likes");
        s.setSpan(boldSs, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        s.setSpan(instagramBlueFcs, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }

    static CharSequence getRelativeTimestampStyled(Context context, long timestampInMillis) {
        final ForegroundColorSpan lightGrayFcs = new ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.light_gray));
        Spannable s = new SpannableString(
                DateUtils.getRelativeTimeSpanString(context, timestampInMillis));
        s.setSpan(lightGrayFcs, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }

    static CharSequence getViewAllCommentsStyled(final Context context, int numComments) {
        Spannable s = new SpannableString("View all " + numComments + " comments");
        return s;
    }

    static CharSequence getViewAllCommentsHoverStyled(final Context context, int numComments) {
        final ForegroundColorSpan lightGrayFcs = new ForegroundColorSpan(
                ContextCompat.getColor(context, R.color.gray));
        Spannable s = new SpannableString("View all " + numComments + " comments");
        s.setSpan(lightGrayFcs, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }
}
