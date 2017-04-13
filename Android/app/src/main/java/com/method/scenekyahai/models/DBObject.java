package com.method.scenekyahai.models;

import io.realm.RealmObject;

/**
 * Created by piyush0 on 13/04/17.
 */

public class DBObject extends RealmObject {
    String messageJSON;

    public String getMessageJSON() {
        return messageJSON;
    }

    public void setMessageJSON(String messageJSON) {
        this.messageJSON = messageJSON;
    }
}
