package com.cjkj.jcb_caizhan.modul.data_statistics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.widget.SwipeRecyclerView.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import me.bakumon.statuslayoutmanager.library.DefaultOnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * Created by 1 on 2018/1/16.
 * 数据统计
 */
public class DataStatisticsFragment extends RxLazyFragment implements SwipeRecyclerView.OnLoadListener {

    @Bind(R.id.swipeRecyclerView)
    SwipeRecyclerView mSwipRecyclerView;
    DataStatisticsAdapter mAdapter;
    List<DataStatisticsBean> mDatas = new ArrayList<>();
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
        initstatusManagerLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initstatusManagerLayout() {
        mStatusLayoutManager = new StatusLayoutManager.Builder(mSwipRecyclerView)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new DefaultOnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        mStatusLayoutManager.showLoadingLayout();
                        loadData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        mStatusLayoutManager.showLoadingLayout();
                        loadData();
                    }
                })
                .build();
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new DataStatisticsAdapter(mSwipRecyclerView.getRecyclerView());
        mSwipRecyclerView.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipRecyclerView.setOnLoadListener(this);
        mSwipRecyclerView.setLoadMoreEnable(false);
        mSwipRecyclerView.setAdapter(mAdapter);
        mSwipRecyclerView.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {
//        loadData();
    }

    @Override
    protected void loadData() {
        mDatas.clear();
        mDatas.add(new DataStatisticsBean("用户数据", "今日注册", "3", "本周注册", "6", "21", "42", "本月注册", "60", "120"));
        mDatas.add(new DataStatisticsBean("出票数据", "今日出票", "3", "本周出票", "6", "21", "42", "本月出票", "60", "120"));
        mDatas.add(new DataStatisticsBean("派奖数据", "今日派奖", "3", "本周派奖", "6", "21", "42", "本月派奖", "60", "120"));
        mDatas.add(new DataStatisticsBean("取票数据", "今日取票", "3", "本周取票", "21", "6", "42", "本月取票", "60", "120"));
        mAdapter.setInfo(mDatas);
//        RetrofitHelper.getTestApi()
//                .getDatas("福利", 10, page)
//                .compose(bindToLifecycle())
//                .cache()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(Info -> {
//                    if (page == 1) {
//                        mDatas = Info.getResults();
//                        if (mDatas.isEmpty()) mStatusLayoutManager.showEmptyLayout();
//                    } else {
//                        if (Info.getResults().isEmpty())
//                            mSwipRecyclerView.onNoMore("-- 到底啦 --");
//                        for (TestInfo.ResultsBean bean : Info.getResults()) {
//                            mDatas.add(bean);
//                        }
//                    }
//                    mTestAdapter.setTestInfo(mDatas);
        finishTask();
//                }, throwable -> mStatusLayoutManager.showErrorLayout());
    }

    @Override
    protected void finishTask() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipRecyclerView.complete();
                mStatusLayoutManager.showSuccessLayout();
               //mAdapter.notifyDataSetChanged();
            }
        }, 1 * 1000);
    }
}
