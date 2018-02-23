package com.cjkj.jcb_caizhan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写ListView不可滑动，用于配合ExpandableListView嵌套使用
 * Created by 1 on 2018/2/23.
 */
public class SubListView extends ListView{

    public SubListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
