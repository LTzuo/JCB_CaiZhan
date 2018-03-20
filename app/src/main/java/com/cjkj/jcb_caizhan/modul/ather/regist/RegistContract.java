package com.cjkj.jcb_caizhan.modul.ather.regist;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;

/**
 * Created by 1 on 2018/1/18.
 * 封装注册页面相关接口，关联页面和数据
 */
public interface RegistContract {

    interface IRegistView extends BaseView {

        void ShowFail(String failMessage);

        void VerificationCodeSussesfuly(String msg);

        void UserRegistSussenfuly();

        void InitPwdSusseful(boolean b);
    }

    interface IRegistPresenter extends BasePresenter {
        /**
         * @param sessionId
         * @param phonenum
         * @param getType   0注册，1登录，2找回密码
         */
        void getVerificationCode(String sessionId, String phonenum, int getType);

        /**
         * 用户注册
         *
         * @param userId  手机号
         * @param verCode 验证码
         * @param token   密码
         * @return
         */
        void userRegist(String phonenum, String verCode, String pwd);

        /**
         * 找回密码
         *
         * @param phonenum 手机号
         * @param verCode  验证码
         * @param pwd      密码
         */
        void initPWD(String phonenum, String verCode, String pwd);
    }
}
