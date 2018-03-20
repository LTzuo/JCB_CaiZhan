package com.cjkj.jcb_caizhan.modul.Personal_Center.withdrawals;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.utils.IntentUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 提现记录
 */
public class WithdrawalsHistoryActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    WithdrawalsListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawals_history;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getContext(), R.color.bg_f3f2f7)));
        mAdapter = new WithdrawalsListAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("双色球即将开奖,奖池金额高达3亿元");
        }
        mAdapter.setInfo(dataList);

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                IntentUtils.Goto(WithdrawalsHistoryActivity.this,WithdrawalsDetailsActivity.class);
            }
        });
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("提现记录");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
