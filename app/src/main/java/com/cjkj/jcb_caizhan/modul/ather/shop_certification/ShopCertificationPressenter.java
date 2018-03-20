package com.cjkj.jcb_caizhan.modul.ather.shop_certification;

import android.util.Log;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonObject;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 彩站店铺认证控制器
 * Created by 1 on 2018/3/15.
 */
public class ShopCertificationPressenter implements ShopCertificationContract.IShopCertifitionPressenter{

    private Subscription mSubscription;

    private ShopCertificationContract.IShopCertificationView mShopCertificationView;

    public ShopCertificationPressenter(ShopCertificationContract.IShopCertificationView mShopCertificationView) {
        this.mShopCertificationView = mShopCertificationView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void siteAuth(String uSessionId, String invater, String siteName, String linkMan, String sitePro, String siteCity, String siteAddress, String sitePic, String siteCPic, String siteLPic, String siteSPic, String cardPic, String authType) {
        mSubscription = RetrofitHelper.getApi()
                .siteAuth(uSessionId,invater,siteName,linkMan,sitePro,siteCity,siteAddress,sitePic,siteCPic,siteLPic,siteSPic,cardPic,authType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mShopCertificationView.siteFild("店铺认证失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            String resultText = json.get("resultText").getAsString();
                            ToastUtil.ShortToast(resultText);
                        }
                        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if (result == 0) {
//                                //提交成功
//                                mShopCertificationView.siteAuthSessuful();
                            }
                        }
                    }
                });
    }


    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
