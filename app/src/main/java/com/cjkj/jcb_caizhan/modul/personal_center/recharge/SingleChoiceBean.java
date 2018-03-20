package com.cjkj.jcb_caizhan.modul.Personal_Center.recharge;

import java.io.Serializable;

/**
 * 个人中心-充值(支付方式实体)
 * Created by 1 on 2018/2/22.
 */
public class SingleChoiceBean implements Serializable {

    private int url;//图标
    private String title;//标题
    private boolean isClick;//选中状态

    public SingleChoiceBean(){}

    public SingleChoiceBean(int url,String title,boolean isClick){
        this.url = url;
        this.title = title;
        this.isClick = isClick;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
