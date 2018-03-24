package com.cjkj.jcb_caizhan.modul.Personal_Center.launch_crowd;

import android.util.Log;

import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

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
import rx.Subscription;

/**
 * 发起众筹控制器
 * Created by 1 on 2018/3/22.
 */
public class LunchCrowdPresenter implements LunchCrowdContract.ILunchCrowdPresenter{
    private Subscription mSubscription;

    private LunchCrowdContract.ILunchCrowdView mView;

    public LunchCrowdPresenter(LunchCrowdContract.ILunchCrowdView mView) {
        this.mView = mView;
    }

    @Override
    public void putCrowd(Map<String, Object> map) {
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
                mView.Faild("发起众筹失败");
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
                            mView.Successful(resultText);
                        }else{
                            mView.Faild(resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mView.Faild("发起众筹失败");
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
