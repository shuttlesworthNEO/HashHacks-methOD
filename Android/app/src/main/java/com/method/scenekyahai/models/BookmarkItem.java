package com.method.scenekyahai.models;

import java.util.ArrayList;

/**
 * Created by piyush0 on 13/04/17.
 */

public class BookmarkItem {
    String title;
    String source;
    String imageURL;
    String url;

    public BookmarkItem(String imageURL, String source, String title, String url) {
        this.imageURL = imageURL;
        this.source = source;
        this.title = title;
        this.url = url;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public static ArrayList<BookmarkItem> getDummyItems() {
        ArrayList<BookmarkItem> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            items.add(new BookmarkItem("http://www.flooringvillage.co.uk/ekmps/shops/flooringvillage/images/request-a-sample--547-p.jpg",
                    "www.image.com","Dummy title","sdkfnsfja.com"));
        }

        return items;

    }

}
