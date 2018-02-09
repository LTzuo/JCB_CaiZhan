package com.cjkj.jcb_caizhan.ui.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.ui.activity.mine.ChangeLotteryStationActivity;
import com.cjkj.jcb_caizhan.ui.activity.mine.SeetingActivity;
import com.cjkj.jcb_caizhan.ui.activity.mine.lottery.LotteryCategoryActivity;
import com.cjkj.jcb_caizhan.ui.fragment.RxLazyFragment;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 1 on 2018/1/15.
 * 个人中心
 */
public class MineFragment extends RxLazyFragment implements ObservableScrollViewCallbacks {

    @Bind(R.id.sv_main_content)
    ObservableScrollView mObservableScrollView;
    @Bind(R.id.ll_toolbar)
    LinearLayout ll_toolbar;
    @Bind(R.id.ll_header_content)
    RelativeLayout mHeaderContent;
    @Bind(R.id.ll_content)
    LinearLayout mContent;
    @Bind(R.id.tv_header_title)
    TextView tv_header_title;
    @Bind(R.id.tv_title)
    TextView tv_title;

    //@Bind(R.id.swipe_refresh_layout)
    //SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Bind(R.id.item_avatar)
    ImageView img_header;

    private int mFlexibleSpaceHeight;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.lin1,R.id.layout_chenge,R.id.layout_seeting})
    public void BtnClick(View v) {
        if (v.getId() == R.id.lin1) {
            Intent i = new Intent(getContext(), LotteryCategoryActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.layout_seeting) {
            IntentUtils.Goto(getActivity(), SeetingActivity.class);
        }else if(v.getId() == R.id.layout_chenge){
            IntentUtils.Goto(getActivity(), ChangeLotteryStationActivity.class);
        }
    }

    @Override
    public void finishCreateView(Bundle state) {
        mObservableScrollView.setScrollViewCallbacks(MineFragment.this);
        mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.minefragment_header_hight);
        isPrepared = true;
        lazyLoad();
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
        mRefreshLayout.autoRefresh();
        //mRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));//Material风格
       // mRefreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));//水滴
        // mRefreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()));//雷达
         mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));//正常刷新
        // mRefreshLayout.setRefreshHeader(new DeliveryHeader(getActivity()));//热气球
        //mRefreshLayout.setRefreshHeader(new DropboxHeader(getActivity()));//装箱子
        //mRefreshLayout.setRefreshHeader(new FunGameBattleCityHeader(getActivity()));//坦克大战
        //mRefreshLayout.setRefreshHeader(new PhoenixHeader(getActivity()));//city
        //设置主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.colorPrimary, R.color.whitesmoke);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData();
            }
        });
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
//        mSwipeRefreshLayout.post(() -> {
//            mSwipeRefreshLayout.setRefreshing(true);
//        loadData();
//        });
    }

    @Override
    protected void loadData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishTask();
            }
        }, 1 * 1000);
    }

    @Override
    protected void finishTask() {
        Glide.with(getContext())
                .load(R.drawable.default_lottery)
                .centerCrop()
                .crossFade()
                .into(img_header);
        ToastUtil.ShortToast("刷新完成");
//        mSwipeRefreshLayout.setRefreshing(false);
        mRefreshLayout.finishRefresh(true);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        onScrollChanged(mObservableScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        float alpha = Math.min(1, (float) scrollY / mFlexibleSpaceHeight);
       // mHeaderContent.setBackgroundColor(ScrollUtils.getColorWithAlpha(1-alpha, getResources().getColor(R.color.colorPrimary)));
        tv_header_title.setTextColor(ScrollUtils.getColorWithAlpha(alpha, getResources().getColor(R.color.white)));
        tv_title.setTextColor(ScrollUtils.getColorWithAlpha(1 - alpha, getResources().getColor(R.color.white)));
       // ViewHelper.setTranslationY(mHeaderContent, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
//        mSwipeRefreshLayout.setNestedScrollingEnabled(false);
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//        mSwipeRefreshLayout.setNestedScrollingEnabled(true);
//        if(scrollState == ScrollState.UP){
//            ViewHelper.setScaleX(img_header,(float)0.0001);
//            ViewHelper.setScaleY(img_header,(float)0.0001);
//        }else if(scrollState == ScrollState.DOWN){
//            ViewHelper.setScaleX(img_header,1);
//            ViewHelper.setScaleY(img_header,1);
//        }
    }

}