package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

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

        holder.item_index.setText(mDatas.get(position).getOrderGroup());
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

    class ViewHolder {
        @Bind(R.id.item_index)
        TextView item_index;
        @Bind(R.id.item_number)
        TextView item_number;
        @Bind(R.id.item_beishu)
        TextView item_beishu;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
