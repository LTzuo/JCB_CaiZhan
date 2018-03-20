package com.cjkj.jcb_caizhan.modul.Personal_Center.lottery.lottery_thread;

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
 * 走势图列表适配器
 * Created by 1 on 2018/2/26.
 */
public class LotteryThreadListAdapter extends AbsRecyclerViewAdapter{

    private List<String> mDatas = new ArrayList<>();

    public  LotteryThreadListAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    public void setInfo(List<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_lotterythread_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.itemText.setText(mDatas.get(position));
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView itemText;
        public ItemViewHolder(View itemView) {
            super(itemView);
            itemText = $(R.id.itemText);
        }
    }
}
