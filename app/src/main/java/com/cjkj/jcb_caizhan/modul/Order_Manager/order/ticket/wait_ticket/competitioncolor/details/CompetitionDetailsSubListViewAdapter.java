package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.OddsEntity;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.widget.NoScollGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 竞彩详情比赛-列表适配器
 * Created by 1 on 2018/3/20.
 */
public class CompetitionDetailsSubListViewAdapter extends BaseAdapter {

    private Context mContext;

    private List<OrderDetailListEntity> mDatas = new ArrayList<>();

    public CompetitionDetailsSubListViewAdapter(Context mContext) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_competition_qiu_recyclerlistview, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.item_index.setText(mDatas.get(position).getOrderGroup());
        if (!mDatas.get(position).getOrderGroup().equals("odds")) {
            holder.layout_odds.setVisibility(View.GONE);
            holder.item_index.setVisibility(View.VISIBLE);
        } else {
            holder.layout_odds.setVisibility(View.VISIBLE);
            holder.item_index.setVisibility(View.GONE);
            //赔率数据
            List<OddsEntity> oddsList = FastJsonUtil.getBeanList(mDatas.get(position).getOddsList(), OddsEntity.class);
            OddsGridListAdapter mOddsGridListAdapter = new OddsGridListAdapter(mContext, oddsList);
            holder.mAddsGridView.setAdapter(mOddsGridListAdapter);
        }
        return v;
    }

    public void setInfo(List<OrderDetailListEntity> datas) {
        mDatas.clear();
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @Bind(R.id.item_index)
        TextView item_index;
        @Bind(R.id.layout_odds)
        LinearLayout layout_odds;
        @Bind(R.id.mAddsGridView)
        NoScollGridView mAddsGridView;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

//    @Override
//    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        bindContext(parent.getContext());
//        return new ItemViewHolder(
//                LayoutInflater.from(getContext()).inflate(R.layout.item_competition_qiu_recyclerlistview, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(ClickableViewHolder holder, int position) {
//        if (holder instanceof ItemViewHolder) {
//            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
//            itemViewHolder.item_index.setText(mDatas.get(position).getOrderGroup());
//            if (!mDatas.get(position).getOrderGroup().equals("odds")) {
//                itemViewHolder.layout_odds.setVisibility(View.GONE);
//                itemViewHolder.item_index.setVisibility(View.VISIBLE);
//            } else {
//                itemViewHolder.layout_odds.setVisibility(View.VISIBLE);
//                itemViewHolder.item_index.setVisibility(View.GONE);
//
//                //赔率数据
//                List<OddsEntity> oddsList = FastJsonUtil.getBeanList(mDatas.get(position).getOddsList(), OddsEntity.class);
//                OddsGridListAdapter mOddsGridListAdapter = new OddsGridListAdapter(getContext(), oddsList);
//                itemViewHolder.mAddsGridView.setAdapter(mOddsGridListAdapter);
//            }
//        }
//        super.onBindViewHolder(holder, position);
//    }

//    @Override
//    public int getItemCount() {
//        return mDatas == null ? 0 : mDatas.size();
//    }

//    private class ItemViewHolder extends ClickableViewHolder {
//
//        TextView item_index;
//        LinearLayout layout_odds;
//        NoScollGridView mAddsGridView;
//
//        public ItemViewHolder(View itemView) {
//            super(itemView);
//            item_index = $(R.id.item_index);
//            layout_odds = $(R.id.layout_odds);
//            mAddsGridView = $(R.id.mAddsGridView);
//        }
//    }


}
