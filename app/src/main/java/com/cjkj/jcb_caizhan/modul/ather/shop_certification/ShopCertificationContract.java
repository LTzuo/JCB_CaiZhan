package com.cjkj.jcb_caizhan.modul.ather.shop_certification;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * 封装店铺认证界面相关接口，关联页面与数据
 * Created by 1 on 2018/3/15.
 */
public interface ShopCertificationContract {

    interface IShopCertificationView extends BaseView{
        void siteAuthSessuful(String msg);
        void getSiteAuthSessuful(JsonObject json);
        void siteFild(String msg);
    }

    interface IShopCertifitionPressenter extends BasePresenter{
        /**
         * uSessionId	string	登录成功返回的sessionId
         * invater	    string	邀请人手机号码
         * siteName	string	彩站名称
         * linkMan	string	彩站主姓名
         * sitePro  	string	彩站所在省份
         * siteCity	    string	彩站所在城市
         * siteAddress	string	彩站详细地址
         * sitePic	    string	店面照片
         * siteCPic	string	福彩代销证照片
         * siteLPic	string	体彩代销证照片
         * siteSPic	string	竞彩代销证照片，注：福彩、体彩、竞彩代销证照片必须有一个
         * cardPic	string	手持身份证照片
         * authType	string	认证类型，0初次认证，1被拒后重新认证
         *
         *
         * body   map
         */
        void siteAuth(Map<String,Object> map);

        /**
         * 获取彩站认证资料
         * @param uSessionId
         */
        void getSiteAuth(String uSessionId);

    }


}
