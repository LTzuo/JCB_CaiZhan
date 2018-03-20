package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket;

import android.os.Bundle;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 本期已完成
 * Created by 1 on 2018/2/23.
 */
public class OverFragment extends RxLazyFragment{

    public static OverFragment newIntance() {
        return new OverFragment();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ssq_over_ticket;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
