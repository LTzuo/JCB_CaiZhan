package com.cjkj.jcb_caizhan.modul.personal_center.no_out_ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.now.ChildListViewAdapter;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.cjkj.jcb_caizhan.widget.ExListView.AbsExListViewAdapter;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseChildBean;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseGroupBean;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心-可折叠二级菜单适配器(未出票)
 * Created by 1 on 2018/3/5.
 */
public class NoOutTicketExAdapter  extends AbsExListViewAdapter{

    private List<ExBaseGroupBean> mDatas = new ArrayList<>();

    public NoOutTicketExAdapter(Context mContext){
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
            view = LayoutInflater.from(getContext()).inflate(R.layout.child_no_out_ticket,viewGroup,false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ChildViewHolder) view.getTag();
        }
        ChildListViewAdapter mChildListViewAdapter = new ChildListViewAdapter(getContext());
        holder.childlistView.setAdapter(mChildListViewAdapter);
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
        SubListView childlistView;
        public ChildViewHolder(View itemView) {
            super(itemView);
            childlistView = $(R.id.childlistView);
        }
    }


}
