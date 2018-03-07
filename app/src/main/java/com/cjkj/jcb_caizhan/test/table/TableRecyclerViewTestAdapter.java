package com.cjkj.jcb_caizhan.test.table;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 列表仿表格适配器
 * Created by 1 on 2018/3/1.
 */
public class TableRecyclerViewTestAdapter extends AbsRecyclerViewAdapter {

    public static final int ITEM_TYPE_HEADER = 1;
    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_FOOTER = 2;

    private List<TableTextEntity> mDatas = new ArrayList<>();

    public TableRecyclerViewTestAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setInfo(List<TableTextEntity> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(
                    LayoutInflater.from(getContext()).inflate(R.layout.test_item_table, parent, false));
        } else{
            return new ItemViewHolder(
                    LayoutInflater.from(getContext()).inflate(R.layout.test_item_table, parent,false));
        }
//        else{
//            return new FooterViewHolder(
//                    LayoutInflater.from(getContext()).inflate(R.layout.test_item_table, parent, false));
//        }
    }

    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.ClickableViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headHolder = (HeaderViewHolder) holder;
            headHolder.head_img.setVisibility(View.GONE);
        }
        if (holder instanceof ItemViewHolder) {
            int realPosition = position - 1;
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.text1.setText(mDatas.get(realPosition).getTitle1());
            itemViewHolder.text2.setText(mDatas.get(realPosition).getTitle2());
            itemViewHolder.text3.setText(mDatas.get(realPosition).getTitle3());
            itemViewHolder.text4.setText(mDatas.get(realPosition).getTitle4());
            itemViewHolder.text5.setText(mDatas.get(realPosition).getTitle5());
            itemViewHolder.text2.setBackground(getContext().getResources().getDrawable(R.drawable.shape_half_red_bg));
            itemViewHolder.text2.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return ITEM_TYPE_HEADER;
        }
//         else if(position == getItemCount() + 1){
//             return ITEM_TYPE_FOOTER;
//         }
        else{
            return ITEM_TYPE_NORMAL;
        }

    }

    private class HeaderViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {
        AvatarImageView head_img;

        public HeaderViewHolder(View headerView) {
            super(headerView);
            head_img = $(R.id.head_img);
        }
    }

    private class FooterViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {
        AvatarImageView head_img;

        public FooterViewHolder(View headerView) {
            super(headerView);
            head_img = $(R.id.head_img);
        }
    }

    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        TextView text1, text2, text3, text4, text5;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text1 = $(R.id.text1);
            text2 = $(R.id.text2);
            text3 = $(R.id.text3);
            text4 = $(R.id.text4);
            text5 = $(R.id.text5);
        }
    }
}
