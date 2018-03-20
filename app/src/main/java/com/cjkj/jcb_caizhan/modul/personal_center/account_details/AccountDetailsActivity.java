package com.cjkj.jcb_caizhan.modul.Personal_Center.account_details;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;

import butterknife.Bind;

/**
 * 个人中心-账户明细
 */
public class AccountDetailsActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    AccountDetailsAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_message;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new AccountDetailsAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("账户明细");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
