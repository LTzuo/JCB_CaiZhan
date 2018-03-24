package com.cjkj.jcb_caizhan.network.api;

import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 所有 Api
 * Created by 1 on 2018/3/14.
 */
public interface MainApi {

    /**
     * 获取欢迎页图片
     *
     * @param uSessionId 用户登录成功的标示
     * @param clientType 客户端类型，0IOS，1Android
     * @param clientOS   客户端系统版本
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getWelcome?")
    Observable<JsonObject> getWelcome(@Query("uSessionId") String uSessionId,
                                      @Query("clientType") String clientType,
                                      @Query("clientOS") String clientOS);

    /**
     * 用户登录
     *
     * @param loginType  登录类型，0密码登录，1验证码登录
     * @param userId     手机号
     * @param token      密码或验证码
     * @param clientType 客户端类型，0IOS，1Android
     * @param clientOS   客户端系统版本
     * @return
     */
    @POST(ApiConstants.URL_BASE + "userLogin?")
    Observable<JsonObject> userLogin(@Query("loginType") String loginType,
                                     @Query("userId") String userId,
                                     @Query("token") String token,
                                     @Query("clientType") String clientType,
                                     @Query("clientOS") String clientOS);


    /**
     * 用户注册
     *
     * @param userId     手机号
     * @param verCode    验证码
     * @param token      密码
     * @param clientType 客户端类型，0IOS，1Android
     * @param clientOS   客户端系统版本
     * @return
     */
    @POST(ApiConstants.URL_BASE + "siteReg?")
    Observable<JsonObject> siteReg(@Query("userId") String userId,
                                   @Query("verCode") String verCode,
                                   @Query("token") String token,
                                   @Query("clientType") String clientType,
                                   @Query("clientOS") String clientOS);

    /**
     * 找回密码
     *
     * @param userId     手机号
     * @param verCode    验证码
     * @param token      密码
     * @param clientType 客户端类型，0IOS，1Android
     * @param clientOS   客户端系统版本
     * @return
     */
    @POST(ApiConstants.URL_BASE + "initPWD?")
    Observable<JsonObject> initPWD(@Query("userId") String userId,
                                   @Query("verCode") String verCode,
                                   @Query("token") String token,
                                   @Query("clientType") String clientType,
                                   @Query("clientOS") String clientOS);


    /**
     * 获取验证码
     *
     * @param sessionId 欢迎页给出的sessionId
     * @param destAddr  接收验证码的手机
     * @param getType   获取类型，0注册，1登录，2找回密码
     * @return
     */
    @POST(ApiConstants.URL_BASE + "sendSms?")
    Observable<JsonObject> sendSms(@Query("sessionId") String sessionId,
                                   @Query("destAddr") String destAddr,
                                   @Query("getType") int getType);


    /**
     * 彩站店铺认证
     *
     * @param uSessionId  登录成功返回的uSessionId (必传)
     * @param invater     邀请人手机号码
     * @param siteName    彩站名称  (必传)
     * @param linkMan     彩站主姓名(必传)
     * @param sitePro     彩站所在省份(必传)
     * @param siteCity    彩站所在城市(必传)
     * @param siteAddress 彩站详细地址(必传)
     * @param sitePic     店面照片(必传)
     * @param siteCPic    福彩代销证照片
     * @param siteLPic    体彩代销证照片
     * @param siteSPic    竞彩代销证照片，（注：福彩、体彩、竞彩代销证照片必须有一个）
     * @param cardPic     手持身份证照片
     * @param authType    认证类型，0初次认证，1被拒后重新认证
     * @return
     */
   // @Multipart
    @POST(ApiConstants.URL_BASE + "siteAuth?")
    Observable<JsonObject> siteAuth(@Query("uSessionId") String uSessionId, @Query("invater") String invater,
                                    @Query("siteName") String siteName, @Query("linkMan") String linkMan,
                                    @Query("sitePro") String sitePro, @Query("siteCity") String siteCity,
                                    @Query("siteAddress") String siteAddress, @Query("sitePic") String sitePic,
                                    @Query("siteCPic") String siteCPic, @Query("siteLPic") String siteLPic,
                                    @Query("siteSPic") String siteSPic, @Query("cardPic") String cardPic,
                                    @Query("authType") String authType);


