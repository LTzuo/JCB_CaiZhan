package com.cjkj.jcb_caizhan.modul.ather.welcome;

import android.util.Log;

import com.cjkj.jcb_caizhan.app.App;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.CommonUtil;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 欢迎页控制器
 * Created by 1 on 2018/3/14.
 */
public class SplashPressenter implements SplashContract.ISpashPressenter {

    private Subscription mSubscription;

    private SplashContract.ISplashView mSplashView;

    public SplashPressenter(SplashContract.ISplashView mSplashView) {
        this.mSplashView = mSplashView;
    }


    @Override
    public void getWelcome(String uSessionId) {
        mSubscription = RetrofitHelper.getApi()
                .getWelcome(uSessionId, "1", CommonUtil.getSystemVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        mSplashView.fail("");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                                int result = json.get("result").getAsInt();
                                String resultText = json.get("resultText").getAsString();
                                if (result == 0) {
                                    if(json.has("sessionId") && AppValidationMgr.isNotEmpty(json.get("sessionId").toString())) {
                                        SPUtil.put(App.getInstance(),Constants.key_SessionId,json.get("sessionId").getAsString());
                                    }
                                    mSplashView.sussesful(resultText,"0");
                                }else if(result == 1 || result == 2 || result == 3){
                                    mSplashView.sussesful(resultText,"1");
                                } else if(result == 4){
                                    //账号失效或被踢了
                                    mSplashView.goToLogin(resultText,true);
                                }else {
                                    mSplashView.fail(resultText);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
