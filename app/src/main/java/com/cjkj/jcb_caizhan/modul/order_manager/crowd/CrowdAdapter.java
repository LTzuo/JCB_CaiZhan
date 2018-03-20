package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.crowd_details.CrowdDetailsActivity;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理-众筹适配器
 * Created by 1 on 2018/2/22.
 */
public class CrowdAdapter extends AbsRecyclerViewAdapter {

    private List<CrowdEntity> mDatas = new ArrayList<>();

    public CrowdAdapter(RecyclerView recyclerView) {
        super(recyclerView);
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
//            itemViewHolder.mItemIcon.setImageResource(itemIcons[position]);
//            itemViewHolder.mItemText.setText(itemNames[position]);
            itemViewHolder.Tv_LookDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtils.Goto((Activity) getContext(), CrowdDetailsActivity.class);
                }
            });
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
//        return mDtas == null ? 0 : mDtas.size();
        return 5;
    }


    private class ItemViewHolder extends ClickableViewHolder {

//        ImageView mItemIcon;
//        TextView mItemText, mItemContext;
          TextView Tv_LookDetails;
        public ItemViewHolder(View itemView) {
            super(itemView);
//            mItemIcon = $(R.id.item_icon);
//            mItemText = $(R.id.item_title);
//            mItemContext = $(R.id.item_content);
            Tv_LookDetails = $(R.id.Tv_LookDetails);
        }
    }
}
