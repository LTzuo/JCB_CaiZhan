package com.cjkj.jcb_caizhan.modul.Personal_Center.launch_crowd;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;

import java.util.Map;

/**
 * 发起众筹
 * Created by 1 on 2018/3/22.
 */
public interface LunchCrowdContract {

    interface ILunchCrowdView extends BaseView {
        void Successful(String msg);

        void Faild(String msg);
    }

    interface ILunchCrowdPresenter extends BasePresenter {
        /**
         * 发起众筹
         *
         * @param map Body
         */
        void putCrowd(Map<String, Object> map);
    }

}
