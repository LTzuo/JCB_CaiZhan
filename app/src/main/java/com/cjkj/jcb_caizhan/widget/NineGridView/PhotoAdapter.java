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
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格图片适配器,嵌套ExpandableListView的RecyclerView使用的适配器
 * Created by 1 on 2018/2/12.
 */
public class PhotoAdapter extends AbsRecyclerViewAdapter {

    private List<ImageItem> datas = new ArrayList<>();
    private int groupPosition;//对应ExpandableListView的Group的index
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认3张
     */
    private int maxImages = 4;

    public interface onItemImageViewClickListener {
        void deleItem(int groupPosition,int position);
    }

    private onItemImageViewClickListener mListener;

    public void setOnItemImageViewClickListener(onItemImageViewClickListener listener) {
        this.mListener = listener;
    }

    public void setDatas(List<ImageItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

//    public void addInfo(ImageItem img) {
//        datas.add(0, img);
//        notifyItemInserted(0);//通知演示插入动画
//        notifyItemRangeChanged(0, datas.size() - 0);//通知数据与界面重新绑定
//    }

    public PhotoAdapter(RecyclerView mRecyvlerView,int groupPosition) {
        super(mRecyvlerView);
        this.groupPosition = groupPosition;
    }

    @Override
    public AbsRecyclerViewAdapter.ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_repairgrid, parent, false));
    }

    @Override
    public void onBindViewHolder(AbsRecyclerViewAdapter.ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            ImageView itemimg = itemViewHolder.item_image;
            ImageView item_dele = itemViewHolder.item_dele;

            if (position == datas.size()) {
                if (position == 3) {
                    itemimg.setVisibility(View.GONE);
                    item_dele.setVisibility(View.GONE);
                } else {
                    Glide.with(getContext())
                            .load(R.drawable.addphoto)
                            .priority(Priority.HIGH)
                            .centerCrop()
                            .into(itemimg);
                    item_dele.setVisibility(View.GONE);
                }
            } else {
                Glide.with(getContext())
                        .load(datas.get(position).getUrl())
                        .priority(Priority.HIGH)
                        .into(itemimg);
                item_dele.setVisibility(View.VISIBLE);
                item_dele.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.deleItem(position,groupPosition);
                    }
                });
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }


    class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView item_image, item_dele;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_image = $(R.id.item_img);
            item_dele = $(R.id.item_dele);
        }
    }
}
