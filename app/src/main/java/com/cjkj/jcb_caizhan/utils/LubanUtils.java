package com.cjkj.jcb_caizhan.utils;

import android.content.Context;
import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 图片处理工具类，基于Luban算法，类似微信的压缩效果
 * 图片转换、压缩
 */
public class LubanUtils {

    public static Map<Integer, String> imgsMap = new HashMap<>();

    /**
     * 根据图片路径，将文件转换成文件再压缩之后保存到临时Map中
     *
     * @param context
     * @param imgPath
     * @param imgKey
     */
    public static void saveImgToMap(Context context, String imgPath, int imgKey) {
        File file = new File(imgPath);
        Luban.with(context)
                .load(file)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                // .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        ToastUtil.ShortToast("压缩图片中");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        ToastUtil.ShortToast("压缩图片成功");
                        saveImgStringToMap(imgKey,file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.ShortToast("压缩失败");
                    }
                }).launch();    //启动压缩
    }


    /**
     * 根据 file 获取String
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static void saveImgStringToMap(int key,File file) {
        try {
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            if (!imgsMap.isEmpty() && imgsMap.containsKey(key)) {
                imgsMap.remove(key);
            }
            imgsMap.put(key,Base64.encodeToString(buffer, Base64.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 imgKey 获取文件转String
     *
     * @param imgKey
     * @return
     * @throws IOException
     */
    public static String getImgString(int imgKey) {
//        try {
            if (!imgsMap.isEmpty() && imgsMap.containsKey(imgKey)) {
//                FileInputStream inputFile = new FileInputStream(imgsMap.get(imgKey));
//                byte[] buffer = new byte[(int) imgsMap.get(imgKey).length()];
//                inputFile.read(buffer);
//                inputFile.close();
//                return Base64.encodeToString(buffer, Base64.DEFAULT);
                return imgsMap.get(imgKey);
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "";
//        }
        return "";
    }

}