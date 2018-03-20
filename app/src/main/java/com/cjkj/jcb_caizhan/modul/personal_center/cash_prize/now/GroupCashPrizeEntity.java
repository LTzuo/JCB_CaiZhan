package com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.now;

/**
 * 当前委托Group实体
 * Created by 1 on 2018/2/28.
 */
public class GroupCashPrizeEntity {

    String groupName;
    String stage;//期数

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
