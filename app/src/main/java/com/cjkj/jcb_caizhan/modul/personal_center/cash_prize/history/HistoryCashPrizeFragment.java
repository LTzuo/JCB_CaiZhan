package com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.history;

import android.os.Bundle;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

import butterknife.Bind;

/**
 * 委托兑奖-历史委托
 * Created by 1 on 2018/2/28.
 */
public class HistoryCashPrizeFragment extends RxLazyFragment{

    @Bind(R.id.text)
    TextView text;

    public static HistoryCashPrizeFragment newIntance() {
        return new HistoryCashPrizeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    public void finishCreateView(Bundle state) {
        text.setText("历史委托");
    }
}
