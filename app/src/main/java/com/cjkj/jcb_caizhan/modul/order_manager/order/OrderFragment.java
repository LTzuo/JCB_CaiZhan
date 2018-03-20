package com.cjkj.jcb_caizhan.modul.Order_Manager.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketActivity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.CompetitioncolorTicketActivity;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;

/**
 * 订单管理-订单
 * Created by 1 on 2018/1/15.
 */
public class OrderFragment extends RxLazyFragment implements OrderContract.IOrderContractView {

    @Bind(R.id.RecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    OrderAdapter mAdapter;

    OrderPressenter mPressenter;

    public static OrderFragment newIntance() {
        return new OrderFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mPressenter = new OrderPressenter(this);
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
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(mAdapter.getItemLotteryTypeid(position).equals("16") || mAdapter.getItemLotteryTypeid(position).equals("17")) {
                    //竞彩
                    Intent intent = new Intent(getContext(), CompetitioncolorTicketActivity.class);
                    intent.putExtra("lotteryTypeid", mAdapter.getItemLotteryTypeid(position));
                    getContext().startActivity(intent);
                }else {
                    //数字彩、进球彩
                    Intent intent1 = new Intent(getContext(), TicketActivity.class);
                    intent1.putExtra("lotteryTypeid", mAdapter.getItemLotteryTypeid(position));
                    getContext().startActivity(intent1);
                }
            }
        });
    }

    @Override
    protected void loadData() {
//        Observable.just(mDatas)
//                .compose(bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mDatas -> {
//                    mAdapter.setInfo(mDatas);
        if (!SPUtil.get(getContext(), Constants.key_uSessionId, "").toString().isEmpty()) {
            mPressenter.getOrderIndex(SPUtil.get(getContext(), Constants.key_uSessionId, "").toString());
        }
//        finishTask();
//                }, throwable -> {
//                });
    }

    @Override
    public void getOrderIndexSuessful(List<OrderEntity> orderList) {
        mAdapter.setInfo(orderList);
        finishTask();
    }

    @Override
    public void getOrderIndexFild(String msg) {
        ToastUtil.ShortToast(msg);
        finishTask();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPressenter.unSubscribe();
    }

}
