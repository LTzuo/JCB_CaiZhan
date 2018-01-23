package com.cjkj.jcb_caizhan.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.ui.adapter.helper.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.entity.TestInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2018/1/16.
 * 测试
 */
public class RetfitTestAdapter extends AbsRecyclerViewAdapter {

   // private Context context;
    private List<TestInfo.ResultsBean> mDatas = new ArrayList<>();

    public RetfitTestAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setTestInfo(List<TestInfo.ResultsBean> datas) {
        this.mDatas = datas;
    }

    public void addInfo(int position,List<TestInfo.ResultsBean> datas) {
        mDatas.add((TestInfo.ResultsBean) datas);
        this.notifyItemRangeInserted(position, mDatas.size());
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mItemText0.setText(mDatas.get(position).getDesc());
            itemViewHolder.mItemText1.setText(mDatas.get(position).getCreatedAt());
            itemViewHolder.mItemText2.setText(mDatas.get(position).getType());
            itemViewHolder.mItemText3.setText(mDatas.get(position).getWho());

            Glide.with(getContext())
                    .load(mDatas.get(position).getUrl() + "")
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(itemViewHolder.head_img);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView mItemText0,mItemText1,mItemText2,mItemText3;
        ImageView head_img;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemText0 = $(R.id.mItemText0);
            mItemText1 = $(R.id.mItemText1);
            mItemText2 = $(R.id.mItemText2);
            mItemText3 = $(R.id.mItemText3);
            head_img = $(R.id.head_img);
        }
    }
}
