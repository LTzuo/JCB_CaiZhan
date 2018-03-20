package com.cjkj.jcb_caizhan.modul.Order_Manager.order;

/**
 * 首页订单实体类
 * Created by 1 on 2018/3/15.
 */
public class OrderEntity {

    String upDoneQuantity; //未完成数量
    String lotteryTypeid;   //彩种编号
    String doneQuantity;   //今日共几单
    String typePic;       //彩种图片
    String lotteryName;    //彩种名称

    public String getUpDoneQuantity() {
        return upDoneQuantity;
    }

    public void setUpDoneQuantity(String upDoneQuantity) {
        this.upDoneQuantity = upDoneQuantity;
    }

    public String getLotteryTypeid() {
        return lotteryTypeid;
    }

    public void setLotteryTypeid(String lotteryTypeid) {
        this.lotteryTypeid = lotteryTypeid;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getTypePic() {
        return typePic;
    }

    public void setTypePic(String typePic) {
        this.typePic = typePic;
    }

    public String getDoneQuantity() {
        return doneQuantity;
    }

    public void setDoneQuantity(String doneQuantity) {
        this.doneQuantity = doneQuantity;
    }
}
