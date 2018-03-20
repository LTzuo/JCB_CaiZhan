package com.cjkj.jcb_caizhan.modul.ather.welcome;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;

/**
 * 封装欢迎页面相关接口，关联页面和数据
 * Created by 1 on 2018/3/14.
 */
public class SplashContract {

    interface  ISplashView extends BaseView{

        void fail(String msg);

        void goToLogin(String img,boolean isgotologin);

        void sussesful(String bannerPath,String b);

    }

    interface  ISpashPressenter extends BasePresenter{
        void getWelcome(String uSessionId);
    }


}
