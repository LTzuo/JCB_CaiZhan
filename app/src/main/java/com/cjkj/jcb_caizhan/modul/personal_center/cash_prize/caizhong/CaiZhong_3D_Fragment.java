package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.caizhong;

import android.os.Bundle;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

import butterknife.Bind;

/**
 * 委托兑奖-福彩3D
 * Created by 1 on 2018/2/28.
 */
public class CaiZhong_3D_Fragment extends RxLazyFragment{

    @Bind(R.id.text)
    TextView text;

    public static CaiZhong_3D_Fragment newIntance() {
        return new CaiZhong_3D_Fragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    public void finishCreateView(Bundle state) {
        text.setText("福彩3D");
    }
}
