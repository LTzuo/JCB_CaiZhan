package com.cjkj.jcb_caizhan.modul.personal_center.order_query;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.personal_center.order_query.out_ticket.OutTicketFragment;
import com.cjkj.jcb_caizhan.modul.personal_center.withdrawals.withdrawals_list.WithdrawalsListFragment;

/**
 * 个人中心-订单查询pager适配器
 * Created by 1 on 2018/2/24.
 */
public class OrderQueryPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public OrderQueryPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.personal_center_OrderQuery);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = OutTicketFragment.newIntance();
                    break;
                case 1:
                    fragments[position] = OutTicketFragment.newIntance();
                    break;
                case 2:
                    fragments[position] = OutTicketFragment.newIntance();
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
