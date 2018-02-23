package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.error_ticket;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 双色球-大票错误
 * Created by 1 on 2018/2/23.
 */
public class SSQ_Error_Ticket extends RxLazyFragment{

    public static SSQ_Error_Ticket newIntance() {
        return new SSQ_Error_Ticket();
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ssq_error_ticket;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
