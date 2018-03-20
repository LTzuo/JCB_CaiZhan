package com.cjkj.jcb_caizhan.modul.Personal_Center.withdrawals.alipay;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 提现到支付宝
 * Created by 1 on 2018/3/14.
 */
public class W_AlipayFragment extends RxLazyFragment {

    public static W_AlipayFragment newIntance() {
        return new W_AlipayFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
