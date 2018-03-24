package com.cjkj.jcb_caizhan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

/**
 * 图片处理工具类，基于Luban算法，类似微信的压缩效果
 * 图片转换、压缩
 */
public class LubanUtils {

    /**
     *
     * 自定义压缩文件
     */
    public static File Compress(Context context, String imgPath){
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
