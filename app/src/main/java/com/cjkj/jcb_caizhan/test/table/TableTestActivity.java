package com.cjkj.jcb_caizhan.test.table;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 测试activity
 */
public class TableTestActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    TableRecyclerViewTestAdapter mTableTestAdapter;

    List<TableTextEntity> mDatas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.test_activity_table;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mTableTestAdapter = new TableRecyclerViewTestAdapter(mRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mTableTestAdapter);
        loadData();
    }

    @Override
    public void loadData() {
        mDatas.add(new TableTextEntity("林天佐","营长","1000万","10.0%","10万"));
        mDatas.add(new TableTextEntity("林天佑","师长","1000万","10.0%","10万"));
        mDatas.add(new TableTextEntity("林亮","军长","9000万","80.0%","90万"));
        mTableTestAdapter.setInfo(mDatas);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("表格测试");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
