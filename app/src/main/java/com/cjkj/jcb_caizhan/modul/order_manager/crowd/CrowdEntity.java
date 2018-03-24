package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd;


/**
 * 订单管理-众筹实体
 * Created by 1 on 2018/2/22.
 */
public class CrowdEntity {
    String orderId;    //订单编号
    String crowdTitle; //众筹标题
    String orderPart;//众筹份数
    String okPart;//已筹份数
    String noPart;//未筹份数
    String crowdState;//状态说明，0凑单中，1待打票，2待开奖，3已中奖，4未中奖，5已取消（撤单)
    String state;//订单状态
    String endTime;//截止时间
    String amount;//总金额
    String perAmount;//每份金额
    String serviceAmount;//服务费
    String content;//众筹方案介绍
    String crowdPics;//众筹方案图片用“;”隔开
    String autherSitePic; //发起人店面照片
    String autherSiteName;//发起人店面名称
    String autherName;   //发起人姓名
    String autherDestAddr;  //发起人电话
    String orderList;//参与人列表

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCrowdTitle() {
        return crowdTitle;
    }

    public void setCrowdTitle(String crowdTitle) {
        this.crowdTitle = crowdTitle;
    }

    public String getOrderPart() {
        return orderPart;
    }

    public void setOrderPart(String orderPart) {
        this.orderPart = orderPart;
    }

    public String getOkPart() {
        return okPart;
    }

    public void setOkPart(String okPart) {
        this.okPart = okPart;
    }

    public String getNoPart() {
        return noPart;
    }

    public void setNoPart(String noPart) {
        this.noPart = noPart;
    }

    public String getCrowdState() {
        return crowdState;
    }

    public void setCrowdState(String crowdState) {
        this.crowdState = crowdState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(String perAmount) {
        this.perAmount = perAmount;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrowdPics() {
        return crowdPics;
    }

    public void setCrowdPics(String crowdPics) {
        this.crowdPics = crowdPics;
    }

    public String getAutherSitePic() {
        return autherSitePic;
    }

    public void setAutherSitePic(String autherSitePic) {
        this.autherSitePic = autherSitePic;
    }

    public String getAutherSiteName() {
        return autherSiteName;
    }

    public void setAutherSiteName(String autherSiteName) {
        this.autherSiteName = autherSiteName;
    }

    public String getAutherName() {
        return autherName;
    }

    public void setAutherName(String autherName) {
        this.autherName = autherName;
    }

    public String getAutherDestAddr() {
        return autherDestAddr;
    }

    public void setAutherDestAddr(String autherDestAddr) {
        this.autherDestAddr = autherDestAddr;
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }
}
