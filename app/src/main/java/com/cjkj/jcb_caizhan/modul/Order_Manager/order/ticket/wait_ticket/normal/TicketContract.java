package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.normal;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * 封装打票相关接口，关联界面与数据
 * Created by 1 on 2018/3/16.
 */
public interface TicketContract {

    interface ITicketView extends BaseView{
        void ShowFail(String msg);

        void Sussesful(List<TicketEntity> orderList);

        //打票成功
        void putOrderPicsSuccessful(String msg);

        //打票失败
        void putOrderPicsFaild(String msg);
    }

    interface  ITicketPressenter extends BasePresenter{
        /**
         * 彩种的打票订单列表
         *
         * @param uSessionId   用户登录成功返回uSessionId
         * @param lotteryTypeid  彩种编号
         * @param pagesNum   页码
         * @param orderType   获取类型，0待打票，1已打票
         * @return
         */
        void getCurrentOrders(String uSessionId,String lotteryTypeid,int pagesNum,String orderType);

        /**
         * 上传打票(Body)
         * @param map
         */
        void putOrderPics(Map<String, Object> map);

    }

}
