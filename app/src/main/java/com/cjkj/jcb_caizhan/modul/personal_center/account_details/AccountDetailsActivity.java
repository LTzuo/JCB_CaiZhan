package com.cjkj.jcb_caizhan.modul.personal_center.account_details;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.Bind;

/**
 * 个人中心-账户明细
 */
public class AccountDetailsActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_query;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        AccountDetailsPagerAdapter mAdapter = new AccountDetailsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("账户明细");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
