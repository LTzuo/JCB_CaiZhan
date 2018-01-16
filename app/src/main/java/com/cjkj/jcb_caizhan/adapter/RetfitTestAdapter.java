package com.cjkj.jcb_caizhan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.adapter.helper.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.entity.TestInfo;


/**
 * Created by 1 on 2018/1/16.
 * 测试
 */
public class RetfitTestAdapter extends AbsRecyclerViewAdapter {

    private Context context;
    private TestInfo info;

    public RetfitTestAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setTestInfo(TestInfo info) {
        this.info = info;
       // ToastUtil.ShortToast(info.getStatus());
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
            itemViewHolder.mItemText.setText(info.getResult().get(position).getName());
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return info.getResult().size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView mItemText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemText = $(R.id.test);
        }
    }
}
