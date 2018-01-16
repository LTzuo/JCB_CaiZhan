package com.cjkj.jcb_caizhan.fragment;

import android.os.Bundle;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

/**
 * Created by 1 on 2018/1/16.
 * 用户管理
 */
public class UserManagementFragment extends RxLazyFragment{


    public static UserManagementFragment newInstance() {
        return new UserManagementFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_management;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
