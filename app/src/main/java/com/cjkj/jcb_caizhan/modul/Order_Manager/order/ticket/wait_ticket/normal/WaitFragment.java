package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.normal;

import android.widget.ExpandableListView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.BaseFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketActivity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.utils.LubanUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 本期待打票
 * Created by 1 on 2018/2/23.
 */
public class WaitFragment extends BaseFragment implements TicketContract.ITicketView, WaitExAdapter.OnChildSubmitBtnClick {

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @Bind(R.id.loading)
    LoadingLayout loading;

    TicketPressenter mPressenter;

    WaitExAdapter mAdapter;

    String lotteryTypeid;

    int index = 1;

    public static WaitFragment newIntance() {
        return new WaitFragment();
    }

    @Override
    public void onFirstUserVisible() {
        lotteryTypeid = ((TicketActivity) getActivity()).getLotteryTypeid();
        mPressenter = new TicketPressenter(this);
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
        mAdapter = new WaitExAdapter(getContext());
        mExpandableListView.setAdapter(mAdapter);
        mAdapter.setOnSubmitBtnClickListener(this);
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
                            lotteryTypeid, index, "0");
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
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
                    if (orderList.isEmpty()) {
                        loading.setStatus(LoadingLayout.Empty);
                    } else {
                        loading.setStatus(LoadingLayout.Success);
                    }
//                }
//            }, 1 * 1000);
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
        mPressenter.unSubscribe();
    }

    @Override
    public void onChildSubmitBtnClick(String orderId, ArrayList<ImageItem> Imgs) {
        Map<String, Object> maps = new HashMap<String, Object>();
        for (int i = 0; i < Imgs.size(); i++) {
            if (Imgs.get(i).isLocal()) {
                File file = LubanUtils.Compress(getContext(), Imgs.get(i).getUrl());
                maps.put(Constants.ImgArray[i], file);
            } else {
                maps.put(Constants.ImgArray[i], Imgs.get(i).getUrl());
            }
        }
        maps.put("uSessionId", SPUtil.get(getContext(), Constants.key_uSessionId, "").toString());
        maps.put("lotteryTypeid", lotteryTypeid);
        maps.put("orderId", orderId);
        new Thread(new Runnable() {
            @Override
            public void run() {
               mPressenter.putOrderPics(maps);
            }
        }).start();
    }

    @Override
    public void putOrderPicsSuccessful(String msg) {
//        if (loaddialog != null) {
//            loaddialog.dismiss();
//        }
        ToastUtil.ShortToast(msg);
        loadData();
    }

    @Override
    public void putOrderPicsFaild(String msg) {
//        if (loaddialog != null) {
//            loaddialog.dismiss();
//        }
        ToastUtil.ShortToast(msg);
    }



}