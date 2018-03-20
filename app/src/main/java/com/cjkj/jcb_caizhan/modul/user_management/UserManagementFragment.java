package com.cjkj.jcb_caizhan.modul.User_Management;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.test.TestInfo;
import com.cjkj.jcb_caizhan.test.RetfitTestAdapter;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.widget.SwipeRecyclerView.SwipeRecyclerView;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.DefaultOnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 1 on 2018/1/16.
 * 用户管理
 */
public class UserManagementFragment extends RxLazyFragment implements SwipeRecyclerView.OnLoadListener,IOnSearchClickListener {

    @Bind(R.id.swipeRecyclerView)
    SwipeRecyclerView mSwipRecyclerView;
    RetfitTestAdapter mTestAdapter;
    List<TestInfo.ResultsBean> mDatas = new ArrayList<>();
    private int page = 1;
    StatusLayoutManager mStatusLayoutManager;

    private SearchFragment searchFragment;

    public static UserManagementFragment newInstance() {
        return new UserManagementFragment();
    }

    @OnClick({R.id.img_search})
    public void OnBtnClick(View v){
        if(v.getId() == R.id.img_search){
            searchFragment.show(getFragmentManager(), SearchFragment.TAG);
        }
    }

    @Override
    public void OnSearchClick(String keyword) {
        //searchInfo.setText(keyword);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_management;
    }

    @Override
    public void finishCreateView(Bundle state) {

        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(this);

        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
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
        mTestAdapter = new RetfitTestAdapter(mSwipRecyclerView.getRecyclerView());
        mSwipRecyclerView.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipRecyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
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
        if (page == 1) mSwipRecyclerView.complete();
        else mSwipRecyclerView.stopLoadingMore();
        mStatusLayoutManager.showSuccessLayout();
        mTestAdapter.notifyDataSetChanged();
    }
}
