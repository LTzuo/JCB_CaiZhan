package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.wait_ticket;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.personal_center.launch_crowd.ImageItem;
import com.cjkj.jcb_caizhan.modul.personal_center.launch_crowd.NineGridAdapter;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 双色球-可折叠二级菜单适配器(待打票)
 * Created by 1 on 2018/2/23.
 */
public class SSQ_ExAdapter extends BaseExpandableListAdapter {

    private static final int REQUEST_CODE = 1;
   // List<NineGridAdapter> mImageAdapterList = new ArrayList<>();
    private Map<Integer,ArrayList<ImageItem>> mImageMap =new HashMap<>();

    ArrayList<ImageItem> mImageList = new ArrayList<>();//临时的列表

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
            holder.mNumbered_unique = (TextView) convertView.findViewById(R.id.Numbered_unique);
            holder.mgroupimage =(ImageView)convertView.findViewById(R.id.groupIcon);
            convertView.setTag(holder);
        }
        if(!isExpanded){
            holder.mgroupimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xl_jt));
        }else{
            holder.mgroupimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shou_jt));
        }
       // holder.mNumbered_unique.setText(groupPosition);
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
            holder.mChildListView = (SubListView) convertView.findViewById(R.id.mChildListView);
            holder.mNineRecyclerView = (RecyclerView) convertView.findViewById(R.id.mNineRecyclerView);

            convertView.setTag(holder);
        }
        List<String> SourceDateList = new ArrayList<>();
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SourceDateList.add("01 02 03 04 05");
        SubListViewAdapter listViewAdaAdapter = new SubListViewAdapter(mContext,SourceDateList);
        holder.mChildListView.setAdapter(listViewAdaAdapter);

        mImageList.clear();
        mImageMap.put(groupPosition,mImageList);
        NineGridAdapter mNineGridAdapter = new NineGridAdapter(holder.mNineRecyclerView);
      //  mImageAdapterList.add(mNineGridAdapter);
        holder.mNineRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mNineGridAdapter.setDatas(groupPosition,mImageMap.get(groupPosition));
        mNineGridAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
//                if (mNineGridAdapter.getItemCount() == 1) {
//                    openCamera(groupPosition);
//                } else {
                    if (mImageMap.get(groupPosition).size() < 3) {
                            openCamera(groupPosition,mNineGridAdapter);
                    } else {
                       // LookBanners(position);
                        ToastUtil.ShortToast("浏览第"+groupPosition+"组");
                    }
//                }
            }
        });
        holder.mNineRecyclerView.setAdapter(mNineGridAdapter);
        return convertView;
    }

    /**
     * 打开相机
     * @param gropPosition
     */
    private void openCamera(int gropPosition,NineGridAdapter mNineGridAdapter) {
        ArrayList<ImageItem> imgLists = mImageMap.get(gropPosition);
        Album.camera(mContext) // 相机功能。
                .image() // 拍照。
               // .filePath() // 文件保存路径，非必须。
                .requestCode(REQUEST_CODE)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ImageItem item = new ImageItem(result);
                        imgLists.add(0,item);
                        mImageMap.put(gropPosition,imgLists);
                        mNineGridAdapter.setDatas(gropPosition,imgLists);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();
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
        TextView mNumbered_unique;
        ImageView mgroupimage;
    }

    class ChildViewHolder{
        SubListView mChildListView;
        RecyclerView mNineRecyclerView;
        TextView mTextView;
    }

    /**
     * 一级节点对象
     */
    public static class GroupItem {
       public String title;
       public List<ChildItem> mChildList;
    }

    /**
     * 二级节点对象
     */
    public static class ChildItem {
        public String message;
    }
}
