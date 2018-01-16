package com.cjkj.jcb_caizhan.fragment.orderManager;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 订单管理-订单
 * Created by 1 on 2018/1/15.
 */
public class OrderFragment extends RxLazyFragment{

    public static OrderFragment newIntance() {
        return new OrderFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }


}
