package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.SubListViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.UserListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket.UserTableListViewAdapter;
import com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.now.ChildListViewAdapter;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity.OrderEntity;
import com.cjkj.jcb_caizhan.test.table.TableListViewTestAdapter;
import com.cjkj.jcb_caizhan.test.table.TableTextEntity;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.GridAdapter;
import com.cjkj.jcb_caizhan.widget.NineGridView.GridImgAdapter;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.cjkj.jcb_caizhan.widget.ExListView.AbsExListViewAdapter;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseChildBean;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseGroupBean;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseViewHolder;
import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 个人中心-可折叠二级菜单适配器(订单查询)
 * Created by 1 on 2018/3/5.
 */
public class OrderQuaryExAdapter extends AbsExListViewAdapter {

    private List<OrderEntity> mDatas = new ArrayList<>();

    public OrderQuaryExAdapter(Context mContext) {
        super(mContext);
    }

    public void setInfo(List<OrderEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.group_no_out_ticket, viewGroup, false);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        holder.groupIcon.setImageResource(isExpanded ? R.drawable.shou_jt : R.drawable.xl_jt);
        Glide.with(getContext())
                .load(mDatas.get(groupPosition).getLotteryPic())
                .centerCrop()
                .crossFade()
                .into(holder.img_lottery);

        holder.group_text.setText(mDatas.get(groupPosition).getLotteryName());
        holder.group_lotteryPerion.setText(mDatas.get(groupPosition).getLotteryPerion());
        holder.order_number.setText("订单编号" + mDatas.get(groupPosition).getOrderId());

        Glide.with(getContext())
                .load(mDatas.get(groupPosition).getUserPic())
                .centerCrop()
                .crossFade()
                .into(holder.group_img);

        holder.group_user.setText(mDatas.get(groupPosition).getUserNickName());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.child_order_query, viewGroup, false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }

        holder.tv_playtype.setText(mDatas.get(groupPosition).getOrderType() + " " +
                mDatas.get(groupPosition).getPlayType() + " " + mDatas.get(groupPosition).getOrderPart() +
                "注 " + mDatas.get(groupPosition).getIsAppend());

        holder.child_orderState.setText(mDatas.get(groupPosition).getOrderState());

        /********球**************/
        List<OrderDetailListEntity> SourceDateList = FastJsonUtil.getBeanList(mDatas.get(groupPosition).getOrderDetailList(), OrderDetailListEntity.class);
        SubListViewAdapter listViewAdaAdapter = new SubListViewAdapter(getContext(), SourceDateList);
        holder.childlistView.setAdapter(listViewAdaAdapter);

        holder.child_amount.setText("合计" + mDatas.get(groupPosition).getAmount() + "元");

        /**********图片**************/
        List<ImageItem> imgs = new ArrayList<>();
        if (!mDatas.get(groupPosition).getOrderPics().isEmpty()) {
            String[] strs = mDatas.get(groupPosition).getOrderPics().split(",|;");
            for (int i = 0, len = strs.length; i < len; i++) {
                ImageItem item = new ImageItem(strs[i]);
                item.setLocal(false);
                imgs.add(0, item);
            }
        }
        holder.mNineRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        GridImgAdapter mGridAdapter = new GridImgAdapter(holder.mNineRecyclerView);
        mGridAdapter.setDatas(imgs);
        holder.mNineRecyclerView.setAdapter(mGridAdapter);

        /*****显示下单时间*****/
        holder.bornDate.setText("下单时间：" + mDatas.get(groupPosition).getBornDate());
        if (mDatas.get(groupPosition).getState().equals("2") || mDatas.get(groupPosition).getState().equals("4") || mDatas.get(groupPosition).getState().equals("9") ||
                mDatas.get(groupPosition).getState().equals("10")) {
            /****显示打票时间***/
            holder.ticketTime.setText("打票时间：" + mDatas.get(groupPosition).getTicketTime());
        }else if (mDatas.get(groupPosition).getState().equals("3")) {
            /*******显示打票错误时间***********/
            holder.ticketTime.setText("打票错误时间：" + mDatas.get(groupPosition).getTicketTime());
        }

        if(mDatas.get(groupPosition).getIsLottery().equals("是")){
            //已开奖
            holder.Layout_open.setVisibility(View.VISIBLE);
            holder.lotteryTime.setText("开奖时间："+mDatas.get(groupPosition).getLotteryTime());
            holder.lotteryNo.setText("开奖号码："+mDatas.get(groupPosition).getLotteryNo());
        }else{
            //未开奖
            holder.Layout_open.setVisibility(View.GONE);
        }

        if(mDatas.get(groupPosition).getIsLottery().equals("是")){
            //已中奖
            holder.Layout_win.setVisibility(View.VISIBLE);
            holder.winAmount.setText("中奖金额："+mDatas.get(groupPosition).getWinAmount()+"元");
        }else{
            //未中奖
            holder.Layout_win.setVisibility(View.GONE);
        }

        /**********合买**************/
        if (!"".equals(mDatas.get(groupPosition).getUserList())) {
            holder.layout_withbuy.setVisibility(View.VISIBLE);
            UserTableListViewAdapter mTableTestAdapter = new UserTableListViewAdapter(getContext());
            holder.childTablelistView.setAdapter(mTableTestAdapter);
            List<UserListEntity> userList = new ArrayList<>();
            userList.addAll(FastJsonUtil.getBeanList(mDatas.get(groupPosition).getUserList(), UserListEntity.class));
            userList.add(0, new UserListEntity("", "合买人", "等级", "出资", "份额", "奖金/加奖"));
            mTableTestAdapter.setInfo(userList);
        } else {
            holder.layout_withbuy.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public OrderEntity getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public OrderEntity getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * group视图
     */
    public class GroupViewHolder extends ExBaseViewHolder {
        ImageView groupIcon, img_lottery;
        TextView group_text, group_lotteryPerion, order_number, group_user;
        AvatarImageView group_img;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupIcon = $(R.id.img_Indicator);
            img_lottery = $(R.id.img_lottery);
            group_text = $(R.id.group_text);
            group_lotteryPerion = $(R.id.group_lotteryPerion);
            order_number = $(R.id.order_number);
            group_img = $(R.id.group_img);
            group_user = $(R.id.group_user);
        }
    }

    /**
     * child视图
     */
    public class ChildViewHolder extends ExBaseViewHolder {
        SubListView childlistView, childTablelistView;
        TextView tv_playtype, child_orderState, child_amount, bornDate, ticketTime,lotteryTime,lotteryNo,winAmount;
        RecyclerView mNineRecyclerView;
        LinearLayout layout_withbuy,Layout_open,Layout_win;

        public ChildViewHolder(View itemView) {
            super(itemView);
            childlistView = $(R.id.childlistView);
            childTablelistView = $(R.id.childTablelistView);
            tv_playtype = $(R.id.tv_playtype);
            child_orderState = $(R.id.child_orderState);
            child_amount = $(R.id.child_amount);
            mNineRecyclerView = $(R.id.mNineRecyclerView);
            layout_withbuy = $(R.id.layout_withbuy);
            bornDate = $(R.id.bornDate);
            ticketTime = $(R.id.ticketTime);
            Layout_open = $(R.id.Layout_open);
            Layout_win = $(R.id.Layout_win);
            lotteryTime = $(R.id.lotteryTime);
            lotteryNo = $(R.id.lotteryNo);
            winAmount = $(R.id.winAmount);
        }
    }


}
