package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity.OrderEntity;

import java.util.List;

/**
 * 封装订单查询相关接口，关联数据与界面
 * Created by 1 on 2018/3/24.
 */
public interface OrderQueryContract {

    interface  IOrderQueryView extends BaseView{
         void Fild(String msg);
         void SuccessFul(List<OrderEntity> mDatas);
    }

    interface IOrderQueryPressenter extends BasePresenter{
        /**
         *
         * @param uSessionId
         * @param userId          彩友编号，查询本站彩友是需要用(在订单查询里可以不传)
         * @param pagesNum       页码
         * @param orderType       订单类型，0数字彩，1传统足球，2竞彩
         * @param lotteryTypeid      彩种编号，查询时使用
         * @param startDate        开始时期，查询时使用
         * @param endDate         结束日期，查询时使用
         * @param orderState        状态，99全部，0凑单中，1等待打票，2已打票，
         *                          3打票错误，4已确认，5系统取消，6 未支付，
         *                          7已支付等待结果，8奏单失败，9场次取消，
         *                          10退票成功，11赔率已更新(0-8为数字彩和传统足球状态、所有都是竞彩的状态)
         * @return
         */
        void getOrders( String uSessionId,int pagesNum,int orderType, String lotteryTypeid,
                        String startDate,String endDate,String orderState);
    }
}
