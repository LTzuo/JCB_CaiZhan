package com.cjkj.jcb_caizhan.modul.Order_Manager.order;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * 首页订单相关，关联页面与数据
 * Created by 1 on 2018/3/15.
 */
public interface OrderContract {

    interface IOrderContractView extends BaseView {

        void getOrderIndexFild(String msg);

        void getOrderIndexSuessful(List<OrderEntity> orderList);
    }

    interface IOrderContractPressenter extends BasePresenter {
        /**
         * 获取首页订单
         *
         * @param uSessionId
         */
        void getOrderIndex(String uSessionId);


    }

}
