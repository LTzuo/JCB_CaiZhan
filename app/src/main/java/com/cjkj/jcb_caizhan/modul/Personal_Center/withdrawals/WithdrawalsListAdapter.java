package com.cjkj.jcb_caizhan.modul.Personal_Center.withdrawals;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心-提现记录适配器
 * Created by 1 on 2018/2/24.
 */
public class WithdrawalsListAdapter extends AbsRecyclerViewAdapter{

    private List<String> mDatas = new ArrayList<>();

    public WithdrawalsListAdapter(RecyclerView mRecyclerView){
        super(mRecyclerView);
    }

    public void setInfo(List<String> datas){
        this.mDatas = datas;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_withdrawals_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
           // itemViewHolder.itemTitle.setText(mDatas.get(position));
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 5;
        //return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends ClickableViewHolder {

       // TextView itemTitle;
        public ItemViewHolder(View itemView) {
            super(itemView);
          //  itemTitle = $(R.id.itemTitle);
        }
    }


}