    /**
     * 获取首页订单-彩种
     * @param uSessionId
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getOrderIndex?")
    Observable<JsonObject> getOrderIndex(@Query("uSessionId") String uSessionId);


    /**
     * 彩种的打票订单列表
     *
     * @param uSessionId   用户登录成功返回uSessionId
     * @param lotteryTypeid  彩种编号
     * @param pagesNum   页码
     * @param orderType   获取类型，0待打票，1已打票
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getCurrentOrders?")
    Observable<JsonObject> getCurrentOrders(@Query("uSessionId") String uSessionId,
                                            @Query("lotteryTypeid") String lotteryTypeid,
                                            @Query("pagesNum") int pagesNum,
                                            @Query("orderType") String orderType);


    /**
     * 调整竞彩赔率和确认赔率
     *
     * @param uSessionId
     * @param putType      操作类型，0调整赔率，1确认赔率
     * @param oddsId       赔率编号，确认赔率时填写orderId
     * @param oddsFiles     赔率字段，(调整赔率时必填)
     * @param oddsValue    赔率，(调整赔率时必填)
     * @return
     */
    @POST(ApiConstants.URL_BASE + "putOrderOdds?")
    Observable<JsonObject> putOrderOdds(@Query("uSessionId") String uSessionId,
                                            @Query("putType") String putType,
                                            @Query("oddsId") String oddsId,
                                            @Query("oddsFiles") String oddsFiles,
                                        @Query("oddsValue") String oddsValue);


    /**
     * 获取彩站认证资料
     * @param uSessionId
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getSiteAuth?")
    Observable<JsonObject> getSiteAuth(@Query("uSessionId") String uSessionId);


    /**
     * 获取众筹列表与详情
     *
     * @param uSessionId
     * @param getType     0众筹中未开奖前，1众筹历史，2单个订单
     * @param orderId      单个订单时订单编号(不是必传)
     * @param pagesNum    页码，一页30条数据(不是必传)
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getCrowds?")
    Observable<JsonObject> getCrowds(@Query("uSessionId") String uSessionId,
                                        @Query("getType") String getType,
                                        @Query("orderId") String orderId,
                                        @Query("pagesNum") String pagesNum);

    /**
     * 数据统计列表
     * @param uSessionId
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getBills?")
    Observable<JsonObject> getBills(@Query("uSessionId") String uSessionId);


    /**
     *
     * @param uSessionId
     * @param userId          彩友编号，查询本站彩友是需要用
     * @param pagesNum       页码
     * @param orderType       订单类型，0数字彩，1传统足球，2竞彩
     * @param lotteryTypeid      彩种编号，查询时使用
     * @param startDate        开始时期，查询时使用
     * @param endDate         结束日期，查询时使用
     * @param orderState        状态，99全部，0凑单中，1等待打票，2已打票，
     *                          3打票错误，4已确认，5系统取消，6 未支付，
     *                          7已支付等待结果，8奏单失败，9场次取消，
     *                          10退票成功，11赔率已更新(0-8为数字彩和传统足球状态、所有都是竞彩的状态)
     * @return
     */
    @POST(ApiConstants.URL_BASE + "getOrders?")
    Observable<JsonObject> getOrders(@Query("uSessionId") String uSessionId,
                                     @Query("userId") String userId,
                                     @Query("pagesNum") int pagesNum,
                                     @Query("orderType") int orderType,
                                     @Query("lotteryTypeid") String lotteryTypeid,
                                     @Query("startDate") String startDate,
                                     @Query("endDate") String endDate,
                                     @Query("orderState") String orderState);



}
