package com.cjkj.jcb_caizhan.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.TestInfo;
import com.cjkj.jcb_caizhan.ui.adapter.helper.AbsRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/1/16.
 * 数据统计适配器
 */
public class DataStatisticsAdapter extends RecyclerView.Adapter<DataStatisticsAdapter.ItemViewHolder> {

    private List<TestInfo.ResultsBean> mDatas = new ArrayList<>();

    private Context context;

    public DataStatisticsAdapter(Context context){
        this.context = context;
    }

    public void setTestInfo(List<TestInfo.ResultsBean> datas){
        this.mDatas = datas;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.tv.setText("my position is" + mDatas.get(position));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.mItemText0);
        }
    }

}
