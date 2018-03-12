package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.over_ticket;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 双色球-本期已完成
 * Created by 1 on 2018/2/23.
 */
public class SSQ_Over_Ticket extends RxLazyFragment{

    public static SSQ_Over_Ticket newIntance() {
        return new SSQ_Over_Ticket();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ssq_over_ticket;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
