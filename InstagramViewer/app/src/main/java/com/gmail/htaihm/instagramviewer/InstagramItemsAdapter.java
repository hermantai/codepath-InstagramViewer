package com.gmail.htaihm.instagramviewer;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class InstagramItemsAdapter extends ArrayAdapter<InstagramItem> {

    public InstagramItemsAdapter(Context context, ArrayList<InstagramItem> items) {
        super(context, android.R.layout.simple_list_item_1, (ArrayList<InstagramItem>) items);
    }
}
