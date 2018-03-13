package com.cjkj.jcb_caizhan.widget.NineGridView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格图片适配器
 * Created by 1 on 2018/2/12.
 */
public class ImgesAdapter extends AbsRecyclerViewAdapter {

    private List<ImageItem> datas = new ArrayList<>();
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认3张
     */
    private int maxImages = 4;

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


    public void addInfo(ImageItem img) {
        datas.add(0, img);
        notifyItemInserted(0);//通知演示插入动画
        notifyItemRangeChanged(0, datas.size() - 0);//通知数据与界面重新绑定
    }

    public ImgesAdapter(RecyclerView mRecyvlerView) {
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

            if (datas != null && position < datas.size()) {
                Glide.with(getContext())
                        .load(datas.get(position).getUrl())
                        .priority(Priority.HIGH)
                        .into(itemimg);
                item_dele.setVisibility(View.VISIBLE);
                item_dele.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.deleItem(position);
                    }
                });
            } else {
                Glide.with(getContext())
                        .load(R.drawable.addphoto)
                        .priority(Priority.HIGH)
                        .centerCrop()
                        .into(itemimg);
                item_dele.setVisibility(View.GONE);
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int count;
        if (datas == null) {
            count = 1;
        } else if (datas.size() == 3) {
            count = 3;
        } else {
            count = datas.size() + 1;
        }
        return count;
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
