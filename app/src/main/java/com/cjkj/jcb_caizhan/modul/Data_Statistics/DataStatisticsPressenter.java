package com.cjkj.jcb_caizhan.modul.Data_Statistics;

import android.util.Log;

import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdContract;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdEntity;
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
 * 数据统计页面控制器
 * Created by 1 on 2018/3/22.
 */
public class DataStatisticsPressenter implements DataStatisticsContract.IDataStatisticsPressenter{

    private Subscription mSubscription;

    private DataStatisticsContract.IDataStatisticsView mView;

    public DataStatisticsPressenter(DataStatisticsContract.IDataStatisticsView mView) {
        this.mView = mView;
    }

    @Override
    public void getBills(String uSessionId) {
        mSubscription = RetrofitHelper.getApi()
                .getBills(uSessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.Filed("获取数据失败");
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
                                List<DataEntity> list  =  FastJsonUtil.getBeanList(json.get("billList").toString(), DataEntity.class);
                                mView.SussessFul(list,json.get("todayOrders").getAsString(),json.get("todayAmount").getAsString());
                            } else {
                                mView.Filed(resultText);
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
