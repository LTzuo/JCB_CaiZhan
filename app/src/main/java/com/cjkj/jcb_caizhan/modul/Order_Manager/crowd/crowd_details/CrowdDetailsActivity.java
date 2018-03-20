package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.crowd_details;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.CustomTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 众筹详情
 */
public class CrowdDetailsActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.mCustomTextView)
    CustomTextView mCustomTextView;

    @OnClick({R.id.mBtnChenge})
    public void OnBtnClick(View v){
        if(v.getId() == R.id.mBtnChenge){
            ToastUtil.ShortToast("修改众筹方案");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_crowd_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initRefreshLayout();
        //mCustomTextView.setSolidColor();
    }

    @Override
    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    public void loadData() {
        finishTask();
    }

    @Override
    public void finishTask() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1 * 1000);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("众筹详情");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
