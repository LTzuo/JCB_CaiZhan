package com.cjkj.jcb_caizhan.network.api;

import com.cjkj.jcb_caizhan.entity.TestInfo;
import com.cjkj.jcb_caizhan.entity.UserManagement.UserManagementInfo;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 1 on 2018/1/16.
 * 用户管理相关api
 */
public interface UserManagemantService {
    /**
     * 测试接口
     */
    @GET("appkey=4e5fab866cb4ec1b")
    Observable<TestInfo> getDatas();
}
