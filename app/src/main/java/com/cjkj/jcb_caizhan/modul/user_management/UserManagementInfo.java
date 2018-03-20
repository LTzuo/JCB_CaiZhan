package com.cjkj.jcb_caizhan.modul.User_Management;

/**
 * Created by 1 on 2018/1/16.
 * 用户管理模型
 */
public class UserManagementInfo {

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

    }
}
