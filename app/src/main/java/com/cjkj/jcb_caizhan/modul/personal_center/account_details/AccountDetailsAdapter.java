package com.cjkj.jcb_caizhan.modul.Personal_Center.account_details;

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
 * 账户明细适配器
 * Created by 1 on 2018/3/1.
 */
public class AccountDetailsAdapter extends AbsRecyclerViewAdapter{

    private List<String> mDatas = new ArrayList<>();

    public  AccountDetailsAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    public void setInfo(List<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_account_details, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
           return 10;
//        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView item_lottery_station,item_tv_user;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_lottery_station = $(R.id.item_lottery_station);

        }
    }
}
