package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.UserListEntity;
import com.cjkj.jcb_caizhan.test.table.TableTextEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 合买用户表格适配器
 * Created by 1 on 2018/3/1.
 */
public class UserTableListViewAdapter extends BaseAdapter {

    private List<UserListEntity> mDatas = new ArrayList<>();

    private Context context;

    public UserTableListViewAdapter(Context context){
        this.context = context;
    }

    public void setInfo(List<UserListEntity> mDatas) {
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
          v = LayoutInflater.from(context).inflate(R.layout.test_item_table, null,false);
            itemViewHolder = new ViewHolder(v);
            v.setTag(itemViewHolder);
        }else{
            itemViewHolder = (ViewHolder) v.getTag();
        }
        itemViewHolder.text1.setText(mDatas.get(position).getUserNickName());
        itemViewHolder.text2.setText(mDatas.get(position).getUserGrade());
        itemViewHolder.text3.setText(mDatas.get(position).getUserAmount());
        itemViewHolder.text4.setText(mDatas.get(position).getPercent());
        itemViewHolder.text5.setText(mDatas.get(position).getWinAmount());


        if(mDatas.get(position).getUserNickName().equals("合买人")||
                mDatas.get(position).getUserNickName().equals("参与众筹")){
            itemViewHolder.head_img.setVisibility(View.GONE);
            itemViewHolder.text2.setBackground(null);
            itemViewHolder.text2.setTextColor(context.getResources().getColor(R.color.black_alpha_60));
        }else{
            itemViewHolder.head_img.setVisibility(View.VISIBLE);
            itemViewHolder.text2.setTextColor(context.getResources().getColor(R.color.white));
            itemViewHolder.text2.setBackground(context.getResources().getDrawable(R.drawable.shape_half_red_bg));

            if(!mDatas.get(position).getUserPic().isEmpty()){
                Glide.with(context)
                        .load(mDatas.get(position).getUserPic())
                        .centerCrop()
                        .crossFade()
                        .into(itemViewHolder.head_img);
            }

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
