package com.method.hashhacks.models;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by piyush0 on 29/03/17.
 */

public class Author implements IUser {
    String id;
    String name;
    String avatar;

    public Author(String avatar, String id, String name) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
