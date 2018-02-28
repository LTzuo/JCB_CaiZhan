package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.wait_ticket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.widget.SubListView;
import java.util.ArrayList;
import java.util.List;

/**
 * 双色球-可折叠二级菜单适配器(待打票)
 * Created by 1 on 2018/2/23.
 */
public class SSQ_ExAdapter extends BaseExpandableListAdapter {

    //一级节点数据
    private List<GroupItem> mGroupItems;
    private Context mContext;

    public SSQ_ExAdapter(Context context,List<GroupItem> mData) {
        mContext = context;
        mGroupItems = mData;
    }

    /**
     * 一级节点数量
     * @return
     */
    @Override
    public int getGroupCount() {
        return mGroupItems.size();
    }

    /**
     * 指定位置一级节点下二级节点数量
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
      //  return mGroupItems.get(groupPosition).mChildList.size();
        return 1;
    }

    /**
     * 获取一级节点对象
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mGroupItems.get(groupPosition);
    }

    /**
     * 获取二级节点对象
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroupItems.get(groupPosition).mChildList.get(childPosition);
    }

    /**
     * 获取一级节点ID，这里用位置值表示
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取二级节点ID，这里用位置值表示
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * ID是否稳定
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取一级节点view
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if(convertView!=null){
            holder = (GroupViewHolder) convertView.getTag();
        }else{
            convertView = View.inflate(mContext,R.layout.group_ssq_wait_ticket, null);
            holder = new GroupViewHolder();
            holder.mTv = (TextView) convertView.findViewById(R.id.group_text);
            holder.mgroupimage =(ImageView)convertView.findViewById(R.id.img_Indicator);
            convertView.setTag(holder);
        }
        if(!isExpanded){
            holder.mgroupimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xl_jt));
        }else{
            holder.mgroupimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shou_jt));
        }
        holder.mTv.setText(mGroupItems.get(groupPosition).title);
        return convertView;
    }

    /**
     * 获取二级节点View
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if(convertView!=null){
            holder = (ChildViewHolder) convertView.getTag();
        }else{
            holder = new ChildViewHolder();
            convertView = View.inflate(mContext,R.layout.child_ssq_wait_ticket, null);
            holder.mTextView = (TextView) convertView.findViewById(R.id.order_number);
            holder.mChildListView = (SubListView) convertView.findViewById(R.id.mChildListView);
            holder.btn0 = (TextView) convertView.findViewById(R.id.btn0);
            convertView.setTag(holder);
        }
        holder.mTextView.setText(mGroupItems.get(groupPosition).mChildList.get(childPosition).message);
        List<String> SourceDateList = new ArrayList<>();
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SubListViewAdapter listViewAdaAdapter = new SubListViewAdapter(mContext,SourceDateList);
        holder.mChildListView.setAdapter(listViewAdaAdapter);
        holder.btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToastUtil.ShortToast("上传照片"+groupPosition);
                //listViewAdaAdapter.addInfo("01 02 03 04 05",0);
            }
        });
        return convertView;
    }

    /**
     * 二级菜单是否可选（true为可选，false为不可选，也就是响应和不响应点击事件）
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder{
        TextView mTv;
        ImageView mgroupimage;
    }

    class ChildViewHolder{
        SubListView mChildListView;
        TextView mTextView;
        TextView btn0;
    }

    /**
     * 一级节点对象
     */
    public static class GroupItem {
        String title;
        List<ChildItem> mChildList;
    }

    /**
     * 二级节点对象
     */
    public static class ChildItem {
        String message;
    }
}
