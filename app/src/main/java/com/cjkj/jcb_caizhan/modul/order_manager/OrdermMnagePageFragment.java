package com.cjkj.jcb_caizhan.modul.order_manager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.flyco.tablayout.SlidingTabLayout;
import butterknife.Bind;
/**
 * Created by 1 on 2018/1/15.
 * 订单管理
 */
public class OrdermMnagePageFragment extends RxLazyFragment {

    @Bind(R.id.base_title)
    TextView base_title;

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;

    public static OrdermMnagePageFragment newInstance() {
        return new OrdermMnagePageFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ordermanager_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initViewPager();
    }

    private void initViewPager() {
        OrdermMnagePagerAdapter mAdapter = new OrdermMnagePagerAdapter(getChildFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

}
