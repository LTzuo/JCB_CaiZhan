package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

import android.os.Parcel;
import android.os.Parcelable;

import com.cjkj.jcb_caizhan.utils.FastJsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单-打票实体
 * Created by 1 on 2018/3/16.
 */
public class TicketEntity implements Parcelable {

    private String serialNo;     //订单序列号，根据彩站期数后台生成，如：001
    private String lotteryName;   //彩种名称
    private String lotteryPerion;   //期数，如：第201801期
    private String orderId;     // 订单编号
    private String userPic;     // 订单所有人头像
    private String userNickName;//订单所有人昵称
    private String playType;    //玩法如：单买 复式投注 5注
    private String orderTimes;  //整单倍数，0取orderDetailList中的倍数，非0取此倍数
    private String orderState;  // 状态，1等待打票，3打票错误，4已确认
    private String orderPic;    // 打票错误照片地址，多个用“;”分隔
    private String orderTime;   // 打票时间
    private String orderNote;   // 错误说明
    private String amount;      //订单金额
    //    private List<orderDetailList> orderDetailList  = new ArrayList<>();//  订单号码列表
    private String orderDetailList;//  订单号码列表
    private List<userList> userList = new ArrayList<>();//合买用户列表


    public List<TicketEntity.userList> getUserList() {
        return userList;
    }

    public void setUserList(List<TicketEntity.userList> userList) {
        this.userList = userList;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getLotteryPerion() {
        return lotteryPerion;
    }

    public void setLotteryPerion(String lotteryPerion) {
        this.lotteryPerion = lotteryPerion;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(String orderTimes) {
        this.orderTimes = orderTimes;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderPic() {
        return orderPic;
    }

    public void setOrderPic(String orderPic) {
        this.orderPic = orderPic;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(String orderDetailList) {
        this.orderDetailList = orderDetailList;
    }


    /**
     * 号码列表-球
     */
//    public class orderDetailList {
//        String orderGroup; //组数，如：A或1或第一位等 [0]
//        String orderContent; //数据，如：01 02 03 - 11
//        String orderTimes;  //倍数
//
//        public String getOrderGroup() {
//            return orderGroup;
//        }
//
//        public void setOrderGroup(String orderGroup) {
//            this.orderGroup = orderGroup;
//        }
//
//        public String getOrderTimes() {
//            return orderTimes;
//        }
//
//        public void setOrderTimes(String orderTimes) {
//            this.orderTimes = orderTimes;
//        }
//
//        public String getOrderContent() {
//            return orderContent;
//        }
//
//        public void setOrderContent(String orderContent) {
//            this.orderContent = orderContent;
//        }
//    }

    /**
     * 合买参与人列表
     */
    public class userList {
        String userPic;    //参与人头像
        String userNickName;  //参与人昵称
        String userGrade;   // 参与人等级
        String userAmount;  // 出资
        String percent;  // 占股比
        String winAmount; //奖金

        public String getUserPic() {
            return userPic;
        }

        public void setUserPic(String userPic) {
            this.userPic = userPic;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserGrade() {
            return userGrade;
        }

        public void setUserGrade(String userGrade) {
            this.userGrade = userGrade;
        }

        public String getUserAmount() {
            return userAmount;
        }

        public void setUserAmount(String userAmount) {
            this.userAmount = userAmount;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }

        public String getWinAmount() {
            return winAmount;
        }

        public void setWinAmount(String winAmount) {
            this.winAmount = winAmount;
        }
    }


    public static final Parcelable.Creator<TicketEntity> CREATOR = new Creator<TicketEntity>() {

        @Override
        public TicketEntity createFromParcel(Parcel source) {
            TicketEntity bean = new TicketEntity();
            bean.serialNo = source.readString();
            bean.lotteryName = source.readString();
            bean.lotteryPerion = source.readString();
            bean.orderId = source.readString();
            bean.userPic = source.readString();
            bean.userNickName = source.readString();
            bean.playType = source.readString();
            bean.orderTimes = source.readString();
            bean.orderState = source.readString();
            bean.orderPic = source.readString();
            bean.orderTime = source.readString();
            bean.orderNote = source.readString();
            bean.amount = source.readString();
            bean.orderDetailList = source.readString();
            //  bean.userList = source.readString();
            return bean;
        }

        @Override
        public TicketEntity[] newArray(int size) {
            // TODO Auto-generated method stub
            return new TicketEntity[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serialNo);
        parcel.writeString(lotteryName);
        parcel.writeString(lotteryPerion);
        parcel.writeString(orderId);
        parcel.writeString(userPic);
        parcel.writeString(userNickName);
        parcel.writeString(playType);
        parcel.writeString(orderTimes);
        parcel.writeString(orderState);
        parcel.writeString(orderPic);
        parcel.writeString(orderTime);
        parcel.writeString(orderNote);
        parcel.writeString(amount);
        parcel.writeString(orderDetailList);
//        private List<orderDetailList> orderDetailList  = new ArrayList<>();//  订单号码列表
//        private List<userList> userList = new ArrayList<>();//合买用户列表
    }

}
