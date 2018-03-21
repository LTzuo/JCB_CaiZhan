package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket;

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
 * 首页订单-本天已经打票相关的控制器
 * Created by 1 on 2018/3/16.
 */
public class OverPressenter implements OverContract.IOverPressenter {

    private Subscription mSubscription;

    private OverContract.IOverView mOverView;

    public OverPressenter(OverContract.IOverView mOverView) {
        this.mOverView = mOverView;
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
                        mOverView.ShowFail("获取订单失败");
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
                                mOverView.Sussesful(orderList);
                            } else {
                                ToastUtil.ShortToast(resultText);
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
