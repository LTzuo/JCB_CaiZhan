package com.cjkj.jcb_caizhan.modul.personal_center.to_door_ticket.todoor;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * 上门取票
 * Created by 1 on 2018/3/5.
 */
public class ToDoorTicketFragment extends RxLazyFragment{

    public static ToDoorTicketFragment newIntance(){
        return new ToDoorTicketFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_test;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
