package com.cjkj.jcb_caizhan.modul.Personal_Center.chenge_station;

/**
 * 切换彩站实体
 * Created by 1 on 2018/2/8.
 */
public class ChangeLotteryStationBean {
    private String url;
    private String stationname;
    private String username;
    private String number;

    public ChangeLotteryStationBean(){}

    public ChangeLotteryStationBean(String url,String stationname,String username,String number){
        this.url = url;
        this.stationname = stationname;
        this.username = username;
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
