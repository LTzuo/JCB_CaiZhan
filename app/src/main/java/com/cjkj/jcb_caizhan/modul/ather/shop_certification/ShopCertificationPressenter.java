package com.cjkj.jcb_caizhan.modul.ather.shop_certification;

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
    public void getSiteAuth(String uSessionId) {
        mSubscription = RetrofitHelper.getApi()
                .getSiteAuth(uSessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mShopCertificationView.siteFild("获取彩站认证资料失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
                        mShopCertificationView.getSiteAuthSessuful(json);
                    }
                });
    }

    @Override
    public void siteAuth(Map<String,Object> map) {
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
                .url(ApiConstants.URL_BASE + "siteAuth?")//地址
                .post(body)//添加请求体
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mShopCertificationView.siteFild("店铺认证失败");
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
                            mShopCertificationView.siteAuthSessuful(resultText);
                        }else{
                            mShopCertificationView.siteFild(resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mShopCertificationView.siteFild("店铺认证失败");
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
