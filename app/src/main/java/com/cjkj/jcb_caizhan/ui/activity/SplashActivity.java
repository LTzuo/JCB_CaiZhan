package com.cjkj.jcb_caizhan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.ui.widget.SimpleButton;
import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * 开机界面
 */
public class SplashActivity extends RxBaseActivity {

    @Bind(R.id.sb_skip)
    SimpleButton mSbSkip;
    @Bind(R.id.img_splash)
    ImageView img_splash;

    private boolean mIsSkip = false;

    private void _doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            finish();
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Glide.with(this).load("https://upload-images.jianshu.io/upload_images/5431992-83bd048f5fe69e4d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/700").diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(img_splash, 100));

        RetrofitHelper.countdown(10)
                //  .compose(this.<Integer>bindToLife())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        _doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        _doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSbSkip.setText("跳过 " + integer);
                    }
                });
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void onBackPressed() {
        // 不响应后退键
        return;
    }

    @OnClick(R.id.sb_skip)
    public void onClick() {
        _doSkip();
    }


}
