package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details.CompetitionTicketDetailsActivity;
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
 * 竞彩-待打票
 * Created by 1 on 2018/3/20.
 */
public class CompetitionTicketFragment extends RxLazyFragment implements CompetitioncolorContract.ICompetitioncolorView{

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @Bind(R.id.loading)
    LoadingLayout loading;

    CompetitionPresenter mPresenter;

    String lotteryTypeid;

    int index = 1;

    CompetitionRecyclerAdapter competitionRecyclerAdapter;

    public static CompetitionTicketFragment newIntance() {
        return new CompetitionTicketFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.recyclerview_layout_refush_loadmore;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mPresenter = new CompetitionPresenter(this);
        lotteryTypeid = ((CompetitioncolorTicketActivity) getActivity()).getLotteryTypeid();
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
        loading.setStatus(LoadingLayout.Loading);
    }

    @Override
    protected void initRecyclerView() {
        competitionRecyclerAdapter = new CompetitionRecyclerAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(competitionRecyclerAdapter);
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(false).setColorSchemeColors(getResources()
                .getColor(R.color.colorPrimary)));
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

        competitionRecyclerAdapter.setOnItemClickListener((position, holder) -> {
            Intent intent = new Intent(getContext(),CompetitionTicketDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("TicketEntity", competitionRecyclerAdapter.getItemData(position));
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    @Override
    protected void loadData() {
        if (!SPUtil.get(getContext(), Constants.key_uSessionId, "").toString().isEmpty()) {
            mPresenter.getCurrentOrders(SPUtil.get(getContext(), Constants.key_uSessionId, "").toString(),
                    lotteryTypeid, index, "0");
        }
    }

    @Override
    public void Successful(List<TicketEntity> orderList) {
        if (index == 1) {
            if (orderList.isEmpty()) {
                loading.setStatus(LoadingLayout.Empty);
            } else {
                loading.setStatus(LoadingLayout.Success);
            }
            competitionRecyclerAdapter.setInfo(orderList);
        } else {
            if (orderList.isEmpty()) {
                ToastUtil.ShortToast("没有更多数据了");
            }
            competitionRecyclerAdapter.addInfo(orderList);
        }
        finishTask();
    }

    @Override
    protected void finishTask() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index == 1)
                    mRefreshLayout.finishRefresh(true);
                else
                    mRefreshLayout.finishLoadMore();
            }
        }, 2 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }
}
