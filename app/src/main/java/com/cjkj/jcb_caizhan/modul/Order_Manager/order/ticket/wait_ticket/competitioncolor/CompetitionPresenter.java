package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor;

import android.util.Log;

import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.google.gson.JsonObject;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 竞彩打票控制器
 * Created by 1 on 2018/3/20.
 */
public class CompetitionPresenter implements CompetitioncolorContract.IComoetitioncolorPresenter{

    private Subscription mSubscription;

    private CompetitioncolorContract.ICompetitioncolorView mCompettioncolorView;

    public CompetitionPresenter(CompetitioncolorContract.ICompetitioncolorView mCompettioncolorView){
        this.mCompettioncolorView = mCompettioncolorView;
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
                    public void onError(Throwable e)
                    {
                       // mTicketView.ShowFail("用户登录失败");
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
                                mCompettioncolorView.Successful(orderList);
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
