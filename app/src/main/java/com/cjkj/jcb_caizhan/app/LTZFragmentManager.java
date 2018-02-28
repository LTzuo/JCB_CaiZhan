package com.cjkj.jcb_caizhan.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_3D_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_7lc_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_7xc_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_All_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_dlt_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_jclq_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_jczq_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_pl3_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_pl5_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_rx9c_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_sfgg_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_zqdc_Fragment;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment.CaiZhong_zqsf_Fragment;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment管理类
 * Fragment的添加与替换
 * Created by 1 on 2018/2/28.
 */
public class LTZFragmentManager {

    private AppCompatActivity mContext;

    private Fragment currentFragment;

    private List<Fragment> FragmentList = new ArrayList<>();

    public LTZFragmentManager(AppCompatActivity context,List<Fragment> fragmentList) {
        this.mContext = context;
        this.FragmentList = fragmentList;

    }

    //切换Fragment
    public void swichFragment(int position) {
        switchFragment(FragmentList.get(position));
    }

    /**
     * 正确的切换Fragment
     * 当currentFragment为空时就添加第一个Fragment
     * 如果currentFragment没有被添加过就新添加，如果添加过就替换
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = mContext.getSupportFragmentManager().beginTransaction();
        if (currentFragment == null) {
            transaction.add(R.id.content_layout, targetFragment).commit();
        } else {
            if (!targetFragment.isAdded()) {
                transaction.hide(currentFragment).add(R.id.content_layout, targetFragment).commit();
            } else {
                transaction.hide(currentFragment).show(targetFragment).commit();
            }
        }
        currentFragment = targetFragment;
    }

    /**
     * 移除所有Fragment
     */
    public void removeFragments() {
        for (Fragment fragment : FragmentList) {
            mContext.getSupportFragmentManager().beginTransaction().remove(fragment);
        }
    }

}
