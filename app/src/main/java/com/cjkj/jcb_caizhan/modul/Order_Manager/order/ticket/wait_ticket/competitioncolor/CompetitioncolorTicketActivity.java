package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.CompetitionTicketPagerAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.Bind;

/**
 * 打票-竞彩
 */
public class CompetitioncolorTicketActivity extends RxBaseActivity {

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
        return R.layout.activity_competitioncolor_ticket;
    }

    public String getLotteryTypeid(){
        return lotteryTypeid;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        lotteryTypeid = getIntent().getStringExtra("lotteryTypeid");

        CompetitionTicketPagerAdapter mAdapter = new CompetitionTicketPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText(lotteryTypeid.equals("16")? "竞彩足球":lotteryTypeid.equals("17")?"竞彩篮球":"不知道啥球");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }


}
