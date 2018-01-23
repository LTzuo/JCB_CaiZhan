package com.cjkj.jcb_caizhan.ui.fragment.userManagement;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.ui.adapter.RetfitTestAdapter;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.ui.fragment.RxLazyFragment;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.ui.widget.CustomEmptyView;

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

    int page = 1;
    boolean isLoadmore = false;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && !mSwipeRefreshLayout.isRefreshing()) {
                    showRefreshing(true);
                }
            }
        });
       mRecyclerView.setAdapter(mTestAdapter);
    }

    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isLoadmore = false;
                loadData();
            }
        });
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            page = 1;
            isLoadmore = false;
            loadData();
        });
    }

    @Override
    protected void loadData() {
        ToastUtil.ShortToast("页码："+page+"===总数据条数"+mTestAdapter.getItemCount());
        RetrofitHelper.getTestApi()
                .getDatas("福利",20,page)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Info -> {
                    if(!isLoadmore){
                        mTestAdapter.setTestInfo(Info.getResults());
                    }else{
                        mTestAdapter.addInfo(mTestAdapter.getItemCount(),Info.getResults());
                    }
                    finishTask();
                }, throwable -> initEmptyView());
    }

    protected void showRefreshing(final boolean refresh) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                page ++;
                isLoadmore = true;
                mSwipeRefreshLayout.setRefreshing(refresh);
                loadData();
            }
        });
    }

    private void initEmptyView() {
        isLoadmore = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.ic_launcher);
        mCustomEmptyView.setEmptyText("加载失败~~(≧▽≦)~~啦啦啦.");
        ToastUtil.ShortToast("数据加载失败,请重新加载或者检查网络是否链接");
    }

    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void finishTask() {
        isLoadmore = false;
        hideEmptyView();
        mSwipeRefreshLayout.setRefreshing(false);
        mTestAdapter.notifyDataSetChanged();
       // mRecyclerView.scrollToPosition(0);
    }

}
