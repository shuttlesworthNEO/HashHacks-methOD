package com.method.scenekyahai.models;


import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

/**
 * Created by piyush0 on 29/03/17.
 */

public class Message implements IMessage {
    String id;
    String text;
    transient IUser user;
    Image image;
    Date createdAt;
    Result result;

    public static class Image {
        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    public Message(Date createdAt, String id, Image image, Result result, String text, IUser user) {
        this.createdAt = createdAt;
        this.id = id;
        this.image = image;
        this.result = result;
        this.text = text;
        this.user = user;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    public Image getImage() {
        return image;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "id" + this.getId() + ", text" + this.getText() + ", user" + this.getUser() + ", createdAt" + this.getCreatedAt() + ", result" + this.getResult();
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}
