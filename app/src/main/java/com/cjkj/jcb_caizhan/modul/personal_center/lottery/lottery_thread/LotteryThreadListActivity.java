package com.cjkj.jcb_caizhan.modul.personal_center.lottery.lottery_thread;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

/**
 * 走势图列表页
 */
public class LotteryThreadListActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    LotteryThreadListAdapter mAdapter;
    List<String> mDatas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_lottery_thread_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new LotteryThreadListAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        loadData();

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
               // IntentUtils.Goto(LotteryThreadListActivity.this,LotteryTrendActivity.class);
                Intent intent = new Intent(LotteryThreadListActivity.this,LotteryTrendActivity.class);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {
        mDatas.add("双色球");
        mDatas.add("3D");
        mDatas.add("七乐彩");
        mDatas.add("大乐透");
        mDatas.add("排列三");
        mDatas.add("排列五");
        mDatas.add("七星彩");
        mAdapter.setInfo(mDatas);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("走势图");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
