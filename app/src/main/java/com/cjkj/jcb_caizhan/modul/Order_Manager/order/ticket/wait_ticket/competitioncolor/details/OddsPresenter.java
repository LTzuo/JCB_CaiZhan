package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import android.util.Log;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 竞彩详情页面控制器
 * Created by 1 on 2018/3/21.
 */
public class OddsPresenter implements OddsContract.IOddsPressenter {

    private Subscription mSubscription;

    private OddsContract.IOddsView mOddsView;

    public OddsPresenter(OddsContract.IOddsView mOddsView) {
        this.mOddsView = mOddsView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void putOrderOdds(String uSessionId, String putType, String oddsId, String oddsFiles, String oddsValue) {
        mSubscription = RetrofitHelper.getApi()
                .putOrderOdds(uSessionId, putType, oddsId, oddsFiles, oddsValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mOddsView.ChengeFaild("更新赔率失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        String resultText = "";
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            resultText = json.get("resultText").getAsString();
                        }
                        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if (result == 0) {
                                mOddsView.ChengeOddsSuccessful(resultText);
                            } else {
                                mOddsView.ChengeFaild(resultText);
                            }
                        }
                    }
                });
    }

    @Override
    public void sureOrderOdds(String uSessionId, String putType, String oddsId, String oddsFiles, String oddsValue) {
        mSubscription = RetrofitHelper.getApi()
                .putOrderOdds(uSessionId, putType, oddsId, oddsFiles, oddsValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mOddsView.SureFaild("确认赔率失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        String resultText = "";
                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
                            resultText = json.get("resultText").getAsString();
                        }
                        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
                            int result = json.get("result").getAsInt();
                            if (result == 0) {
                                mOddsView.SureOddsSuccessful(resultText);
                            } else {
                                mOddsView.SureFaild(resultText);
                            }
                        }
                    }
                });
    }

    @Override
    public void putOrderPics(Map<String, Object> map) {
        OkHttpClient okHttpClient = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof File) {
                        File f = (File) entry.getValue();
                        builder.addFormDataPart(entry.getKey(), f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
                    } else {
                        builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                    }
                }
            }
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //构建Request请求
        final Request request = new Request.Builder()
                .url(ApiConstants.URL_BASE + "putOrderPics?")//地址
                .post(body)//添加请求体
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mOddsView.putOrderPicsFaild("上传打票失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    Log.i("LOG",data);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data);
                        int result =  json.getInt("result");
                        String resultText = json.getString("resultText");
                        if(result == 0){
                            mOddsView.putOrderPicsSuccessful(resultText);
                        }else{
                            mOddsView.putOrderPicsFaild(resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mOddsView.putOrderPicsFaild("上传打票失败");
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
