package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 封装众筹列表相关接口，关联数据与界面
 * Created by 1 on 2018/3/22.
 */
public interface CrowdContract {

    interface ICrowdView extends BaseView {
        void Filed(String msg);

        //获取众筹列表
        void ListSuccessFul(List<CrowdEntity> crowdList);

        //获取众筹详情
        void DetilsSuccessFul(CrowdEntity mCrowdEntity);

        //修改众筹
        void PutSuccessFul(String msg);

        //更新众筹状态
        void UpdateCrowdSuccessFul(String msg);
    }

    interface ICrowdPressenter extends BasePresenter {
        /**
         * 获取众筹列表
         *
         * @param uSessionId
         * @param getType    0众筹中未开奖前，1众筹历史，2单个订单
         * @param pagesNum   页码，一页30条数据(不是必传)
         * @return
         */
        void getCrowdList(String uSessionId, String getType, int pagesNum);

        /**
         * 获取众筹详情
         *
         * @param uSessionId
         * @param getType    0众筹中未开奖前，1众筹历史，2单个订单
         * @param orderId    单个订单时订单编号
         * @return
         */
        void getCrowdDetils(String uSessionId, String getType, String orderId);


        /**
         * 修改众筹方案
         *
         * @param map Body
         */
        void putCrowd(Map<String, Object> map);

        /**
         * 更新众筹状态（updateType 0撤单，1中止众筹，2打票，3未中奖，4已中奖）
         * @param map
         */
        void updateCrowd(Map<String, Object> map);
    }


}
