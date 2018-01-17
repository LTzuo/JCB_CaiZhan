package com.cjkj.jcb_caizhan.ui.fragment.orderManager;

import android.os.Bundle;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * Created by 1 on 2018/1/15.
 * 订单管理—合买
 */
public class TogetherBuyFragment extends RxLazyFragment{

    public static TogetherBuyFragment newIntance() {
        return new TogetherBuyFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_togetherbuy;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }

}
