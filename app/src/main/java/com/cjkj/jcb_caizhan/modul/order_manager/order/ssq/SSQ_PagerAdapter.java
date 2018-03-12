package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.over_ticket.SSQ_Over_Ticket;
import com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.wait_ticket.SSQ_Wait_Ticket;

/**
 * Created by 1 on 2018/3/9.
 */
public class SSQ_PagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public SSQ_PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.order_arrays);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = SSQ_Wait_Ticket.newIntance();
                    break;
                case 1:
                    fragments[position] = SSQ_Over_Ticket.newIntance();
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
