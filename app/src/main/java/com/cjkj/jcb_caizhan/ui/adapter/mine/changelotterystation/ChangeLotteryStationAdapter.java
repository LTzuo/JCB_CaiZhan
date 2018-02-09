package com.cjkj.jcb_caizhan.ui.adapter.mine.changelotterystation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.mine.changelotterystation.ChangeLotteryStationBean;
import com.cjkj.jcb_caizhan.ui.adapter.helper.AbsRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 切换彩站适配器
 * Created by 1 on 2018/2/8.
 */
public class ChangeLotteryStationAdapter extends AbsRecyclerViewAdapter {

    private List<ChangeLotteryStationBean> mDatas = new ArrayList<>();

    public  ChangeLotteryStationAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    public void setInfo(List<ChangeLotteryStationBean> datas) {
        this.mDatas = datas;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_change_lottery_station, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.item_lottery_station.setText(mDatas.get(position).getStationname());
            itemViewHolder.item_tv_user.setText(mDatas.get(position).getUsername()+" :"+mDatas.get(position).getNumber());
            Glide.with(getContext())
                    .load(mDatas.get(position).getUrl() + "")
                    .placeholder(R.drawable.default_lottery)
                    .error(R.drawable.default_lottery)
                    .into(itemViewHolder.item_header);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView item_lottery_station,item_tv_user;
        AvatarImageView item_header;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_lottery_station = $(R.id.item_lottery_station);
            item_tv_user = $(R.id.item_tv_user);
            item_header = $(R.id.item_header);
        }
    }
}
