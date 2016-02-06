package com.gmail.htaihm.instagramviewer;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Transformation;

public class UiStyle {
    static Transformation createUserProfilePictureTransformation() {
        return new RoundedTransformationBuilder()
                .oval(true)
                .build();
    }
}
