package com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.history.HistoryCashPrizeFragment;
import com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.now.NowCashPrizeFragment;

/**
 * 个人中心-委托兑奖pager适配器
 * Created by 1 on 2018/2/24.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public CustomPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.CashPrizeValues);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = NowCashPrizeFragment.newIntance();
                    break;
                case 1:
                    fragments[position] = HistoryCashPrizeFragment.newIntance();
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
