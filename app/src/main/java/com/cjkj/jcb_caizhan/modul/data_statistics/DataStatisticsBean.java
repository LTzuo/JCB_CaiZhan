package com.cjkj.jcb_caizhan.modul.data_statistics;

/**
 * 数据统计实体
 * Created by 1 on 2018/2/8.
 */
public class DataStatisticsBean {

    private String dataType;//数据类型
    private String dayTitle;
    private String dayofsingle;//日单
    private String dayofprice;//日金额
    private String weekTitle;
    private String weekofsingle;//周单
    private String weekofprice;//周金额
    private String monthTitle;
    private String monthofsingle;//月单
    private String monthofprice;//月金额

    public DataStatisticsBean(){}

    public DataStatisticsBean(String dataType, String dayTitle,String dayofsingle,String weekTitle, String dayofprice, String weekofsingle,
             String weekofprice, String monthTitle,String monthofsingle, String monthofprice){
        this.dataType = dataType;
        this.dayTitle = dayTitle;
        this.dayofsingle = dayofsingle;
        this.dayofprice = dayofprice;
        this.weekTitle = weekTitle;
        this.weekofsingle = weekofsingle;
        this.weekofprice = weekofprice;
        this.monthTitle = monthTitle;
        this.monthofsingle = monthofsingle;
        this.monthofprice = monthofprice;
    }

    public String getDayTitle() {
        return dayTitle;
    }

    public void setDayTitle(String dayTitle) {
        this.dayTitle = dayTitle;
    }

    public String getWeekTitle() {
        return weekTitle;
    }

    public void setWeekTitle(String weekTitle) {
        this.weekTitle = weekTitle;
    }

    public String getMonthTitle() {
        return monthTitle;
    }

    public void setMonthTitle(String monthTitle) {
        this.monthTitle = monthTitle;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDayofsingle() {
        return dayofsingle;
    }

    public void setDayofsingle(String dayofsingle) {
        this.dayofsingle = dayofsingle;
    }

    public String getDayofprice() {
        return dayofprice;
    }

    public void setDayofprice(String dayofprice) {
        this.dayofprice = dayofprice;
    }

    public String getWeekofsingle() {
        return weekofsingle;
    }

    public void setWeekofsingle(String weekofsingle) {
        this.weekofsingle = weekofsingle;
    }

    public String getWeekofprice() {
        return weekofprice;
    }

    public void setWeekofprice(String weekofprice) {
        this.weekofprice = weekofprice;
    }

    public String getMonthofsingle() {
        return monthofsingle;
    }

    public void setMonthofsingle(String monthofsingle) {
        this.monthofsingle = monthofsingle;
    }

    public String getMonthofprice() {
        return monthofprice;
    }

    public void setMonthofprice(String monthofprice) {
        this.monthofprice = monthofprice;
    }
}
