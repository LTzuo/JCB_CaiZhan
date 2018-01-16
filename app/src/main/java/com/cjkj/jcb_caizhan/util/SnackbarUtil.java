package com.cjkj.jcb_caizhan.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by hcc on 18/1/16 .
 * 一个简单的SnackBar工具类
 */
public class SnackbarUtil {
    public static void showMessage(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
