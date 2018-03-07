package com.cjkj.jcb_caizhan.modul.order_manager.order;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.SSQActivity;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import butterknife.Bind;

/**
 * 订单管理-订单
 * Created by 1 on 2018/1/15.
 */
public class OrderFragment extends RxLazyFragment {

    @Bind(R.id.RecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    OrderAdapter mAdapter;

    public static OrderFragment newIntance() {
        return new OrderFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order;
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
        mAdapter = new OrderAdapter(mRecyclerView);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {
            switch (position) {
                case 0:
                    IntentUtils.Goto(getActivity(),SSQActivity.class);
                    break;
                case 1:
                    ToastUtil.ShortToast("3d");
                    break;
                case 2:
                    ToastUtil.ShortToast("七乐彩");
                    break;
                case 3:
                    ToastUtil.ShortToast("大乐透");
                    break;
                case 4:
                    ToastUtil.ShortToast("排列三");
                    break;
                case 5:
                    ToastUtil.ShortToast("排列五");
                    break;
                case 6:
                    ToastUtil.ShortToast("七星彩");
                    break;
                case 7:
                    ToastUtil.ShortToast("竞彩足球");
                    break;
                case 8:
                    ToastUtil.ShortToast("竞彩篮球");
                    break;
                case 9:
                    ToastUtil.ShortToast("胜负过关");
                    break;
                case 10:
                    ToastUtil.ShortToast("足球胜负");
                    break;
                case 11:
                    ToastUtil.ShortToast("任选九场");
                    break;
                case 12:
                    ToastUtil.ShortToast("足球单场");
                    break;
            }
        });
//        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
//                ToastUtil.ShortToast("-"+position);
//            }
//        });
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
