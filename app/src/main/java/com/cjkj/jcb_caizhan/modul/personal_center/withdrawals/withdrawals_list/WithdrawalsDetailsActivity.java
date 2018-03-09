package com.cjkj.jcb_caizhan.modul.personal_center.withdrawals.withdrawals_list;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.widget.CheckBox.SmoothCheckBox;

import butterknife.Bind;

/**
 * 个人中心-提现详情
 */
public class WithdrawalsDetailsActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.mSmoothCheckBox)
    SmoothCheckBox mSmoothCheckBox;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawals_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSmoothCheckBox.setChecked(true,true);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("提现详情");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
