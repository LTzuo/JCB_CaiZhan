package com.cjkj.jcb_caizhan.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 禁止GridView滑动
 * @author 林亮
 *
 */
public class NoScollGridView extends GridView{
	
	public NoScollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
	
	public NoScollGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
     * 其中onMeasure函数决定了组件显示的高度与宽度；
     * makeMeasureSpec函数中第一个函数决定布局空间的大小，第二个参数是布局模式
     * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getAction() == MotionEvent.ACTION_MOVE){
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

}
