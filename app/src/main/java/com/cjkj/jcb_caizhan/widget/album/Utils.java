package com.cjkj.jcb_caizhan.widget.album;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by 1 on 2018/3/7.
 */
public class Utils {

    public static File getAppRootPath(Context context) {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return context.getFilesDir();
        }
    }

    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().canWrite();
        } else
            return false;
    }
}
