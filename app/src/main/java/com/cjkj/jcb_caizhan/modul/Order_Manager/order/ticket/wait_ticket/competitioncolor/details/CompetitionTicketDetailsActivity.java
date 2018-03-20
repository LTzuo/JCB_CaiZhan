package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.widget.SubListView;
import java.util.List;
import butterknife.Bind;

/**
 * 竞彩等待打票详情
 */
public class CompetitionTicketDetailsActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.lotteryName)
    TextView lotteryName;
    @Bind(R.id.lotteryPerion)
    TextView lotteryPerion;
    @Bind(R.id.serialNo)
    TextView serialNo;
    @Bind(R.id.orderState)
    TextView orderState;
    @Bind(R.id.amount)
    TextView amount;
    @Bind(R.id.userNickName)
    TextView userNickName;
    @Bind(R.id.orderId)
    TextView orderId;
    @Bind(R.id.playType)
    TextView playType;

    @Bind(R.id.mSubListView)
    SubListView mSubListView;

    TicketEntity mDatas;

    CompetitionDetailsSubListViewAdapter mSubListViewAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_competition_ticket_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        mDatas = intent.getParcelableExtra("TicketEntity");
        initRecyclerView();
        loadData();
    }

    @Override
    public void initRecyclerView() {
        mSubListViewAdapter = new CompetitionDetailsSubListViewAdapter(this);
        mSubListView.setAdapter(mSubListViewAdapter);
    }

    @Override
    public void loadData() {
        lotteryName.setText(mDatas.getLotteryName());
        lotteryPerion.setText(mDatas.getLotteryPerion());
        serialNo.setText(mDatas.getSerialNo());
        orderState.setText(mDatas.getOrderState());
        amount.setText(mDatas.getAmount());
        userNickName.setText(mDatas.getUserNickName());
        orderId.setText(mDatas.getOrderId());
        playType.setText(mDatas.getPlayType()+(mDatas.getOrderTimes().equals("0")?"":"(倍数"+mDatas.getOrderTimes()+")"));

        Log.i("LOG",mDatas.getOrderDetailList());
        List<OrderDetailListEntity> ordeDetailList = FastJsonUtil.getBeanList(mDatas.getOrderDetailList(), OrderDetailListEntity.class);
        mSubListViewAdapter.setInfo(ordeDetailList);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("订单详情");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

}
