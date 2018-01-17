package com.cjkj.jcb_caizhan.network.api;

import com.cjkj.jcb_caizhan.entity.TestInfo;
import com.cjkj.jcb_caizhan.entity.UserManagement.UserManagementInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 1 on 2018/1/16.
 * 用户管理相关api
 */
public interface UserManagemantApi {
//    /**
//     * 测试接口
//     *  key = 4e5fab866cb4ec1b
//     */
//    @GET("area/province?")
//    Observable<List<TestInfo.TestWeatherInfo>> getDatas(@Query("appkey") String appkey);

    /**
     * 测试接口
     * 根据category获取Android、iOS等干货数据
     * @param category  类别
     * @param count     条目数目
     * @param page      页数
     */
    @GET("data/{category}/{count}/{page}")
    Observable<TestInfo> getDatas(@Path("category")String category, @Path("count")int count, @Path("page")int page);
}
