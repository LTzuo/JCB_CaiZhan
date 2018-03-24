package com.cjkj.jcb_caizhan.modul.Personal_Center.launch_crowd;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdContract;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdPresenter;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 众筹历史
 */
public class CrowdHistoryActivity extends RxBaseActivity implements CrowdContract.ICrowdView {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    CrowdAdapter mAdapter;

    CrowdPresenter mCrowdPresenter;

    List<CrowdEntity> mDatas = new ArrayList<>();

    int index = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_crowd_history;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mCrowdPresenter = new CrowdPresenter(this);
        initRefreshLayout();
        initRecyclerView();
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
    public void initRecyclerView() {
        mAdapter = new CrowdAdapter(mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {
        mCrowdPresenter.getCrowdList(SPUtil.get(this, Constants.key_uSessionId, "").toString(),
                "1", index);
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("众筹历史");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    public void Filed(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void ListSuccessFul(List<CrowdEntity> crowdList) {
        mAdapter.setInfo(crowdList);
        finishTask();
    }

    @Override
    public void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void DetilsSuccessFul(CrowdEntity mCrowdEntity) {
        //无返回
    }

    @Override
    public void PutSuccessFul(String msg) {
        //无返回
    }

    @Override
    public void UpdateCrowdSuccessFul(String msg) {
        //无返回
    }
}
