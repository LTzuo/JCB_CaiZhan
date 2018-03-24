package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
/**
 * 众筹
 * Created by 1 on 2018/2/22.
 */
public class CrowdfundFragment extends RxLazyFragment implements CrowdContract.ICrowdView{

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    CrowdAdapter mAdapter;

    List<CrowdEntity> mDatas = new ArrayList<>();

    int index = 1;

    public static CrowdfundFragment newIntance() {
        return new CrowdfundFragment();
    }

    CrowdPresenter mCrowdPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_crowd;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mCrowdPresenter = new CrowdPresenter(this);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!isPrepared || !isVisible) {
            return;
        }
        initstatusManagerLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initstatusManagerLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new CrowdAdapter(mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mCrowdPresenter.getCrowdList(SPUtil.get(getContext(), Constants.key_uSessionId,"").toString(),
                "0",index);
    }

    @Override
    public void ListSuccessFul(List<CrowdEntity> crowdList) {
        mAdapter.setInfo(crowdList);
        finishTask();
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

    @Override
    public void Filed(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    protected void finishTask() {
       mSwipeRefreshLayout.setRefreshing(false);
    }

}
