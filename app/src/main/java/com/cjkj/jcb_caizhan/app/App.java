package com.cjkj.jcb_caizhan.app;

import android.app.Application;

import com.cjkj.jcb_caizhan.widget.Album.GlideAlbumLoader;
import com.cjkj.jcb_caizhan.widget.Imageloader.GlideImageLoader;
import com.previewlibrary.ZoomMediaLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;


/**
 * Created by 1 on 2018/1/15.
 */
public class App extends Application {

    public static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //浏览大图
        ZoomMediaLoader.getInstance().init(new GlideImageLoader());

        initCameraTheme();
    }

    /**
     * 自定义图片加载器
     */
    private void initCameraTheme(){
//        ISNav.getInstance().init(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, String path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
//            }
//        });

        Album.initialize(
                AlbumConfig.newBuilder(this)
                        .setAlbumLoader(new GlideAlbumLoader()) // 设置Album加载器。
                        .setLocale(Locale.CHINA) // 比如强制设置在任何语言下都用中文显示。
                        .build()
        );
    }



    public static App getInstance() {
        return mInstance;
    }

}