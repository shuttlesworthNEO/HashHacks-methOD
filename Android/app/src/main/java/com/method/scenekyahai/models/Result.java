package com.method.scenekyahai.models;

/**
 * Created by piyush0 on 12/04/17.
 */

public class Result {
    String url;
    String imageURL;
    String title;
    String source;
    String id;

    public Result(String id, String imageURL, String source, String title, String url) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.url + ", " + this.getTitle() + ", " + this.getImageURL() + ", " + this.getSource() + ", " + this.id;
    }
}
