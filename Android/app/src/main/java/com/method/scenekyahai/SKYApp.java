package com.method.scenekyahai;

import android.app.Application;

/**
 * Created by piyush0 on 13/04/17.
 */

public class SKYApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);
    }
}
