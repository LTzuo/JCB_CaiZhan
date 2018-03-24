package com.cjkj.jcb_caizhan.widget.NineGridView;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格图片适配器(可设置删除按钮)
 * Created by 1 on 2018/2/12.
 */
public class GridAdapter extends AbsRecyclerViewAdapter {

    private List<ImageItem> datas = new ArrayList<>();

    private GridLayoutManager mGridLayoutManager;

    private boolean isvisibility = false;//默认不可见

    private int maxImages = 4;

    public void setDatas(List<ImageItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public List<ImageItem> getImages() {
        return datas;
    }

    /**
     * 设置图片编辑是否可见
     */
    public void setNineVisibility(boolean isvisibility) {
        this.isvisibility = isvisibility;
        notifyDataSetChanged();
    }

    public void setGridManager(GridLayoutManager mGridLayoutManager) {
        this.mGridLayoutManager = mGridLayoutManager;
    }

    public GridAdapter(RecyclerView mRecyvlerView) {
        super(mRecyvlerView);
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_crawd_img, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (datas != null && position < datas.size()) {
                Glide.with(getContext())
                        .load(datas.get(position).getUrl())
                        .priority(Priority.HIGH)
                        .into(itemViewHolder.item_image);
                if (isvisibility) {
                    itemViewHolder.img_item_dele.setVisibility(View.VISIBLE);
                } else {
                    itemViewHolder.img_item_dele.setVisibility(View.GONE);
                }
            } else {
                itemViewHolder.img_item_dele.setVisibility(View.GONE);
                Glide.with(getContext())
                        .load(R.drawable.addphoto)
                        .priority(Priority.HIGH)
                        .into(itemViewHolder.item_image);
                if (isvisibility) {
                    itemViewHolder.item_image.setVisibility(View.VISIBLE);
                } else {
                    if (position != 0) {
                        itemViewHolder.item_image.setVisibility(View.GONE);
                    }
                }
            }
            itemViewHolder.img_item_dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datas.remove(position);
                    notifyDataSetChanged();
//                    notifyItemChanged(position);
                }
            });
        }
        super.onBindViewHolder(holder, position);
    }

    /**
     * 查看大图
     */
    public void LookBanners(int position) {
        computeBoundsBackward(position);
        GPreviewBuilder.from((Activity) getContext())
                .setData(datas)
                .setCurrentIndex(position)
                .setSingleFling(true)
                .setDrag(false)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .start();
    }

    /**
     * * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < datas.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.item_image);
                thumbView.getGlobalVisibleRect(bounds);
            }
            datas.get(i).setBounds(bounds);
        }
    }

//    @Override
//    public int getItemCount() {
//        return  datas.size();
//    }

    @Override
    public int getItemCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxImages) {
            return datas.size();
        } else {
            return count;
        }
    }

    class ItemViewHolder extends ClickableViewHolder {

        ImageView item_image, img_item_dele;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_image = $(R.id.item_image);
            img_item_dele = $(R.id.img_item_dele);
        }
    }
}
