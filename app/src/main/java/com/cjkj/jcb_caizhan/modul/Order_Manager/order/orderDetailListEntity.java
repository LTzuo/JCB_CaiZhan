package com.cjkj.jcb_caizhan.modul.Order_Manager.order;

/**
 * 号码列表-球
 */
public class OrderDetailListEntity {

    String orderGroup; //组数，如：A或1或第一位等 [0]
    String orderContent; //数据，如：01 02 03 - 11
    String orderTimes;  //倍数
    String oddsList;//竞彩赔率数据,竞彩时有效

    public String getOddsList() {
        return oddsList;
    }

    public void setOddsList(String oddsList) {
        this.oddsList = oddsList;
    }




    public String getOrderGroup() {
        return orderGroup;
    }

    public void setOrderGroup(String orderGroup) {
        this.orderGroup = orderGroup;
    }

    public String getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(String orderTimes) {
        this.orderTimes = orderTimes;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }
}
