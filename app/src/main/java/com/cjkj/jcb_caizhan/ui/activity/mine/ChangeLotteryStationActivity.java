package com.cjkj.jcb_caizhan.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.mine.changelotterystation.ChangeLotteryStationBean;
import com.cjkj.jcb_caizhan.ui.activity.RxBaseActivity;
import com.cjkj.jcb_caizhan.ui.adapter.mine.changelotterystation.ChangeLotteryStationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 切换彩站
 */
public class ChangeLotteryStationActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    ChangeLotteryStationAdapter mAdapter;
    List<ChangeLotteryStationBean> mDatas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_lottery_station;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new ChangeLotteryStationAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }

    @Override
    public void loadData() {
        mDatas.add(new ChangeLotteryStationBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4285405491,434758780&fm=27&gp=0.jpg",
                "林天佐福利彩票1站", "林天佐", "13943025871"));
        mDatas.add(new ChangeLotteryStationBean("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3499809529,2590974592&fm=27&gp=0.jpg",
                "林天佐福利彩票2站", "林天佐", "13943025871"));
        mDatas.add(new ChangeLotteryStationBean("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3975696287,48046482&fm=27&gp=0.jpg",
                "林天佐福利彩票3站", "林天佐", "13943025871"));
        mDatas.add(new ChangeLotteryStationBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1518080813871&di=4698f48742e13a8aae20343b11575e7a&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3739602253%2C912892566%26fm%3D214%26gp%3D0.jpg",
                "林天佐福利彩票4站", "林天佐", "13943025871"));
        mDatas.add(new ChangeLotteryStationBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=160915956,1481856262&fm=27&gp=0.jpg",
                "林天佐福利彩票5站", "林天佐", "13943025871"));
        mAdapter.setInfo(mDatas);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("切换彩站");// 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

}
