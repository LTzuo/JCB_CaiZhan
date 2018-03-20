package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.flyco.tablayout.SlidingTabLayout;
import butterknife.Bind;
/**
 * 订单管理-订单
 */
public class TicketActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    private String lotteryTypeid;
    @Override
    public int getLayoutId() {
        return R.layout.activity_ticket;
    }

    public String getLotteryTypeid(){
        return lotteryTypeid;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        lotteryTypeid = getIntent().getStringExtra("lotteryTypeid");

        TicketPagerAdapter mAdapter = new TicketPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText(lotteryTypeid.equals("4")?"双色球":lotteryTypeid.equals("5")?"3D":lotteryTypeid.equals("6")?
                "七乐彩":lotteryTypeid.equals("7")?"大乐透":lotteryTypeid.equals("8")?"排列三":lotteryTypeid.equals("9")?
                "排列五":lotteryTypeid.equals("10")?"七星彩":lotteryTypeid.equals("12")?"胜负彩":lotteryTypeid.equals("13")?
                "任选九":lotteryTypeid.equals("14")?"半全场":lotteryTypeid.equals("15")?"进球彩":lotteryTypeid.equals("16")?
                "竞彩足球":lotteryTypeid.equals("17")?"竞彩篮球":"不知道啥球");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

}
