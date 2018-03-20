package com.cjkj.jcb_caizhan.modul.ather.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.ather.HomeActivity;
import com.cjkj.jcb_caizhan.modul.ather.login.LoginActivity;
import com.cjkj.jcb_caizhan.modul.ather.shop_certification.ShopCertificationActivity;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.utils.ac_manager.ActivityManager;
import com.cjkj.jcb_caizhan.widget.SimpleButton;
import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
/**
 * 开机界面
 */
public class SplashActivity extends RxBaseActivity implements SplashContract.ISplashView {

    @Bind(R.id.sb_skip)
    SimpleButton mSbSkip;
    @Bind(R.id.img_splash)
    ImageView img_splash;

    private boolean mIsSkip = false;
    private boolean isgotologin = false;
    private String b = null;

    SplashPressenter mSplashPressenter;

    private void _doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            if(!AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_uSessionId,"").toString()) || isgotologin){
                IntentUtils.Goto(this, LoginActivity.class);
                ActivityManager.getInstance().finishActivity(this);
                return;
            }
            if(b != null){
                if(b.equals("0")){
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this, ShopCertificationActivity.class);
                    startActivity(intent);
                }
                ActivityManager.getInstance().finishActivity(this);
                overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSplashPressenter = new SplashPressenter(this);
        //if(AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_uSessionId,"").toString())){
            mSplashPressenter.getWelcome(SPUtil.get(this, Constants.key_uSessionId,"").toString());
        //}
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
    public void sussesful(String bannerPath,String b) {
        this.b = b;
        Glide.with(this).load(bannerPath).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(img_splash, 100));

    }

    @Override
    public void goToLogin(String path,boolean isgotologin) {
       this.isgotologin = isgotologin;
        Glide.with(this).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(img_splash, 100));

    }

    @Override
    public void fail(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPressenter.unSubscribe();
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
