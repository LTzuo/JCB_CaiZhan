package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.caizhong;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.CustomPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import butterknife.Bind;

/**
 * 委托兑奖-全部彩种
 * Created by 1 on 2018/2/28.
 */
public class CaiZhong_All_Fragment extends RxLazyFragment{

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    public static CaiZhong_All_Fragment newIntance() {
        return new CaiZhong_All_Fragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_custom_chenge_caizhong;
    }

    @Override
    public void finishCreateView(Bundle state) {
        CustomPagerAdapter mAdapter = new CustomPagerAdapter(getChildFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

}
