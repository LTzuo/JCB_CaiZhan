package com.cjkj.jcb_caizhan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * json解析工具类
 */
public class FastJsonUtil {


    public static String createJsonString(Object object){
        String jsonString = "";
        try {
            jsonString = JSON.toJSONString(object);

        } catch (Exception e) {
            // TODO: handle exception
        }

        return jsonString;
    }




    /**
     * ��fastjson ��json�ַ�������Ϊһ�� JavaBean
     *
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }




    /**
     * ��fastjson ��json�ַ��� ������Ϊһ�� List<JavaBean> �� List<String>
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getBeanList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    /**
     * ��fastjson ��jsonString ������ List<Map<String,Object>>
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // ����д��
            // list = JSON.parseObject(jsonString, new
            // TypeReference<List<Map<String, Object>>>(){}.getType());

            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;

    }

    /**
     * 方法名称:transStringToMap
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     * 返回值:Map
     */
    public static Map transStringToMap(String mapString){
        Map map = new HashMap();
        java.util.StringTokenizer items;
        for(StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
            map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }

    /**
     * 将List转换为jsonString
     * @param datalist
     * @return
     */
    public static <T>  String listToJsonString(List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return "";

        return JSON.toJSONString(datalist,true);

    }

//    public static Map<String, Object> getMap(List<Map<String, Object>> mList){
//    	Map<String, Object> map=new HashMap<String, Object>();
//    	try{
//    		map=JSON.parseObject(mList+"");
//    	}catch(Exception e){
//
//    	}
//    	return map;
//    }

}
