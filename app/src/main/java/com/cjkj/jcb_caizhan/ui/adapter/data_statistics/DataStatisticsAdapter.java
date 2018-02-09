package com.cjkj.jcb_caizhan.ui.adapter.data_statistics;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.data_statstics.DataStatisticsBean;
import com.cjkj.jcb_caizhan.ui.adapter.helper.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据统计适配器
 * Created by 1 on 2018/2/8.
 */
public class DataStatisticsAdapter extends AbsRecyclerViewAdapter {

    private List<DataStatisticsBean> mDatas = new ArrayList<>();

    public DataStatisticsAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setInfo(List<DataStatisticsBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_data_statistics, parent, false));
    }

    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.item_type.setText(mDatas.get(position).getDataType());
            itemViewHolder.item_day_title.setText(mDatas.get(position).getDayTitle());
            itemViewHolder.item_week_title.setText(mDatas.get(position).getWeekTitle());
            itemViewHolder.item_month_title.setText(mDatas.get(position).getMonthTitle());
            itemViewHolder.item_day_single.setText(mDatas.get(position).getDayofsingle());
            itemViewHolder.item_week_single.setText(mDatas.get(position).getWeekofsingle());
            itemViewHolder.item_month_single.setText(mDatas.get(position).getMonthofsingle());
            itemViewHolder.item_day_price.setText(mDatas.get(position).getDayofprice());
            itemViewHolder.item_week_price.setText(mDatas.get(position).getWeekofprice());
            itemViewHolder.item_month_price.setText(mDatas.get(position).getMonthofprice());
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView item_type, item_day_title, item_week_title, item_month_title,
                item_day_single, item_week_single, item_month_single,
                item_day_price,item_week_price,item_month_price;

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
