package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket.OverFragment;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.normal.WaitFragment;

/**
 * 数字彩，进球彩打票pager适配器
 * Created by 1 on 2018/3/9.
 */
public class TicketPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public TicketPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.order_arrays);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = WaitFragment.newIntance();
                    break;
                case 1:
                    fragments[position] = OverFragment.newIntance();
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
