package com.cjkj.jcb_caizhan.ui.fragment.order_manager;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.order_manager.CrowdEntity;
import com.cjkj.jcb_caizhan.ui.adapter.order_manager.CrowdAdapter;
import com.cjkj.jcb_caizhan.ui.fragment.RxLazyFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

/**
 * 众筹
 * Created by 1 on 2018/2/22.
 */
public class CrowdfundFragment extends RxLazyFragment {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    CrowdAdapter mAdapter;

    List<CrowdEntity> mDatas = new ArrayList<>();

    public static CrowdfundFragment newIntance() {
        return new CrowdfundFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_crowd;
    }

    @Override
    public void finishCreateView(Bundle state) {
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
//        Observable.just(mDatas)
//                .compose(bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mDatas -> {
//                    mAdapter.setInfo(mDatas);
        finishTask();
//                }, throwable -> {
//                });
    }

    @Override
    protected void finishTask() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1 * 1000);
    }

}
