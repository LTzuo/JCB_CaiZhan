package com.cjkj.jcb_caizhan.ui.fragment.dataStatisics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.TestInfo;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.ui.adapter.RetfitTestAdapter;
import com.cjkj.jcb_caizhan.ui.fragment.RxLazyFragment;
import com.cjkj.jcb_caizhan.ui.widget.CustomEmptyView;
import com.cjkj.jcb_caizhan.ui.widget.swiperecyclerview.SwipeRecyclerView;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.bakumon.statuslayoutmanager.library.DefaultOnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 2018/1/16.
 * 数据统计
 */
public class DataStatisticsFragment extends RxLazyFragment implements SwipeRecyclerView.OnLoadListener {

    @Bind(R.id.swipeRecyclerView)
    SwipeRecyclerView mSwipRecyclerView;

    RetfitTestAdapter mTestAdapter;
    List<TestInfo.ResultsBean> mDatas = new ArrayList<>();
    private int page = 1;
    StatusLayoutManager mStatusLayoutManager;

    public static DataStatisticsFragment newInstance() {
        return new DataStatisticsFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_data_statistics;
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
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRefreshLayout() {
        mStatusLayoutManager = new StatusLayoutManager.Builder(mSwipRecyclerView)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new DefaultOnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        loadData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        loadData();
                    }
                })
                .build();
    }

    @Override
    protected void initRecyclerView() {
        mTestAdapter = new RetfitTestAdapter(mSwipRecyclerView.getRecyclerView());
        mSwipRecyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        //设置网络处理
        //mSwipRecyclerView.onNetChange(true);
        //设置错误信息
        //recyclerView.onError("error");
        mSwipRecyclerView.setOnLoadListener(this);
        mSwipRecyclerView.setAdapter(mTestAdapter);
        mSwipRecyclerView.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData();
    }

    @Override
    protected void loadData() {
        RetrofitHelper.getTestApi()
                .getDatas("福利", 10, page)
                .compose(bindToLifecycle())
                .cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Info -> {
                    if (page == 1) {
                        mDatas = Info.getResults();
                        if (mDatas.isEmpty()) mStatusLayoutManager.showEmptyLayout();
                    } else {
                        if (Info.getResults().isEmpty())
                            mSwipRecyclerView.onNoMore("-- 到底啦 --");
                        for (TestInfo.ResultsBean bean : Info.getResults()) {
                            mDatas.add(bean);
                        }
                    }
                    mTestAdapter.setTestInfo(mDatas);
                    finishTask();
                }, throwable -> mStatusLayoutManager.showErrorLayout());
    }

    @Override
    protected void finishTask() {
        if (page == 1) {
            mSwipRecyclerView.complete();
        } else {
            mSwipRecyclerView.stopLoadingMore();
        }
        mStatusLayoutManager.showSuccessLayout();
        mTestAdapter.notifyDataSetChanged();
    }
}
