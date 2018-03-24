package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details.OddsGridListAdapter;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NoScollGridView;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yhq.dialog.core.DialogBuilder;

/**
 * 不可滑动ListvListView适配器
 * Created by 1 on 2018/2/23.
 */
public class SubListViewAdapter extends BaseAdapter {

    List<OrderDetailListEntity> mDatas = new ArrayList<>();
    Context mContext;

    public SubListViewAdapter(Context context, List<OrderDetailListEntity> datas) {
        mContext = context;
        mDatas = datas;
        notifyDataSetChanged();
    }

//    public void addInfo(String info,int position){
//        mDatas.add(position, info);
//        notifyDataSetChanged();
////        notifyItemInserted(position);//通知演示插入动画
////        notifyItemRangeChanged(position, mDatas.size() - position);//通知数据与界面重新绑定
//    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_child_qiu_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mDatas.get(position).getOrderGroup().equals("odds")){
            holder.tv_seeodds.setVisibility(View.VISIBLE);
            holder.tv_seeodds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LookOdds(mDatas.get(position).getOddsList());
                }
            });
        }else{
            holder.tv_seeodds.setVisibility(View.GONE);
            holder.item_index.setText(mDatas.get(position).getOrderGroup());
        }

        if(mDatas.get(position).getOrderGroup().indexOf("蓝") != -1 || mDatas.get(position).getOrderGroup().indexOf("后") != -1){
            holder.item_number.setTextColor(mContext.getResources().getColor(R.color.blue));
        }else{
            holder.item_number.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        holder.item_number.setText(mDatas.get(position).getOrderContent());
        if(!mDatas.get(position).getOrderTimes().equals("0")){
            holder.item_beishu.setText("倍数"+mDatas.get(position).getOrderTimes());
        }
        return convertView;
    }

    /**弹出对话框查看赔率**/
    private void LookOdds(String odds){
        //赔率数据
        List<OddsEntity> oddsList = FastJsonUtil.getBeanList(odds, OddsEntity.class);
        View customView =
                View.inflate(mContext, R.layout.layout_noscrollgridview, null);
        NoScollGridView gridView = (NoScollGridView) customView.findViewById(R.id.mAddsGridView);
        OddsGridListAdapter mOddsGridListAdapter = new OddsGridListAdapter(mContext, oddsList);
        gridView.setAdapter(mOddsGridListAdapter);
        DialogBuilder.otherDialog(mContext).setContentView(customView).show();
    }

    class ViewHolder {
        @Bind(R.id.item_index)
        TextView item_index;
        @Bind(R.id.item_number)
        TextView item_number;
        @Bind(R.id.item_beishu)
        TextView item_beishu;

        @Bind(R.id.tv_seeodds)
        TextView tv_seeodds;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
