package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;

import butterknife.ButterKnife;

/**
 * 委托兑奖-彩种切换适配器
 * Created by 1 on 2018/2/27.
 */
public class CustomGridAdapter extends BaseAdapter{

    private Context mContext;

    private String mDatas[] ;

    private boolean selsectDatas[] = {
            false,false,false,false,false,false,false,false,false,false,false,false,false
    };

    public CustomGridAdapter(Context context){
        this.mContext = context;
        mDatas  = mContext.getResources().getStringArray(R.array.custom_caizhong);
    }

    public void SelsectItem(int index){
        for (int i = 0; i <= selsectDatas.length - 1; i++) {
            selsectDatas[i] = false;
        }
        selsectDatas[index] = true;
        notifyDataSetChanged();
    }

    public String getSelectItem(){
        String selecetText = null;
        for(int i = 0;i<= selsectDatas.length - 1;i++){
            if(selsectDatas[i]){
                selecetText = mDatas[i];
            }
        }
        return selecetText;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int i) {
        return mDatas[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(v == null){
            v = LayoutInflater.from(mContext).inflate(R.layout.item_custom_layout,null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
           holder.mItemText.setText(mDatas[i]);
        if(selsectDatas[i]){
            holder.mItemText.setBackground(mContext.getResources().getDrawable(R.drawable.shape_custom_selected_bg));
            holder.mItemText.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.mItemText.setBackground(mContext.getResources().getDrawable(R.drawable.shape_custom_unselected_bg));
            holder.mItemText.setTextColor(mContext.getResources().getColor(R.color.black_alpha_60));
        }
        return v;
    }

    class ViewHolder{
        TextView mItemText;
        public ViewHolder(View v){
            super();
            ButterKnife.bind(this,v);
            mItemText = (TextView) v.findViewById(R.id.itemText);
        }
    }

}
