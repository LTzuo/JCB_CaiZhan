package com.cjkj.jcb_caizhan.modul.Data_Statistics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
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
public class DataStatisticsFragment extends RxLazyFragment implements SwipeRecyclerView.OnLoadListener, DataStatisticsContract.IDataStatisticsView {

    @Bind(R.id.swipeRecyclerView)
    SwipeRecyclerView mSwipRecyclerView;
    DataStatisticsAdapter mAdapter;
    List<DataEntity> mDatas = new ArrayList<>();
    StatusLayoutManager mStatusLayoutManager;

    public static DataStatisticsFragment newInstance() {
        return new DataStatisticsFragment();
    }

    DataStatisticsPressenter mPressenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_data_statistics;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mPressenter = new DataStatisticsPressenter(this);
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
        mPressenter.getBills(SPUtil.get(getContext(), Constants.key_uSessionId, "").toString());
    }

    @Override
    protected void finishTask() {
        mSwipRecyclerView.complete();
        mStatusLayoutManager.showSuccessLayout();
    }

    @Override
    public void SussessFul(List<DataEntity> list,String item_1,String item_2) {
        mAdapter.setInfo(list,item_1,item_2);
        finishTask();
    }

    @Override
    public void Filed(String msg) {
        ToastUtil.ShortToast(msg);
    }

}
