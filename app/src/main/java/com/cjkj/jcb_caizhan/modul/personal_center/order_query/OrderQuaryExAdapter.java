package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.now.ChildListViewAdapter;
import com.cjkj.jcb_caizhan.test.table.TableListViewTestAdapter;
import com.cjkj.jcb_caizhan.test.table.TableTextEntity;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.cjkj.jcb_caizhan.widget.ExListView.AbsExListViewAdapter;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseChildBean;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseGroupBean;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心-可折叠二级菜单适配器(订单查询)
 * Created by 1 on 2018/3/5.
 */
public class OrderQuaryExAdapter extends AbsExListViewAdapter{

    private List<ExBaseGroupBean> mDatas = new ArrayList<>();

    public OrderQuaryExAdapter(Context mContext){
        super(mContext);
    }

    public void setInfo(List<ExBaseGroupBean> mDatas){
        this.mDatas = mDatas;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.group_no_out_ticket,viewGroup,false);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (GroupViewHolder) view.getTag();
        }
        holder.groupIcon.setImageResource(isExpanded ? R.drawable.shou_jt : R.drawable.xl_jt);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.child_order_query,viewGroup,false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ChildViewHolder) view.getTag();
        }
        ChildListViewAdapter mChildListViewAdapter = new ChildListViewAdapter(getContext());
        holder.childlistView.setAdapter(mChildListViewAdapter);
        TableListViewTestAdapter mTableTestAdapter = new TableListViewTestAdapter(getContext());
        holder.childTablelistView.setAdapter(mTableTestAdapter);
        List<TableTextEntity> mDatas = new ArrayList<>();
        mDatas.add(new TableTextEntity("合买人","等级","出资","份额","奖金/加奖"));
        mDatas.add(new TableTextEntity("林天佐","营长","2000万","20.0%","20万"));
        mDatas.add(new TableTextEntity("林亮","军长","9000万","80.0%","90万"));
        mTableTestAdapter.setInfo(mDatas);
        return view;
    }

    @Override
    public ExBaseGroupBean getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    @Override
    public ExBaseChildBean getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * group视图
     */
   public class GroupViewHolder extends ExBaseViewHolder{
        ImageView groupIcon;
        public GroupViewHolder(View itemView) {
            super(itemView);
            groupIcon = $(R.id.img_Indicator);
        }
    }

    /**
     * child视图
     */
    public class ChildViewHolder extends ExBaseViewHolder {
        SubListView childlistView,childTablelistView;
        public ChildViewHolder(View itemView) {
            super(itemView);
            childlistView = $(R.id.childlistView);
            childTablelistView = $(R.id.childTablelistView);
        }
    }


}
