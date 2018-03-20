package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;

import java.util.List;

/**
 * 封装竞彩相关接口，关联页面与数据
 * Created by 1 on 2018/3/20.
 */
public interface CompetitioncolorContract {

    interface ICompetitioncolorView extends BaseView{
         void Successful(List<TicketEntity> orderList);
    }

    interface IComoetitioncolorPresenter extends BasePresenter{
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
    }

}
