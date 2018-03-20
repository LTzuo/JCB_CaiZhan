package com.cjkj.jcb_caizhan.modul.ather.login;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;

/**
 * Created by 1 on 2018/1/19.
 * 封装登录页面相关接口，关联页面和数据
 */
public interface LoginContract {

    interface ILogView extends BaseView {
        void ShowFail(String msg);

        /**
         * @param b   true 表示店铺已认证  false表示未认证、审核中或者审核失败
         */
        void LoginSussesful(boolean b);

        void VerificationCodeSussesfuly(String msg);
    }

    interface  ILoginPressenter extends BasePresenter {
        /**
         * 用户正常登录
         * @param loginType  登录类型，0密码登录，1验证码登录
         * @param userId     手机号
         * @param token      密码或验证码
         * @return
         */
        void userLogin(String loginType, String userId,String token);

        /**
         * 用户短信验证登录
         * @param loginType  登录类型，0密码登录，1验证码登录
         * @param userId     手机号
         * @param token      密码或验证码
         * @return
         */
        void userShortMessageLogin(String loginType, String userId,String token);

        /**
         * @param sessionId
         * @param phonenum
         * @param getType   0注册，1登录，2找回密码
         */
        void getVerificationCode(String sessionId,String phonenum,int getType);

    }
}
