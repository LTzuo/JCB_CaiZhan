package com.cjkj.jcb_caizhan.modul.Order_Manager.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理-订单是配器
 * Created by 1 on 2018/2/8.
 */
public class OrderAdapter extends AbsRecyclerViewAdapter {

//    private String[] itemNames = new String[]{
//            "双色球", "3D", "七乐彩",
//            "大乐透", "排列三", "排列五",
//            "七星彩", "竞彩足球", "竞彩篮球",
//            "胜负过关", "足球胜负", "任选九场",
//            "足球单场"
//    };
//
//    private int[] itemIcons = new int[]{
//            R.drawable.icon_ssq, R.drawable.icon_3d,
//            R.drawable.icon_7lc, R.drawable.icon_dlt,
//            R.drawable.icon_pl3, R.drawable.icon_pl5,
//            R.drawable.icon_7xc, R.drawable.icon_zqjc,
//            R.drawable.icon_jclq, R.drawable.icon_sfgg,
//            R.drawable.icon_zqsf, R.drawable.icon_rx9,
//            R.drawable.icon_zqdc
//    };
    private List<OrderEntity> mDatas = new ArrayList<>();

    public OrderAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public String getItemLotteryTypeid(int position){
        return mDatas.get(position).getLotteryTypeid();
    }


    public void setInfo(List<OrderEntity> mdatas){
        this.mDatas = mdatas;
        notifyDataSetChanged();
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
            Glide.with(getContext())
                    .load(mDatas.get(position).getTypePic() + "")
                    .placeholder(R.drawable.default_lottery)
                    .error(R.drawable.default_lottery)
                    .into(itemViewHolder.mItemIcon);
            itemViewHolder.mItemText.setText(mDatas.get(position).getLotteryName());
            itemViewHolder.mItemContext.setText("未完成"+mDatas.get(position).getUpDoneQuantity()+"个，共"+mDatas.get(position).getDoneQuantity()+"个");
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
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
