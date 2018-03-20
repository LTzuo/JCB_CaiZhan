package com.cjkj.jcb_caizhan.modul.Personal_Center.launch_crowd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;

import butterknife.ButterKnife;

/**
 *
 * Created by 1 on 2018/3/12.
 */
public class TagGridAdapter extends BaseAdapter {

    private String mDatas[];

    private boolean selsectDatas[] = {
            false,false,false,false,false,false
    };

    private Context mContext;

    public TagGridAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas = mContext.getResources().getStringArray(R.array.FlowlayoutTagValues);
    }

    public void SelsectItem(int index){
        for (int i = 0; i <= selsectDatas.length - 1; i++) {
            selsectDatas[i] = false;
        }
        selsectDatas[index] = true;
        notifyDataSetChanged();
    }

    public String getSelect(){
        String select = "";
        for(int i = 0;i<selsectDatas.length;i++){
            if(selsectDatas[i]){
                select = mDatas[i];
            }
        }
        return select.replace("份","");
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
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_custom_layout, viewGroup, false);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.mItemText.setText(mDatas[i]);
        if(selsectDatas[i]){
            holder.mItemText.setBackground(mContext.getResources().getDrawable(R.drawable.shape_bg_red_selected_bg));
            holder.mItemText.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            holder.mItemText.setBackground(mContext.getResources().getDrawable(R.drawable.shape_bg_gray_unselected_bg));
            holder.mItemText.setTextColor(mContext.getResources().getColor(R.color.black_alpha_70));
        }
        return v;
    }

    class ViewHolder {
        TextView mItemText;

        public ViewHolder(View v) {
            super();
            ButterKnife.bind(this, v);
            mItemText = (TextView) v.findViewById(R.id.itemText);
        }
    }

}
