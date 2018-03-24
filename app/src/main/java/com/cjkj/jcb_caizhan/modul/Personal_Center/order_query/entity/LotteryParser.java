package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 1 on 2018/3/24.
 */
public interface LotteryParser {

    /**
     * 解析输入流 得到Book对象集合
     * @param is
     * @return
     * @throws Exception
     */
    public List<Lottery> parse(InputStream is) throws Exception;

    /**
     * 序列化Book对象集合 得到XML形式的字符串
     * @param lotterys
     * @return
     * @throws Exception
     */
    public String serialize(List<Lottery> lotterys) throws Exception;
}
