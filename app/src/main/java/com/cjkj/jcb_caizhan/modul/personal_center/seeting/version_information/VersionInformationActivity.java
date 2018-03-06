package com.cjkj.jcb_caizhan.modul.personal_center.seeting.version_information;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.utils.CommonUtil;

import butterknife.Bind;

/**
 * 版本信息
 */
public class VersionInformationActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.tv_app_version)
    TextView tv_app_version;
    @Bind(R.id.tv_app_name)
    TextView tv_app_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_version_information;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        tv_app_name.setText(getString(R.string.app_name));
        tv_app_version.setText("v"+CommonUtil.getLocalVersionName(this));
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("版本信息");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
