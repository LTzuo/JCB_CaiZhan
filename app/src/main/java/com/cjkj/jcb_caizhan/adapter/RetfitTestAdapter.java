package com.cjkj.jcb_caizhan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.adapter.helper.AbsRecyclerViewAdapter;
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

    public void setTestInfo(List<TestInfo.ResultsBean> mDatas) {
        this.mDatas = mDatas;
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
            itemViewHolder.mItemText.setText(mDatas.get(position).getWho());

            Glide.with(getContext())
                    .load(mDatas.get(position).getUrl() + "")
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(itemViewHolder.text_img);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView mItemText;
        ImageView text_img;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemText = $(R.id.test);
            text_img = $(R.id.text_img);
        }
    }
}
