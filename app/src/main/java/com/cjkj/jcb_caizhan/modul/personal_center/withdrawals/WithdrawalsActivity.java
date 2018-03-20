package com.cjkj.jcb_caizhan.modul.Personal_Center.withdrawals;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.flyco.tablayout.SlidingTabLayout;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人中心-提现
 */
public class WithdrawalsActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_custom)
    TextView toolbar_custom;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    @OnClick({R.id.toolbar_custom})
    public void OnBtnClick(View v){
        if(v.getId() == R.id.toolbar_custom){
            IntentUtils.Goto(this,WithdrawalsHistoryActivity.class);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawals;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        WithdrawalsPagerAdapter mAdapter = new WithdrawalsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("提现");
        toolbar_custom.setText("记录");
        toolbar_custom.setVisibility(View.VISIBLE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

}
