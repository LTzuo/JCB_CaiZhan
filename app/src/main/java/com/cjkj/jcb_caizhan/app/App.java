package com.cjkj.jcb_caizhan.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.widget.Album.GlideAlbumLoader;
import com.cjkj.jcb_caizhan.widget.Imageloader.GlideImageLoader;
import com.previewlibrary.ZoomMediaLoader;
import com.weavey.loading.lib.LoadingLayout;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;


/**
 * Created by 1 on 2018/1/15.
 */
public class App extends Application {

    public static App mInstance;

    public static Handler mAppHandler = new Handler(Looper.myLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //浏览大图
        ZoomMediaLoader.getInstance().init(new GlideImageLoader());

        initCameraTheme();

        initLoadinglayout();
    }

    private void initLoadinglayout(){
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络")
                .setErrorImage(R.mipmap.define_error)
                .setEmptyImage(R.mipmap.define_empty)
                .setNoNetworkImage(R.mipmap.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150,40);
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