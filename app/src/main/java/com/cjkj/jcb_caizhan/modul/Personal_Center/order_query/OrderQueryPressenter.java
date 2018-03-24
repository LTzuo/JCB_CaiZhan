package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query;

import android.util.Log;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity.OrderEntity;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.google.gson.JsonObject;
import java.util.List;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 订单查询控制器
 * Created by 1 on 2018/3/24.
 */
public class OrderQueryPressenter implements OrderQueryContract.IOrderQueryPressenter{

    private Subscription mSubscription;

    private OrderQueryContract.IOrderQueryView mView;

    public OrderQueryPressenter(OrderQueryContract.IOrderQueryView mView) {
        this.mView = mView;
    }

    @Override
    public void getOrders(String uSessionId, int pagesNum, int orderType, String lotteryTypeid, String startDate, String endDate, String orderState) {
        mSubscription = RetrofitHelper.getApi()
                .getOrders(uSessionId,"",pagesNum,orderType,lotteryTypeid,startDate,endDate,orderState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.Fild("订单查询失败");
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
                                mView.SuccessFul(orderList);
                            } else {
                                mView.Fild(resultText);
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
