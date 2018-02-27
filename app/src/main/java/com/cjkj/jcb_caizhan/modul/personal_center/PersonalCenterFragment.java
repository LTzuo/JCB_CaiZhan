package com.cjkj.jcb_caizhan.modul.personal_center;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.personal_center.account_details.AccountDetailsActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.CashPrizeActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.chenge_station.ChangeLotteryStationActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.launch_crowd.LaunchCrowdfundingActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.lottery.AwardResultActivity;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.modul.personal_center.mine_message.MineMessageActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.order_query.OrderQueryActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.recharge.RechargeActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.seeting.SeetingActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.withdrawals.WithdrawalsActivity;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
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
public class PersonalCenterFragment extends RxLazyFragment implements ObservableScrollViewCallbacks {

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

    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Bind(R.id.item_avatar)
    ImageView img_header;

    private int mFlexibleSpaceHeight;

    public static PersonalCenterFragment newInstance() {
        return new PersonalCenterFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.lin1,R.id.layout_chenge,R.id.layout_seeting,R.id.layout_launchCrowdfunding,R.id.Layout_recharge,R.id.layout_Withdrawals
             ,R.id.Layout_message,R.id.Layout_OrderQuery,R.id.Layout_AccountDetails,R.id.Layout_Duijiang})
    public void BtnClick(View v) {
        if (v.getId() == R.id.lin1) {
            //开奖结果
          //IntentUtils.Goto(getActivity(), LotteryCategoryActivity.class);
            IntentUtils.Goto(getActivity(), AwardResultActivity.class);
        } else if (v.getId() == R.id.layout_seeting) {
            //设置
            IntentUtils.Goto(getActivity(), SeetingActivity.class);
        }else if(v.getId() == R.id.layout_chenge){
            //切换彩站
            IntentUtils.Goto(getActivity(), ChangeLotteryStationActivity.class);
        }else if(v.getId() == R.id.layout_launchCrowdfunding){
            //发起众筹
            IntentUtils.Goto(getActivity(), LaunchCrowdfundingActivity.class);
        }else if(v.getId() == R.id.Layout_recharge){
            //充值
            IntentUtils.Goto(getActivity(),RechargeActivity.class);
        }else if(v.getId() == R.id.layout_Withdrawals){
            //提现
            IntentUtils.Goto(getActivity(),WithdrawalsActivity.class);
        }else if(v.getId() == R.id.Layout_message){
            //我的消息
            IntentUtils.Goto(getActivity(),MineMessageActivity.class);
        }else if(v.getId() == R.id.Layout_OrderQuery){
            //订单查询
            IntentUtils.Goto(getActivity(),OrderQueryActivity.class);
        }else if(v.getId() == R.id.Layout_AccountDetails){
            //账户明细
            IntentUtils.Goto(getActivity(),AccountDetailsActivity.class);
        }else if(v.getId() == R.id.Layout_Duijiang){
            //委托兑奖
            IntentUtils.Goto(getActivity(),CashPrizeActivity.class);
        }
    }

    @Override
    public void finishCreateView(Bundle state) {
        mObservableScrollView.setScrollViewCallbacks(PersonalCenterFragment.this);
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