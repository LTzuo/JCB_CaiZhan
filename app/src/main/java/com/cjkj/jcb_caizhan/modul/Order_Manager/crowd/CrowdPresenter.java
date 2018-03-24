package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd;

import android.util.Log;

import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderEntity;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
 * 订单-众筹控制器
 * Created by 1 on 2018/3/22.
 */
public class CrowdPresenter implements CrowdContract.ICrowdPressenter{

    private Subscription mSubscription;

    private CrowdContract.ICrowdView mView;

    public CrowdPresenter(CrowdContract.ICrowdView mView) {
        this.mView = mView;
    }

    @Override
    public void getCrowdList(String uSessionId, String getType, int pagesNum) {
        mSubscription = RetrofitHelper.getApi()
                .getCrowds(uSessionId,getType,"",String.valueOf(pagesNum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.Filed("获取众筹列表数据失败");
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
                                List<CrowdEntity> crowdList  =  FastJsonUtil.getBeanList(json.get("crowdList").toString(), CrowdEntity.class);
                                mView.ListSuccessFul(crowdList);
                            } else {
                                mView.Filed(resultText);
                            }
                        }
                    }
                });
    }

    @Override
    public void getCrowdDetils(String uSessionId, String getType, String orderId) {
        mSubscription = RetrofitHelper.getApi()
                .getCrowds(uSessionId,getType,orderId,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.Filed("获取众筹详情数据失败");
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
                                List<CrowdEntity> crowdList  =  FastJsonUtil.getBeanList(json.get("crowdList").toString(), CrowdEntity.class);
                                mView.DetilsSuccessFul(crowdList.get(0));
                            } else {
                                mView.Filed(resultText);
                            }
                        }
                    }
                });
    }

    @Override
    public void putCrowd(Map<String, Object> map) {
        //修改众筹
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
                .url(ApiConstants.URL_BASE + "putCrowd?")//地址
                .post(body)//添加请求体
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mView.Filed("修改众筹失败");
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
                            mView.PutSuccessFul(resultText);
                        }else{
                            mView.Filed(resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mView.Filed("修改众筹失败");
                }
            }
        });

    }

    @Override
    public void updateCrowd(Map<String, Object> map) {
        //更新众筹状态
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
                .url(ApiConstants.URL_BASE + "updateCrowd?")//地址
                .post(body)//添加请求体
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mView.Filed("更新众筹状态失败");
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
                            mView.UpdateCrowdSuccessFul(resultText);
                        }else{
                            mView.Filed(resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mView.Filed("更新众筹状态失败");
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
