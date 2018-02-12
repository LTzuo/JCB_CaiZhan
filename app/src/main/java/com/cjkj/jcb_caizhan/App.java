package com.cjkj.jcb_caizhan;

import android.app.Application;

import com.cjkj.jcb_caizhan.ui.widget.glideloader.GlideImageLoader;
import com.previewlibrary.ZoomMediaLoader;
/**
 * Created by 1 on 2018/1/15.
 */
public class App extends Application {

    public static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        Album.initialize(
//                AlbumConfig.newBuilder(this)
//                        .setAlbumLoader(new GlideAlbumLoader()) // 设置Album加载器。
//                        .setLocale(Locale.CHINA) // 比如强制设置在任何语言下都用中文显示。
//                        .build()
//        );
        ZoomMediaLoader.getInstance().init(new GlideImageLoader());
    }

    public static App getInstance() {
        return mInstance;
    }

}