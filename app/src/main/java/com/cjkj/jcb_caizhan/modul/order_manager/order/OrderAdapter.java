package com.cjkj.jcb_caizhan.modul.order_manager.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;

/**
 * 订单管理-订单是配器
 * Created by 1 on 2018/2/8.
 */
public class OrderAdapter extends AbsRecyclerViewAdapter {

    private String[] itemNames = new String[]{
            "双色球", "3D", "七乐彩",
            "大乐透", "排列三", "排列五",
            "七星彩", "竞彩足球", "竞彩篮球",
            "胜负过关", "足球胜负", "任选九场",
            "足球单场"

    };

    private int[] itemIcons = new int[]{
            R.drawable.icon_ssq, R.drawable.icon_3d,
            R.drawable.icon_7lc, R.drawable.icon_dlt,
            R.drawable.icon_pl3, R.drawable.icon_pl5,
            R.drawable.icon_7xc, R.drawable.icon_zqjc,
            R.drawable.icon_jclq, R.drawable.icon_sfgg,
            R.drawable.icon_zqsf, R.drawable.icon_rx9,
            R.drawable.icon_zqdc
    };


    public OrderAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_order, parent, false));
    }


    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mItemIcon.setImageResource(itemIcons[position]);
            itemViewHolder.mItemText.setText(itemNames[position]);
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return itemIcons.length;
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mItemIcon;
        TextView mItemText, mItemContext;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemIcon = $(R.id.item_icon);
            mItemText = $(R.id.item_title);
            mItemContext = $(R.id.item_content);
        }
    }
}
