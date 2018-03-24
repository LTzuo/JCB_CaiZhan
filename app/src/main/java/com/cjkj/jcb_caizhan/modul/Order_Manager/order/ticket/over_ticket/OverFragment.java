package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket;

import android.widget.ExpandableListView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.BaseFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketActivity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.Bind;

/**
 * 本期已完成
 * Created by 1 on 2018/2/23.
 */
public class OverFragment extends BaseFragment implements OverContract.IOverView{

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Bind(R.id.loading)
    LoadingLayout loading;

    OverPressenter mPressenter;

    OverExAdapter mAdapter;

    String lotteryTypeid;

    int index = 1;

    public static OverFragment newIntance() {
        return new OverFragment();
    }

    @Override
    public void onFirstUserVisible() {
        lotteryTypeid = ((TicketActivity) getActivity()).getLotteryTypeid();
        mPressenter = new OverPressenter(this);
        initstatusManagerLayout();
        initRecyclerView();
    }

    @Override
    public void onUserVisible() {
        loadData();
    }

    @Override
    protected void initstatusManagerLayout() {
        //loading.setStatus(LoadingLayout.Loading);
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new OverExAdapter(getContext());
        mExpandableListView.setAdapter(mAdapter);

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mExpandableListView.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < count; j++) {
                    if (j != groupPosition) {
                        mExpandableListView.collapseGroup(j);
                    }
                }
            }
        });
        mRefreshLayout.autoRefresh();
        mRefreshLayout.autoLoadMore();
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(false).setColorSchemeColors(getResources()
                .getColor(R.color.colorPrimary)));//Material风格
        //mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setDrawableSize(20));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                index = 1;
                loadData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index++;
                loadData();
            }
        });
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_ssq_wait_ticket;
    }

    @Override
    protected void loadData() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!SPUtil.get(getContext(), Constants.key_uSessionId, "").toString().isEmpty()) {
                    mPressenter.getCurrentOrders(SPUtil.get(getContext(), Constants.key_uSessionId, "").toString(),
                            lotteryTypeid, index, "1");
                }
            }
        });
    }

    @Override
    protected void finishTask() {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if(mRefreshLayout == null)
            return;
        if (index == 1)
            mRefreshLayout.finishRefresh(true);
        else
            mRefreshLayout.finishLoadMore();
//            }
//        }, 1 * 1000);
    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public void Sussesful(List<TicketEntity> orderList) {
        if (index == 1) {
            mAdapter.setInfo(orderList);
                    if (orderList.isEmpty()) {
                        loading.setStatus(LoadingLayout.Empty);
                    } else {
                        loading.setStatus(LoadingLayout.Success);
                    }
        } else {
            if (orderList.isEmpty()) {
                ToastUtil.ShortToast("没有更多数据了");
            }
            mAdapter.addInfo(orderList);
        }
        finishTask();
    }

    @Override
    public void ShowFail(String msg) {
        ToastUtil.ShortToast(msg);
        loading.setStatus(LoadingLayout.Loading);
        finishTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPressenter != null)
        mPressenter.unSubscribe();
    }


}
