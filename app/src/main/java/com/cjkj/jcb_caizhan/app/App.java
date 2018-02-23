package com.cjkj.jcb_caizhan.app;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.widget.imageloader.GlideImageLoader;
import com.previewlibrary.ZoomMediaLoader;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

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

        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

    public static App getInstance() {
        return mInstance;
    }

}