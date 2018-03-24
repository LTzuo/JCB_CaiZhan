package com.cjkj.jcb_caizhan.modul.Data_Statistics;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据统计适配器
 * Created by 1 on 2018/2/8.
 */
public class DataStatisticsAdapter extends AbsRecyclerViewAdapter {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private List<DataEntity> mDatas = new ArrayList<>();
    private String todayOrders, todayAmount;

    public DataStatisticsAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setInfo(List<DataEntity> mDatas, String todayOrders, String todayAmount) {
        this.todayOrders = todayOrders;
        this.todayAmount = todayAmount;
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        if (viewType == 0) {
            return new HeaderViewHolder(
                    LayoutInflater.from(getContext()).inflate(R.layout.header_data_statistics, parent, false));
        } else {
            return new ItemViewHolder(
                    LayoutInflater.from(getContext()).inflate(R.layout.item_data_statistics, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.ClickableViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.item_1.setText(todayOrders);
            headerHolder.item_2.setText(todayAmount);
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.item_type.setText(mDatas.get(position - 1).getBillTitle());
            itemViewHolder.item_day_title.setText(mDatas.get(position - 1).getBillDetails().get(0).getDetailTitle());
            itemViewHolder.item_week_title.setText(mDatas.get(position - 1).getBillDetails().get(1).getDetailTitle());
            itemViewHolder.item_month_title.setText(mDatas.get(position - 1).getBillDetails().get(2).getDetailTitle());
            itemViewHolder.item_day_single.setText(mDatas.get(position - 1).getBillDetails().get(0).getDetailValue1());
            itemViewHolder.item_week_single.setText(mDatas.get(position - 1).getBillDetails().get(1).getDetailValue1());
            itemViewHolder.item_month_single.setText(mDatas.get(position - 1).getBillDetails().get(2).getDetailValue1());
            itemViewHolder.item_day_price.setText(mDatas.get(position - 1).getBillDetails().get(0).getDetailValue2());
            itemViewHolder.item_week_price.setText(mDatas.get(position - 1).getBillDetails().get(1).getDetailValue2());
            itemViewHolder.item_month_price.setText(mDatas.get(position - 1).getBillDetails().get(2).getDetailValue2());
        }
        super.onBindViewHolder(holder, position);
    }

    /*根据位置来返回不同的item类型*/
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else
            return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + 1;
    }

    private class HeaderViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {
        TextView item_1, item_2;

        public HeaderViewHolder(View headerView) {
            super(headerView);
            item_1 = $(R.id.item_1);
            item_2 = $(R.id.item_2);
        }
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView item_type, item_day_title, item_week_title, item_month_title,
                item_day_single, item_week_single, item_month_single,
                item_day_price, item_week_price, item_month_price;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_type = $(R.id.item_type);
            item_day_title = $(R.id.item_day_title);
            item_week_title = $(R.id.item_week_title);
            item_month_title = $(R.id.item_month_title);
            item_day_single = $(R.id.item_day_single);
            item_week_single = $(R.id.item_week_single);
            item_month_single = $(R.id.item_month_single);
            item_day_price = $(R.id.item_day_price);
            item_week_price = $(R.id.item_week_price);
            item_month_price = $(R.id.item_month_price);
        }
    }


}
