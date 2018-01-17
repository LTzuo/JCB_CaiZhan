package com.cjkj.jcb_caizhan.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.adapter.RetfitTestAdapter;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.CustomEmptyView;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 2018/1/16.
 * 用户管理
 */
public class UserManagementFragment extends RxLazyFragment{

    @Bind(R.id.recycle)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    RetfitTestAdapter mTestAdapter;
    public static UserManagementFragment newInstance() {
        return new UserManagementFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_management;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {
        mTestAdapter = new RetfitTestAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mTestAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getTestApi()
                .getDatas("福利",5,1)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Info -> {
                    mTestAdapter.setTestInfo(Info.getResults());
                    finishTask();
                }, throwable -> initEmptyView());
    }

    private void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.ic_launcher);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        ToastUtil.ShortToast("数据加载失败,请重新加载或者检查网络是否链接");
    }

    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void finishTask() {
        hideEmptyView();
        mSwipeRefreshLayout.setRefreshing(false);
        mTestAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

}
