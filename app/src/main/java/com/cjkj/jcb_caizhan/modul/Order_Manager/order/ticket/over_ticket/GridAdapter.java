package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格图片适配器
 * Created by 1 on 2018/2/12.
 */
public  class GridAdapter extends AbsRecyclerViewAdapter {

    private List<ImageItem> datas = new ArrayList<>();


    public interface onItemImageViewClickListener {
        void deleItem(int position);
    }

    private onItemImageViewClickListener mListener;

    public void setOnItemImageViewClickListener(onItemImageViewClickListener listener) {
        this.mListener = listener;
    }

    public void setDatas(List<ImageItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }


    public GridAdapter(RecyclerView mRecyvlerView) {
        super(mRecyvlerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_repairgrid, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ImageView itemimg = itemViewHolder.item_image;
            ImageView item_dele = itemViewHolder.item_dele;

                Glide.with(getContext())
                        .load(datas.get(position).getUrl())
                        .priority(Priority.HIGH)
                        .into(itemimg);
                item_dele.setVisibility(View.GONE);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
            return datas == null ? 0 : datas.size() ;
    }


    class ItemViewHolder extends ClickableViewHolder {

        ImageView item_image, item_dele;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_image = $(R.id.item_img);
            item_dele = $(R.id.item_dele);
        }
    }
}
