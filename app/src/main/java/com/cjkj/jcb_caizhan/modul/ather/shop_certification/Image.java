package com.cjkj.jcb_caizhan.modul.ather.shop_certification;

/**
 *彩站认证中图片实体
 * Created by 1 on 2018/3/22.
 */
public class Image {

    String imgPath;//图片路径
    boolean isLocal = true;//是否是本地路径,默认为本地路径

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
