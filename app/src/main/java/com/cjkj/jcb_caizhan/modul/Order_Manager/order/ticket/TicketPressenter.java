package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

import android.util.Log;

import com.cjkj.jcb_caizhan.app.App;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderEntity;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.CommonUtil;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.SPUtil;
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
                        mTicketView.ShowFail("用户登录失败");
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
        mSubscription = RetrofitHelper.getApi()
                .putOrderPics( )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       // mTicketView.getOrderIndexFild("获取订单数据失败");
                        mTicketView.ShowFail("上传失败");
                    }

                    @Override
                    public void onNext(JsonObject json) {
                        Log.i(Constants.LOG, json.toString());
//                        String resultText = "";
//                        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
//                            resultText = json.get("resultText").getAsString();
//                        }
//                        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
//                            int result = json.get("result").getAsInt();
//                            if (result == 0) {
//                                List<OrderEntity> orderList  =  FastJsonUtil.getBeanList(json.get("orderList").toString(), OrderEntity.class);
//                                mOrderContractView.getOrderIndexSuessful(orderList);
//                            } else {
//                                //result == 1002是uSessionId失效
//                                mOrderContractView.getOrderIndexFild(resultText);
//                            }
//                        }
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
