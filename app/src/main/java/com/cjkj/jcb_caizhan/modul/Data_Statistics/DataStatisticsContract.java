package com.cjkj.jcb_caizhan.modul.Data_Statistics;

import com.cjkj.jcb_caizhan.base.BasePresenter;
import com.cjkj.jcb_caizhan.base.BaseView;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 封装数据统计相关接口，关联数据与页面
 * Created by 1 on 2018/3/22.
 */
public interface DataStatisticsContract {

    interface IDataStatisticsView extends BaseView {

        void SussessFul(List<DataEntity> crowdList,String item_1,String item_2);

        void Filed(String msg);
    }

    interface IDataStatisticsPressenter extends BasePresenter {
        /**
         * 数据统计列表
         *
         * @param uSessionId
         * @return
         */
        void getBills(String uSessionId);

    }
}
