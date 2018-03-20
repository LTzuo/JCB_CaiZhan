package com.cjkj.jcb_caizhan.modul.Order_Manager.order;

import android.util.Log;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页订单控制器
 * Created by 1 on 2018/3/15.
 */
public class OrderPressenter implements OrderContract.IOrderContractPressenter {

    private Subscription mSubscription;

    private OrderContract.IOrderContractView mOrderContractView;

    public OrderPressenter(OrderContract.IOrderContractView mOrderContractView) {
        this.mOrderContractView = mOrderContractView;
    }

    @Override
    public void getOrderIndex(String uSessionId) {
        mSubscription = RetrofitHelper.getApi()
                .getOrderIndex(uSessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mOrderContractView.getOrderIndexFild("获取订单数据失败");
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
                                List<OrderEntity> orderList  =  FastJsonUtil.getBeanList(json.get("orderList").toString(), OrderEntity.class);
                                mOrderContractView.getOrderIndexSuessful(orderList);
                            } else {
                                //result == 1002是uSessionId失效
                                mOrderContractView.getOrderIndexFild(resultText);
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
