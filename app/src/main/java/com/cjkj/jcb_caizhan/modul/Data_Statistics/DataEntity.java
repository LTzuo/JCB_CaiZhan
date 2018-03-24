package com.cjkj.jcb_caizhan.modul.Data_Statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据统计实体
 * Created by 1 on 2018/3/22.
 */
public class DataEntity {
    String billTitle;  //统计主题
    List<BillDetails> billDetails = new ArrayList<>(); //统计明细列表

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public List<BillDetails> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(List<BillDetails> billDetails) {
        this.billDetails = billDetails;
    }

    public static class BillDetails{
        String detailTitle;   //明细主题
        String detailValue1;  //内容1
        String detailValue2;  //内容2

        public String getDetailTitle() {
            return detailTitle;
        }

        public void setDetailTitle(String detailTitle) {
            this.detailTitle = detailTitle;
        }

        public String getDetailValue1() {
            return detailValue1;
        }

        public void setDetailValue1(String detailValue1) {
            this.detailValue1 = detailValue1;
        }

        public String getDetailValue2() {
            return detailValue2;
        }

        public void setDetailValue2(String detailValue2) {
            this.detailValue2 = detailValue2;
        }
    }


}
