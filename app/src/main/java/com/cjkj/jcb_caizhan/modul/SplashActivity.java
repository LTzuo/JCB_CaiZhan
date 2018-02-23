package com.cjkj.jcb_caizhan.modul;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.widget.SimpleButton;
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

    private ComponentName componentName;
    private ComponentName componentNameDefault;
    private PackageManager packageManager;

    private int INDEX;

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
//        if (SPUtil.get(SplashActivity.this, Constants.LUNCHER_ICON_KEY, 1) == null) {
//            INDEX = 1;
//        } else {
//            INDEX = (int) SPUtil.get(SplashActivity.this, Constants.LUNCHER_ICON_KEY, 1);
//        }
//        INDEX++;
//        SPUtil.put(SplashActivity.this, Constants.LUNCHER_ICON_KEY, INDEX);
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
        //        if (INDEX % 10 == 0) {
//            ToastUtil.ShortToast(INDEX + "被10整除");
//            chengeDefaultLuncherIcon();
//        } else {
//            ToastUtil.ShortToast(INDEX + "不能被10整除");
//            chengeNewLuncherIcon();
//        }
        //获取组件别名
//        componentName = new ComponentName(getBaseContext(), getPackageName() + ".newsLuncherActivity");
//        componentNameDefault = new ComponentName(getBaseContext(), getClass().getName());
//        packageManager = getPackageManager();
//
//        if(DateHelper.getInstance().getCurrentYear().equals("2018")){
//            if(DateHelper.getInstance().getCurrentMonth().equals("2")){
//                chengeNewLuncherIcon();
//                ToastUtil.ShortToast("1");
//            }else if(DateHelper.getInstance().getCurrentMonth().equals("3")){
//                if(Integer.parseInt(DateHelper.getInstance().getCurrDay()) < 16){
//                    chengeNewLuncherIcon();
//                    ToastUtil.ShortToast("2");
//                }else{
//                    chengeDefaultLuncherIcon();
//                    ToastUtil.ShortToast("3");
//                }
//            }else{
//                chengeDefaultLuncherIcon();
//                ToastUtil.ShortToast("4");
//            }
//        }else{
//            chengeDefaultLuncherIcon();
//            ToastUtil.ShortToast("5");
//        }

    }

    /**
     * 切换Luncher 名称和图标
     */
    public void chengeNewLuncherIcon() {
//        if (packageManager.getComponentEnabledSetting(componentName)==
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
//                packageManager.getComponentEnabledSetting(componentName) ==
//                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(componentNameDefault, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
//        }else if (packageManager.getComponentEnabledSetting(componentName)==
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
//            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//            packageManager.setComponentEnabledSetting(componentNameDefault, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

    /**
     * 切换Luncher 名称和图标
     */
    public void chengeDefaultLuncherIcon() {
//        if (packageManager.getComponentEnabledSetting(componentName)==
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
//                packageManager.getComponentEnabledSetting(componentName) ==
//                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
//            packageManager.setComponentEnabledSetting(componentName,
//                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
//            packageManager.setComponentEnabledSetting(componentNameDefault, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
//        }else if (packageManager.getComponentEnabledSetting(componentName)==
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(componentNameDefault, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//        }
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
