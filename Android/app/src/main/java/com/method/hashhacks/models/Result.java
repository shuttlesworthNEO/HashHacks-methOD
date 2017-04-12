package com.method.hashhacks.models;

/**
 * Created by piyush0 on 12/04/17.
 */

public class Result {
    String url;
    String imageURL;
    String title;
    String source;

    public Result(String imageURL, String source, String title, String url) {
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

    @Override
    public String toString() {
        return this.url + ", " + this.getTitle() + ", " + this.getImageURL() + ", " + this.getSource() + "; ";
    }
}
