package com.cjkj.jcb_caizhan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Base64;

import com.nanchen.compresshelper.CompressHelper;

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

                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.ShortToast("压缩失败");
                    }
                }).launch();    //启动压缩
    }

    /**
     *
     * 自定义压缩文件
     */
    public static File Compress(Context context,String imgPath){
     return   new CompressHelper.Builder(context)
                .setMaxWidth(1200)  // 默认最大宽度为720
                .setMaxHeight(1600) // 默认最大高度为960
                .setQuality(80)    // 默认压缩质量为80
               // .setFileName(yourFileName) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(new File(imgPath));
    }

}