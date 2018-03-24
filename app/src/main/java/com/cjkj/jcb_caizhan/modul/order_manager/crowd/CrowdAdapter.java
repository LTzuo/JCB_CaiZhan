package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.crowd_details.CrowdDetailsActivity;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.GridAdapter;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.POST;

/**
 * 订单管理-众筹适配器
 * Created by 1 on 2018/2/22.
 */
public class CrowdAdapter extends AbsRecyclerViewAdapter {

    private List<CrowdEntity> mDatas = new ArrayList<>();

    public CrowdAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public void setInfo(List<CrowdEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_crowd, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tv_crowdTitle.setText(mDatas.get(position).getCrowdTitle());
            itemViewHolder.tv_amount.setText("￥" + mDatas.get(position).getAmount());
            if (mDatas.get(position).getState().equals("3")) {
                itemViewHolder.tv_crowdState.setText("已中奖");
            } else if (mDatas.get(position).getState().equals("4")) {
                itemViewHolder.tv_crowdState.setText("未中奖");
            } else if (mDatas.get(position).getState().equals("5")) {
                itemViewHolder.tv_crowdState.setText("已撤单");
            } else {
                itemViewHolder.tv_crowdState.setText(mDatas.get(position).getCrowdState());
            }
            itemViewHolder.tv_content.setText(mDatas.get(position).getContent());
            itemViewHolder.tv_endTime.setText("截止时间 " + mDatas.get(position).getEndTime());
            itemViewHolder.tv_fenshu.setText("已众筹" + mDatas.get(position).getOkPart() + "份," + "剩余" + mDatas.get(position).getNoPart() + "份");
            itemViewHolder.Tv_LookDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), CrowdDetailsActivity.class);
                    intent.putExtra("orderId", mDatas.get(position).getOrderId());
                    getContext().startActivity(intent);
                }
            });

            List<ImageItem> imgs = new ArrayList<>();
            if (!mDatas.get(position).getCrowdPics().isEmpty()) {
                String[] strs = mDatas.get(position).getCrowdPics().split(",|;");
                for (int i = 0, len = strs.length; i < len; i++) {
                    ImageItem item = new ImageItem(strs[i]);
                    item.setLocal(false);
                    imgs.add(0, item);
                }
            }
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 3);
            itemViewHolder.mGridRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            GridAdapter mGridAdapter = new GridAdapter(itemViewHolder.mGridRecyclerView);
            mGridAdapter.setGridManager(mGridLayoutManager);
            mGridAdapter.setDatas(imgs);
            itemViewHolder.mGridRecyclerView.setAdapter(mGridAdapter);
            mGridAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position, ClickableViewHolder holder) {
                    mGridAdapter.LookBanners(position);
                }
            });
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    private class ItemViewHolder extends ClickableViewHolder {

        TextView tv_crowdTitle, tv_amount, tv_crowdState, tv_content,
                tv_endTime, tv_fenshu;
        TextView Tv_LookDetails;

        RecyclerView mGridRecyclerView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_crowdTitle = $(R.id.tv_crowdTitle);
            tv_amount = $(R.id.tv_amount);
            tv_crowdState = $(R.id.tv_crowdState);
            tv_content = $(R.id.tv_content);
            tv_endTime = $(R.id.tv_endTime);
            tv_fenshu = $(R.id.tv_fenshu);
            Tv_LookDetails = $(R.id.Tv_LookDetails);
            mGridRecyclerView = $(R.id.mGridRecyclerView);
        }
    }
}
