package com.cjkj.jcb_caizhan.ui.fragment.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.ui.activity.mine.lottery.LotteryCategoryActivity;
import com.cjkj.jcb_caizhan.ui.fragment.RxLazyFragment;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.ui.widget.ObservableScrollView;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 1 on 2018/1/15.
 * 个人中心
 */
public class MineFragment  extends RxLazyFragment implements ObservableScrollView.OnObservableScrollViewListener  {

    @Bind(R.id.sv_main_content)
    ObservableScrollView mObservableScrollView;
    @Bind(R.id.ll_header_content)
    LinearLayout mHeaderContent;
    @Bind(R.id.ll_content)
    LinearLayout mContent;
    @Bind(R.id.tv_header_title)
    TextView tv_header_title;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.item_avatar)
    ImageView img_header;
    private int mHeight;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @OnClick(R.id.lin1)
    public void BtnClick(View v){

        Intent i = new Intent(getContext(),LotteryCategoryActivity.class);
        startActivity(i);
    }

    @Override
    public void finishCreateView(Bundle state) {
        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = mHeaderContent.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = mHeaderContent.getHeight() - mContent.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                mObservableScrollView.setOnObservableScrollViewListener(MineFragment.this);
            }
        });
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
           // mHeaderContent.setBackgroundColor(Color.argb(225, 75, 137, 220));
            tv_header_title.setTextColor(Color.argb(0, 48, 63, 159));
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
          //  mHeaderContent.setBackgroundColor(Color.argb((225-(int) alpha), 75, 137, 220));
            tv_header_title.setTextColor(Color.argb((int) alpha, 238, 239, 255));
        } else {
            //过顶部图区域，标题栏定色
           // mHeaderContent.setBackgroundColor(Color.argb(255, 75, 137, 220));
            tv_header_title.setTextColor(Color.argb(255,238, 239, 255));
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initstatusManagerLayout();
        isPrepared = false;
    }

    @Override
    protected void initstatusManagerLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    protected void loadData() {
       mHandler.postDelayed(new Runnable() {
           @Override
           public void run() {
              finishTask();
           }
       },1*1000);
    }

    @Override
    protected void finishTask() {
//     Glide.with(getContext())
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516705014262&di=d0a4d7f5d6255786fbdad18d9950ef71&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2F20%2F2093%2F209378%2F20937885_1200x1000_0.jpg")
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(img_header);
        Glide.with(getContext())
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516705014262&di=d0a4d7f5d6255786fbdad18d9950ef71&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2F20%2F2093%2F209378%2F20937885_1200x1000_0.jpg")
                .centerCrop()
                .crossFade()
                .into(img_header);
        ToastUtil.ShortToast("刷新完成");
        mSwipeRefreshLayout.setRefreshing(false);
    }

}