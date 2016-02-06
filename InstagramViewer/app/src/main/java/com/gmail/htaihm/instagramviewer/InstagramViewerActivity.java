package com.gmail.htaihm.instagramviewer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class InstagramViewerActivity extends AppCompatActivity
        implements InstagramPhotosAdapter.ViewAllCommentsListener{
    private static final String TAG = "PhotoFetcher";
    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private static final String END_POINT = "https://api.instagram.com/v1/media/popular";

    private ArrayList<InstagramPhoto> mPhotos;
    private InstagramPhotosAdapter mInstagramPhotosAdapter;

    @Bind(R.id.swipeContainer) SwipeRefreshLayout mSwipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_viewer);

        ButterKnife.bind(this);

        mPhotos = new ArrayList<>();
        mInstagramPhotosAdapter = new InstagramPhotosAdapter(this, mPhotos);
        mInstagramPhotosAdapter.setViewAllCommentsListener(this);
        ListView lvItems = ButterKnife.findById(this, R.id.lvItems);
        lvItems.setAdapter(mInstagramPhotosAdapter);

        fetchPopularPhotos();

        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mInstagramPhotosAdapter.clear();
                fetchPopularPhotos();
            }
        });
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchPopularPhotos() {
        Uri uri = Uri.parse(END_POINT)
                .buildUpon()
                .appendQueryParameter("client_id", CLIENT_ID)
                .build();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(uri.toString(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "get: " + response);
                }
                try {
                    JSONArray photosJson = response.getJSONArray("data");

                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.setUsername(photoJson.getJSONObject("user").getString("username"));
                        photo.setUserProfilePictureUrl(
                                photoJson.getJSONObject("user").getString("profile_picture"));

                        JSONObject captionJson = photoJson.optJSONObject("caption");
                        if (captionJson == null) {
                            photo.setCaption("");
                        } else {
                            photo.setCaption(captionJson.getString("text"));
                        }

                        photo.setImageUrl(
                                photoJson
                                        .getJSONObject("images")
                                        .getJSONObject("standard_resolution")
                                        .getString("url"));
                        photo.setImageHeight(
                                photoJson
                                        .getJSONObject("images")
                                        .getJSONObject("standard_resolution")
                                        .getInt("height"));
                        photo.setLikesCount(
                                photoJson.getJSONObject("likes").getInt("count"));
                        photo.setCreatedTime(
                                photoJson.getLong("created_time"));

                        JSONObject commentsJson = photoJson.optJSONObject("comments");

                        if (commentsJson != null) {
                            JSONArray commentsJsonArray = commentsJson.getJSONArray("data");

                            for (int c = 0; c < commentsJsonArray.length(); c++) {
                                JSONObject commentJson = commentsJsonArray.getJSONObject(c);
                                InstagramPhotoComment comment = new InstagramPhotoComment();

                                comment.setCreatedTime(commentJson.getLong("created_time"));
                                comment.setComment(commentJson.getString("text"));
                                comment.setUsername(
                                        commentJson.getJSONObject("from").getString("username"));
                                comment.setUserProfilePictureUrl(
                                        commentJson.getJSONObject("from")
                                                .getString("profile_picture"));

                                photo.addComment(comment);
                            }
                        }

                        mPhotos.add(photo);
                    }
                } catch (JSONException je) {
                    handleError(
                            "Error when parsing returned JSON from Instagram: "
                                    + je.getLocalizedMessage(),
                            je);
                }
                mInstagramPhotosAdapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(
                    int statusCode,
                    Header[] headers,
                    Throwable throwable,
                    JSONObject errorResponse) {
                if (BuildConfig.DEBUG) {
                    Log.d(
                            TAG,
                            String.format(
                                    "Code: %d, status: %s, headers: %s",
                                    statusCode,
                                    errorResponse,
                                    headers));
                }
                handleError(
                        String.format(
                                "Error when fetching photos from instagram."
                                        + " Status code: %d, status: %s",
                                statusCode,
                                errorResponse),
                        throwable);
                mSwipeContainer.setRefreshing(false);
            }
        });
    }

    public void handleError(String msg, Throwable throwable) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Log.e(TAG, msg, throwable);
    }

    @Override
    public void onViewAllComments(List<InstagramPhotoComment> comments) {
        InstagramPhotoCommentListFragment frag =
                InstagramPhotoCommentListFragment.newInstance(comments);
        frag.show(getSupportFragmentManager(),"InstagramPhotoCommentListFragment");
    }
}
