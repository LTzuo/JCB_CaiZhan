package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

import butterknife.Bind;

/**
 * 委托兑奖-大乐透
 * Created by 1 on 2018/2/28.
 */
public class CaiZhong_dlt_Fragment extends RxLazyFragment{

    @Bind(R.id.text)
    TextView text;

    public static CaiZhong_dlt_Fragment newIntance() {
        return new CaiZhong_dlt_Fragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_custom_chenge_caizhong;
    }

    @Override
    public void finishCreateView(Bundle state) {
        text.setText("大乐透");
    }
}
