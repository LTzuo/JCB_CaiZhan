package com.cjkj.jcb_caizhan;

import android.app.Application;
/**
 * Created by 1 on 2018/1/15.
 */
public class App extends Application {

    public static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static App getInstance() {
        return mInstance;
    }

}