package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.now;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.cjkj.jcb_caizhan.R;
import butterknife.ButterKnife;

/**
 * child-中球类订单列表适配器
 * Created by 1 on 2018/3/2.
 */
public class ChildListViewAdapter extends BaseAdapter{

    private Context mContext;

    public ChildListViewAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(v == null){
            v = LayoutInflater.from(mContext).inflate(R.layout.item_child_qiu_listview,null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        return v;
    }

    class ViewHolder{

        public ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }
}
