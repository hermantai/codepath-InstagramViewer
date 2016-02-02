package com.gmail.htaihm.instagramviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;

public class InstagramViewerActivity extends AppCompatActivity {
    ListAdapter mInstagramItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_viewer);

        mInstagramItemsAdapter = new InstagramItemsAdapter(
                this, InstagramItem.loadItems());
        ListView lvItems = ButterKnife.findById(this, R.id.lvItems);
        lvItems.setAdapter(mInstagramItemsAdapter);
    }
}
