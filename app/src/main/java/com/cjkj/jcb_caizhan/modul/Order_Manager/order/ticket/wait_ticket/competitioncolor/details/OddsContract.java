package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 封装竞彩详情页面相关接口，关联页面与数据
 * Created by 1 on 2018/3/21.
 */
public interface OddsContract {

    interface  IOddsView extends BaseView{

        //修改赔率成功
        void ChengeOddsSuccessful(String msg);

        //修改失败入口
        void ChengeFaild(String msg);

        //确认赔率成功
        void SureOddsSuccessful(String msg);

        //确认赔率失败
        void SureFaild(String msg);

        //打票成功
        void putOrderPicsSuccessful(String msg);

        //打票失败
        void putOrderPicsFaild(String msg);

    }

    interface  IOddsPressenter extends BasePresenter{
        /**
         * 调整竞彩赔率
         *
         * @param uSessionId
         * @param putType      操作类型，0调整赔率
         * @param oddsId       赔率编号，确认赔率时填写orderId
         * @param oddsFiles     赔率字段，(调整赔率时必填)
         * @param oddsValue    赔率，(调整赔率时必填)
         * @return
         */
        void putOrderOdds(String uSessionId,String putType,String oddsId,String oddsFiles,String oddsValue);

        /**
         * 确认赔率
         *
         * @param uSessionId
         * @param putType      操作类型，1确认赔率
         * @param oddsId       赔率编号，确认赔率时填写orderId
         * @param oddsFiles     赔率字段，(调整赔率时必填)
         * @param oddsValue    赔率，(调整赔率时必填)
         * @return
         */
        void sureOrderOdds(String uSessionId,String putType,String oddsId,String oddsFiles,String oddsValue);

        /**
         * 上传打票(Body)
         * @param map
         */
        void putOrderPics(Map<String, Object> map);
    }

}
