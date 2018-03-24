package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity;

/**
 * 彩种/订单 公用实体，供本地存储xml使用
 * Created by 1 on 2018/3/24.
 */
public class Lottery {

    private int id;
    private String name;
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id:" + id + ", name:" + name + ", value:" + value;
    }
}
