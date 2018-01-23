package com.cjkj.jcb_caizhan.ui.fragment.dataStatisics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.TestInfo;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.ui.adapter.RetfitTestAdapter;
import com.cjkj.jcb_caizhan.ui.fragment.RxLazyFragment;
import com.cjkj.jcb_caizhan.ui.widget.swiperecyclerview.SwipeRecyclerView;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
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
    protected void initRecyclerView() {
        mTestAdapter = new RetfitTestAdapter(mSwipRecyclerView.getRecyclerView());
        mSwipRecyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        //设置emptyView
        /*TextView textView = new TextView(this);
        textView.setText("empty view");
        recyclerView.setEmptyView(textView);*/
        //设置网络处理
        // recyclerView.onNetChange(true);
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Info -> {
                    if(page == 1){
                        mDatas = Info.getResults();
                    }else{
                        for(TestInfo.ResultsBean bean : Info.getResults()){
                            mDatas.add(bean);
                        }
                    }
                    mTestAdapter.setTestInfo(mDatas);
                    finishTask();
                }, throwable -> initEmptyView());
    }

    private void initEmptyView() {
        ToastUtil.ShortToast("数据加载失败,请重新加载或者检查网络是否链接");
    }

    @Override
    protected void finishTask() {
        if(page == 1){
            mSwipRecyclerView.complete();
        }else{
            mSwipRecyclerView.stopLoadingMore();
        }
        mTestAdapter.notifyDataSetChanged();
    }
}
