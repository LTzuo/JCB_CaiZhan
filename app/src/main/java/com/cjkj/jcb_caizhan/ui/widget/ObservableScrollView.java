package com.cjkj.jcb_caizhan.ui.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
/**
 * 重写ScrollView对外抛出滑动监听数据
 * 2018/1/16
 * 林天佐/此ScrollView可与任何刷新控件嵌套使用，并保持了ScrollView有一个子View特性，解决了刷新时滑动冲突
 */
public class ObservableScrollView extends ScrollView implements NestedScrollingChild {

    private NestedScrollingChildHelper childHelper;
    private float ox;
    private float oy;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    private void init() {
        this.setWillNotDraw(false);
        this.childHelper = new NestedScrollingChildHelper(this);
        this.childHelper.setNestedScrollingEnabled(true);
    }

    /**
     * 回调接口监听事件
     */
    private OnObservableScrollViewListener mOnObservableScrollViewListener;

    public ObservableScrollView(Context context) {
        super(context);
        this.init();
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    /**
     * 添加回调接口，便于把滑动事件的数据向外抛
     */
    public interface OnObservableScrollViewListener {
        void onObservableScrollViewListener(int l, int t, int oldl, int oldt);
    }

    /**
     * 注册回调接口监听事件
     *
     * @param onObservableScrollViewListener
     */
    public void setOnObservableScrollViewListener(OnObservableScrollViewListener onObservableScrollViewListener) {
        this.mOnObservableScrollViewListener = onObservableScrollViewListener;
    }

    /**
     * 滑动监听
     * This is called in response to an internal scroll in this view (i.e., the
     * view scrolled its own contents). This is typically as a result of
     * {@link #scrollBy(int, int)} or {@link #scrollTo(int, int)} having been
     * called.
     *
     * @param l    Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t    Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnObservableScrollViewListener != null) {
            //将监听到的数据向外抛
            mOnObservableScrollViewListener.onObservableScrollViewListener(l, t, oldl, oldt);
        }
    }


    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == 0) {
            this.ox = ev.getX();
            this.oy = ev.getY();
            this.startNestedScroll(3);
        }

        if(ev.getAction() == 1 || ev.getAction() == 3) {
            this.stopNestedScroll();
        }

        if(ev.getAction() == 2) {
            float clampedX = ev.getX();
            float clampedY = ev.getY();
            int dx = (int)(this.ox - clampedX);
            int dy = (int)(this.oy - clampedY);
            if(this.dispatchNestedPreScroll(dx, dy, this.consumed, this.offsetInWindow)) {
                ev.setLocation(clampedX + (float)this.consumed[0], clampedY + (float)this.consumed[1]);
            }
            this.ox = ev.getX();
            this.oy = ev.getY();
        }

        return super.onTouchEvent(ev);
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        this.childHelper.setNestedScrollingEnabled(enabled);
    }

    public boolean isNestedScrollingEnabled() {
        return this.childHelper.isNestedScrollingEnabled();
    }

    public boolean startNestedScroll(int axes) {
        return this.childHelper.startNestedScroll(axes);
    }

    public void stopNestedScroll() {
        this.childHelper.stopNestedScroll();
    }

    public boolean hasNestedScrollingParent() {
        return this.childHelper.hasNestedScrollingParent();
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return this.childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return this.childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return this.childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return this.childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return this.dispatchNestedPreFling(velocityX, velocityY);
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return this.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public void fling(int velocityY) {
        super.fling(velocityY);
    }

}