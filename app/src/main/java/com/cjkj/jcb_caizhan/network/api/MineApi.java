package com.cjkj.jcb_caizhan.network.api;

import com.cjkj.jcb_caizhan.base.MobBaseEntity;
import com.cjkj.jcb_caizhan.entity.mine.lottery.MobLotteryEntity;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * Created by 1 on 2018/1/17.
 * 个人中心相关api
 */
public interface MineApi {

    //支持彩种列表
    //http://apicloud.mob.com/lottery/list?key=appkey
    @GET(ApiConstants.URL_Mob  + "lottery/list")
    Call<MobBaseEntity<ArrayList<String>>> querylotteryList(@Query("key") String appkey);

    //彩票开奖结果查询
    //http://apicloud.mob.com/lottery/query?key=appkey&name=大乐透
    @GET(ApiConstants.URL_Mob + "lottery/query")
    Call<MobBaseEntity<MobLotteryEntity>> querylotteryDetail(@Query("key") String appkey,
                                                             @Query("name") String name
    );
}
