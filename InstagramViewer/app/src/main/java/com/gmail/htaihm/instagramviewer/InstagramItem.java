package com.gmail.htaihm.instagramviewer;

import java.util.ArrayList;

public class InstagramItem {
    private String caption = "hello";

    public InstagramItem(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return caption;
    }

    public static ArrayList<InstagramItem> loadItems() {
        ArrayList<InstagramItem> items = new ArrayList<>();
        items.add(new InstagramItem("a1"));
        items.add(new InstagramItem("a2"));

        return items;
    }
}
