package com.cjkj.jcb_caizhan.modul.order_manager.crowd;

import java.util.List;

/**
 * 订单管理-众筹实体
 * Created by 1 on 2018/2/22.
 */
public class CrowdEntity {
    private String title0;
    private String title1;
    private String title2;
    private String title3;

    private List<String> imgurls;

    public String getTitle0() {
        return title0;
    }

    public void setTitle0(String title0) {
        this.title0 = title0;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public List<String> getImgurls() {
        return imgurls;
    }

    public void setImgurls(List<String> imgurls) {
        this.imgurls = imgurls;
    }
}
