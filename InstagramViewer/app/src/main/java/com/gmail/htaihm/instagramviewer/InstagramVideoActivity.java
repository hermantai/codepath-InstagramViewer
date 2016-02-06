package com.gmail.htaihm.instagramviewer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstagramVideoActivity extends AppCompatActivity {
    private final static String INTENT_EXTRA_VIDEO_URL =
            "com.gmail.htaihm.instagramviewer.video_url";

    public final static Intent newIntent(Context context, String videoUrl) {
        Intent i = new Intent(context, InstagramVideoActivity.class);
        i.putExtra(INTENT_EXTRA_VIDEO_URL, videoUrl);
        return i;
    }

    @Bind(R.id.vvInstagramVideo)
    VideoView mVvInstagramVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);

        mVvInstagramVideo.setVideoPath(
            getIntent().getStringExtra(INTENT_EXTRA_VIDEO_URL));

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVvInstagramVideo);
        mVvInstagramVideo.setMediaController(mediaController);

        mVvInstagramVideo.requestFocus();
        mVvInstagramVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVvInstagramVideo.start();
            }
        });
    }
}
