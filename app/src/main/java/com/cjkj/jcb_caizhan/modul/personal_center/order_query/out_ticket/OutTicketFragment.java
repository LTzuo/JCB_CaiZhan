package com.cjkj.jcb_caizhan.modul.personal_center.order_query.out_ticket;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 订单查询-出票
 * Created by 1 on 2018/2/26.
 */
public class OutTicketFragment extends RxLazyFragment{

    public static OutTicketFragment newIntance() {
        return new OutTicketFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragmetn_outticket;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
