package com.cjkj.jcb_caizhan.modul.ather.login;

import android.content.Intent;
import android.util.Log;

import com.cjkj.jcb_caizhan.app.App;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Personal_Center.personcl_information.modify.ModifyActivity;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.CommonUtil;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.cjkj.jcb_caizhan.modul.ather.login.LoginContract.ILoginPressenter;

/**
 * Created by 1 on 2018/1/19.
 * 登录控制器
 */
public class LoginPressenter implements ILoginPressenter {

    private Subscription mSubscription;

    private LoginContract.ILogView mLoginView;

    public LoginPressenter(LoginContract.ILogView mLoginView) {
        this.mLoginView = mLoginView;
    }

    @Override
    public void userLogin(String loginType, String userId, String token) {
        mSubscription = RetrofitHelper.getApi()
                .userLogin(loginType, userId, token, "1", CommonUtil.getSystemVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.ShowFail("用户登录失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            String resultText = json.get("resultText").getAsString();
                            ToastUtil.ShortToast(resultText);
                        }
                        if (json.has("uSessionId") && AppValidationMgr.isNotEmpty(json.get("uSessionId").toString())) {
                            SPUtil.put(App.getInstance(), Constants.key_uSessionId, json.get("uSessionId").getAsString());
                        }
                        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if (result == 0) {
                                //去首页
                                mLoginView.LoginSussesful(true);
                            } else if (result == 1 || result == 2 || result == 0 || result == 3) {
                                //去店铺认证
                                mLoginView.LoginSussesful(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void userShortMessageLogin(String loginType, String userId, String token) {
        mSubscription = RetrofitHelper.getApi()
                .userLogin(loginType, userId, token, "1", CommonUtil.getSystemVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.ShowFail("用户登录失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            String resultText = json.get("resultText").getAsString();
                            ToastUtil.ShortToast(resultText);
                        }
                        if (json.has("uSessionId") && AppValidationMgr.isNotEmpty(json.get("uSessionId").toString())) {
                            SPUtil.put(App.getInstance(), Constants.key_uSessionId, json.get("uSessionId").getAsString());
                        }
                        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if (result == 0) {
                                //去首页
                                mLoginView.LoginSussesful(true);
                            } else if (result == 1 || result == 2 || result == 0) {
                                //去店铺认证
                                mLoginView.LoginSussesful(false);
                            }
                        }
                    }
                });
    }

    @Override
    public void getVerificationCode(String sessionId, String phonenum, int getType) {
        mSubscription = RetrofitHelper.getApi()
                .sendSms(sessionId, phonenum, getType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.ShowFail("获取验证码失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            String resultText = json.get("resultText").getAsString();
                            int result = json.get("result").getAsInt();
                            if (result != 0) {
                                mLoginView.VerificationCodeSussesfuly(resultText);
                            }
                        } else {
                            mLoginView.ShowFail("获取验证码失败");
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
