package com.cjkj.jcb_caizhan.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.cjkj.jcb_caizhan.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment通用管理类
 * 适合多fragment,特别是10个以上的fragment页面
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

    /**
     * 添加与切换Fragment
     * @param position
     */
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
