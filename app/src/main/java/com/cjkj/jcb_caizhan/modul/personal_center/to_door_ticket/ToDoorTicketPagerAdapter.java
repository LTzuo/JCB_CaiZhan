package com.cjkj.jcb_caizhan.modul.personal_center.to_door_ticket;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.personal_center.to_door_ticket.todoor.ToDoorTicketFragment;

/**
 * 个人中心-上门取票pager适配器
 * Created by 1 on 2018/2/24.
 */
public class ToDoorTicketPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public ToDoorTicketPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.todoorticket_arrays);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = ToDoorTicketFragment.newIntance();
                    break;
                case 1:
                    fragments[position] = ToDoorTicketFragment.newIntance();
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
