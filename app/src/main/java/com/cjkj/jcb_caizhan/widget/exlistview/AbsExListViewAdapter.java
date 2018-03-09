package com.cjkj.jcb_caizhan.widget.ExListView;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

/**
 * ExpandableListViewAdapter适配器父类、支持扩展
 * GroupViewHolder\ChildViewHolder
 * Created by 1 on 2018/3/5.
 */
public abstract class AbsExListViewAdapter extends BaseExpandableListAdapter {

    private Context context;

//    private int groupResource,childResource;

    //    public AbsExListViewAdapter(Context context,int groupResource,int childResource){
//        this.context = context;
//        this.groupResource = groupResource;
//        this.childResource = childResource;
//    }
    public AbsExListViewAdapter(Context context) {
        this.context = context;
    }


    public Context getContext() {
        return this.context;
    }

//    protected abstract void getGroupView(ExBaseViewHolder groupViewHolder,int groupPosition,boolean isExpanded,ExBaseGroupBean data);
//
//    protected abstract void getChildView(ExBaseViewHolder childViewHolder,int groupPosition,int childPosition,boolean isLastChild,ExBaseGroupBean data);

//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
//
//        return null;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
//        return null;
//    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
