package com.cjkj.jcb_caizhan.ui.widget.checkbox;

import android.content.Context;

/**
 * Created by 1 on 2018/2/22.
 */
public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
