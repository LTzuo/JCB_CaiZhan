package com.cjkj.jcb_caizhan.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.ui.fragment.orderManager.OrderFragment;
import com.cjkj.jcb_caizhan.ui.fragment.orderManager.TogetherBuyFragment;

/**
 * 订单管理界面Fragment模块Adapter
 */
public class OrdermMnagePagerAdapter extends FragmentPagerAdapter {

  private final String[] TITLES;
  private Fragment[] fragments;

  public OrdermMnagePagerAdapter(FragmentManager fm, Context context) {
    super(fm);
    TITLES = context.getResources().getStringArray(R.array.sections);
   // TITLES = new String[]{"订单(1)","合买","跟单","合作"};
    fragments = new Fragment[TITLES.length];
  }


  @Override
  public Fragment getItem(int position) {
    if (fragments[position] == null) {
      switch (position) {
        case 0:
          fragments[position] = OrderFragment.newIntance();
          break;
        case 1:
          fragments[position] = TogetherBuyFragment.newIntance();
          break;
        case 2:
          fragments[position] = OrderFragment.newIntance();
          break;
        default:
          break;
      }
    }
    return fragments[position];
  }


  @Override
  public int getCount() {
    return TITLES.length;
  }


  @Override
  public CharSequence getPageTitle(int position) {
    return TITLES[position];
  }
}
