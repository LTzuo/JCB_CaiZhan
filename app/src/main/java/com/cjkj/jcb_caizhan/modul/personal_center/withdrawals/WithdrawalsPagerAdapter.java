package com.cjkj.jcb_caizhan.modul.Personal_Center.withdrawals;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Personal_Center.withdrawals.alipay.W_AlipayFragment;
/**
 * 个人中心-提现pager适配器
 * Created by 1 on 2018/2/24.
 */
public class WithdrawalsPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private Fragment[] fragments;

    public WithdrawalsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.personal_center_withdrawals);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = W_AlipayFragment.newIntance();
                    break;
                case 1:
                    fragments[position] = W_AlipayFragment.newIntance();
                    break;
                case 2:
                    fragments[position] = W_AlipayFragment.newIntance();
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
