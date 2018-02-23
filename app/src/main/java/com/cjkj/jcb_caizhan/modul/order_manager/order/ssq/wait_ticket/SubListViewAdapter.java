package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.wait_ticket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import java.util.ArrayList;
import java.util.List;

/**
 * （双色球）不可滑动ListvListView适配器
 * Created by 1 on 2018/2/23.
 */
public class SubListViewAdapter extends BaseAdapter {

    List<String> mDatas = new ArrayList<>();
    Context mContext;

    public SubListViewAdapter(Context context,List<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void addInfo(String info,int position){
        mDatas.add(position, info);
        notifyDataSetChanged();
//        notifyItemInserted(position);//通知演示插入动画
//        notifyItemRangeChanged(position, mDatas.size() - position);//通知数据与界面重新绑定
    }

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
        ViewHolder holder =null;
        if (convertView ==null) {
            holder = new ViewHolder();
            convertView  = View.inflate(mContext, R.layout.item_ssq_wait_child, null);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_text);
            holder.tv_index = (TextView) convertView.findViewById(R.id.tv_index);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_index.setText("【"+(position+1)+"】");
        holder.mTextView.setText(mDatas.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView tv_index,mTextView;
    }

}
