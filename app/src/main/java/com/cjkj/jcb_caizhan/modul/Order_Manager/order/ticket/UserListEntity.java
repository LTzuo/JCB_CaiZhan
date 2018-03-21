package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

/**
 * 用户合买实体
 * Created by 1 on 2018/3/21.
 */
public class UserListEntity {
    String userPic;    //参与人头像
    String userNickName;  //参与人昵称
    String userGrade;   // 参与人等级
    String userAmount;  // 出资
    String percent;  // 占股比
    String winAmount; //奖金
    public UserListEntity(){}
    public UserListEntity(String userPic, String userNickName, String userGrade,
                          String userAmount, String percent,String winAmount) {
        this.userPic = userPic;
        this.userNickName = userNickName;
        this.userGrade = userGrade;
        this.userAmount = userAmount;
        this.percent = percent;
        this.winAmount = winAmount;
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
