package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.normal;

import android.util.Log;

import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
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
 * 首页订单-与打票相关的控制器
 * Created by 1 on 2018/3/16.
 */
public class TicketPressenter implements TicketContract.ITicketPressenter {

    private Subscription mSubscription;

    private TicketContract.ITicketView mTicketView;

    public TicketPressenter(TicketContract.ITicketView mTicketView) {
        this.mTicketView = mTicketView;
    }

    @Override
    public void getCurrentOrders(String uSessionId, String lotteryTypeid, int pagesNum, String orderType) {
        mSubscription = RetrofitHelper.getApi()
                .getCurrentOrders(uSessionId, lotteryTypeid, pagesNum, orderType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mTicketView.ShowFail("获取订单失败");
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
                            String orderListStr = "";
                            if (json.has("orderList") && AppValidationMgr.isNotEmpty(json.get("orderList").toString())) {
                                orderListStr = json.get("orderList").toString();
                            }
                            if (result == 0) {
                                List<TicketEntity> orderList = FastJsonUtil.getBeanList(orderListStr, TicketEntity.class);
                                mTicketView.Sussesful(orderList);
                            } else {
                                ToastUtil.ShortToast(resultText);
                            }
                        }
                    }
                });
    }

    @Override
    public void putOrderPics( Map<String, Object> maps) {
        OkHttpClient okHttpClient = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (null != maps) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
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
                mTicketView.putOrderPicsFaild("上传打票失败");
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
                            mTicketView.putOrderPicsSuccessful(resultText);
                        }else{
                            mTicketView.putOrderPicsFaild(resultText);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    mTicketView.putOrderPicsFaild("上传打票失败");
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
