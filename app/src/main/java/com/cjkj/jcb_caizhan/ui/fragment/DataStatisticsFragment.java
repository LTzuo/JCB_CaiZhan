package com.cjkj.jcb_caizhan.ui.fragment;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * Created by 1 on 2018/1/16.
 * 数据统计
 */
public class DataStatisticsFragment extends RxLazyFragment{

    public static DataStatisticsFragment newInstance() {
        return new DataStatisticsFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_data_statistics;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
