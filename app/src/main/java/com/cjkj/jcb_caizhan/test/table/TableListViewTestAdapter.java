package com.cjkj.jcb_caizhan.test.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * Created by 1 on 2018/3/1.
 */
public class TableListViewTestAdapter extends BaseAdapter {

    private List<TableTextEntity> mDatas = new ArrayList<>();

    private Context context;

    private void addHeader(){

    }

    public TableListViewTestAdapter (Context context){
        this.context = context;
    }

    public void setInfo(List<TableTextEntity> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup) {
        ViewHolder itemViewHolder = null;
        if(v == null){
          v = LayoutInflater.from(context).inflate(R.layout.item_table_test, null,false);
            itemViewHolder = new ViewHolder(v);
            v.setTag(itemViewHolder);
        }else{
            itemViewHolder = (ViewHolder) v.getTag();
        }
        itemViewHolder.text1.setText(mDatas.get(position).getTitle1());
        itemViewHolder.text2.setText(mDatas.get(position).getTitle2());
        itemViewHolder.text3.setText(mDatas.get(position).getTitle3());
        itemViewHolder.text4.setText(mDatas.get(position).getTitle4());
        itemViewHolder.text5.setText(mDatas.get(position).getTitle5());


        if(mDatas.get(position).getTitle1().equals("合买人")){
            itemViewHolder.head_img.setVisibility(View.GONE);
            itemViewHolder.text2.setBackground(null);
            itemViewHolder.text2.setTextColor(context.getResources().getColor(R.color.black_alpha_60));
        }else{
            itemViewHolder.head_img.setVisibility(View.VISIBLE);
            itemViewHolder.text2.setTextColor(context.getResources().getColor(R.color.white));
            itemViewHolder.text2.setBackground(context.getResources().getDrawable(R.drawable.shape_half_red_bg));
        }
        return v;
    }

    class ViewHolder{
        @Bind(R.id.text1)
        TextView text1;
        @Bind(R.id.text2)
        TextView text2;
        @Bind(R.id.text3)
        TextView text3;
        @Bind(R.id.text4)
        TextView text4;
        @Bind(R.id.text5)
        TextView text5;
        @Bind(R.id.head_img)
        AvatarImageView head_img;
        public ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }

}
