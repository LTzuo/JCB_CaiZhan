package com.cjkj.jcb_caizhan.modul.ather.regist;

import android.util.Log;
import com.cjkj.jcb_caizhan.app.App;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.ather.regist.RegistContract.IRegistPresenter;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.CommonUtil;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonObject;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 2018/1/18.
 * 注册页面控制器
 */
public class RegistPresenter implements IRegistPresenter {

    private Subscription mSubscription;

    private RegistContract.IRegistView mRegistView;

    public RegistPresenter(RegistContract.IRegistView registview) {
        this.mRegistView = registview;
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
                        mRegistView.ShowFail("获取验证码失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").getAsString())) {
                            String resultText = json.get("resultText").getAsString();
                            if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").getAsString())) {
                                int result = json.get("result").getAsInt();
                                if (result != 0) {
                                    mRegistView.VerificationCodeSussesfuly(resultText);
                                }
                            }
                        } else {
                            mRegistView.ShowFail("获取验证码失败");
                        }
                    }
                });
    }

    @Override
    public void userRegist(String phonenum, String verCode, String pwd) {
        mSubscription = RetrofitHelper.getApi()
                .siteReg(phonenum, verCode, pwd, "1", CommonUtil.getSystemVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRegistView.ShowFail("用户注册失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if(json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            String resultText = json.get("resultText").getAsString();
                            ToastUtil.ShortToast(resultText);
                        }
                        if(json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if(result == 0 || result == 1){
                                if(json.has("uSessionId") && AppValidationMgr.isNotEmpty(json.get("uSessionId").toString())) {
                                    SPUtil.put(App.getInstance(),Constants.key_uSessionId,json.get("uSessionId").getAsString());
                                }
                                mRegistView.UserRegistSussenfuly();
                             }
                        }else{
                            mRegistView.ShowFail("用户注册失败");
                        }
                    }
                });
    }

    @Override
    public void initPWD(String phonenum, String verCode, String pwd) {
        mSubscription = RetrofitHelper.getApi()
                .initPWD(phonenum, verCode, pwd, "1", CommonUtil.getSystemVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRegistView.ShowFail("找回失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        if(json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            String resultText = json.get("resultText").getAsString();
                            ToastUtil.ShortToast(resultText);
                        }
                        if(json.has("uSessionId") && AppValidationMgr.isNotEmpty(json.get("uSessionId").toString())) {
                            SPUtil.put(App.getInstance(),Constants.key_uSessionId,json.get("uSessionId").getAsString());
                        }
                        if(json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if(result == 0){
                                mRegistView.InitPwdSusseful(true);
                            }else if(result == 1 || result == 2 || result == 3){
                                mRegistView.InitPwdSusseful(false);
                            }
                        }else{
                            mRegistView.ShowFail("找回失败");
                        }
                    }
                });
    }

    @Override
    public void subscribe() {
//        if(id == R.id.btn_newregist){
//            getVerificationCode();
//        }
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
