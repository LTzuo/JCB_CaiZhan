package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import java.util.ArrayList;
import java.util.List;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 竞彩-列表适配器
 * Created by 1 on 2018/3/20.
 */
public class CompetitionRecyclerAdapter extends AbsRecyclerViewAdapter{
    private List<TicketEntity> mDatas = new ArrayList<>();

    public CompetitionRecyclerAdapter(RecyclerView mRecyclerView){
        super(mRecyclerView);
    }

    public void setInfo(List<TicketEntity> datas){
        mDatas.clear();
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void addInfo(List<TicketEntity> datas){
        for(TicketEntity eneity:datas){
            mDatas.add(eneity);
        }
        notifyDataSetChanged();
    }

    public TicketEntity getItemData(int position){
        return mDatas.get(position);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_compatitioncolor_waitticket, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
           // itemViewHolder.itemTitle.setText(mDatas.get(position));
            itemViewHolder.mNumbered_unique.setText(mDatas.get(position).getSerialNo());
            Glide.with(getContext())
                    .load(mDatas.get(position).getUserPic())
                    .centerCrop()
                    .crossFade()
                    .into(itemViewHolder.group_header);
            itemViewHolder.group_lotteryName.setText(mDatas.get(position).getLotteryName());
            itemViewHolder.group_lotteryPerion.setText(mDatas.get(position).getLotteryPerion());
            itemViewHolder.group_orderId.setText("订单编号"+mDatas.get(position).getOrderId());
            itemViewHolder.group_user.setText(mDatas.get(position).getUserNickName());
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView mNumbered_unique;  //编号
      //  ImageView mgroupimage;  //向上向下箭头
        AvatarImageView group_header; //用户头像
        TextView group_lotteryName;//球类
        TextView group_lotteryPerion;//期数
        TextView group_orderId;//订单编号
        TextView group_user;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mNumbered_unique = $(R.id.Numbered_unique);
            group_header = $(R.id.group_header);
            group_lotteryName = $(R.id.group_lotteryName);
            group_lotteryPerion = $(R.id.group_lotteryPerion);
            group_orderId = $(R.id.group_orderId);
            group_user = $(R.id.group_user);
        }
    }



}
