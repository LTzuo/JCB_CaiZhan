package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.normal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.SubListViewAdapter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.ChildImages;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.cjkj.jcb_caizhan.widget.NineGridView.PhotoAdapter;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.previewlibrary.GPreviewBuilder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 双色球-可折叠二级菜单适配器(待打票)
 * Created by 1 on 2018/2/23.
 */
public class WaitExAdapter extends BaseExpandableListAdapter {

    private static final int REQUEST_CODE = 1;

    private List<TicketEntity> mDatas = new ArrayList<>();

    private Map<Integer, ChildImages> mImageMap = new HashMap<>();

    private Context mContext;

    public WaitExAdapter(Context context) {
        mContext = context;
    }

    private OnChildSubmitBtnClick mChildSubmitBtnClickListener;

    public interface OnChildSubmitBtnClick {
        void onChildSubmitBtnClick(String orderId, ArrayList<ImageItem> Imgs);
    }

    public void setOnSubmitBtnClickListener(OnChildSubmitBtnClick mChildSubmitBtnClickListener) {
        this.mChildSubmitBtnClickListener = mChildSubmitBtnClickListener;
    }

    public void setInfo(List<TicketEntity> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void addInfo(List<TicketEntity> datas) {
        for (TicketEntity bean : datas)
            mDatas.add(bean);
        notifyDataSetChanged();
    }

    /**
     * 一级节点数量
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return mDatas.size();
    }

    /**
     * 指定位置一级节点下二级节点数量
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        //  return mGroupItems.get(groupPosition).mChildList.size();
        return mDatas.isEmpty() ? 0 : 1;
    }


    /**
     * 获取一级节点对象
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mDatas.get(groupPosition);
    }

    /**
     * 获取二级节点对象
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //得修改
        return mDatas.get(groupPosition);
    }

    /**
     * 获取一级节点ID，这里用位置值表示
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取二级节点ID，这里用位置值表示
     *
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
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取一级节点view
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView != null) {
            holder = (GroupViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(mContext, R.layout.group_wait_ticket, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        }
        if (!isExpanded) {
            holder.mgroupimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.xl_jt));
        } else {
            holder.mgroupimage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shou_jt));
        }

        holder.mNumbered_unique.setText(mDatas.get(groupPosition).getSerialNo());
        Glide.with(mContext)
                .load(mDatas.get(groupPosition).getUserPic())
                .centerCrop()
                .crossFade()
                .into(holder.group_header);
        holder.group_lotteryName.setText(mDatas.get(groupPosition).getLotteryName());
        holder.group_lotteryPerion.setText(mDatas.get(groupPosition).getLotteryPerion());
        holder.group_orderId.setText("订单编号" + mDatas.get(groupPosition).getOrderId());
        holder.group_user.setText(mDatas.get(groupPosition).getUserNickName());

        return convertView;
    }

    /**
     * 获取二级节点View
     *
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
        if (convertView != null) {
            holder = (ChildViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(mContext, R.layout.child_wait_ticket, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.childplayType.setText(mDatas.get(groupPosition).getPlayType());
        holder.childorderState.setText(mDatas.get(groupPosition).getOrderState());
        if(mDatas.get(groupPosition).getOrderState().equals("打票错误")){
            holder.Layout_orderNote.setVisibility(View.VISIBLE);
            holder.tv_orderNote.setText("错误说明:"+mDatas.get(groupPosition).getOrderNote());
        }else{
            holder.Layout_orderNote.setVisibility(View.GONE);
        }
        if (!mDatas.get(groupPosition).getOrderTimes().equals("0")) {
            holder.childmultiple.setText("(倍数" + mDatas.get(groupPosition).getOrderTimes() + ")");
        }

        holder.childamount.setText("合计：" + mDatas.get(groupPosition).getAmount() + "元");

        List<OrderDetailListEntity> SourceDateList = FastJsonUtil.getBeanList(mDatas.get(groupPosition).getOrderDetailList(), OrderDetailListEntity.class);
        SubListViewAdapter listViewAdaAdapter = new SubListViewAdapter(mContext, SourceDateList);
        holder.mChildListView.setAdapter(listViewAdaAdapter);

//        if (!mDatas.get(groupPosition).getUserList().isEmpty()) {
//            ToastUtil.ShortToast(mDatas.get(groupPosition).getUserList().size() + "人参与合买");
//        }

        holder.child_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mImageMap.isEmpty() || mImageMap.get(groupPosition).getImgs().isEmpty()){
                    ToastUtil.ShortToast("请上传票据");
                    return;
                }
                mChildSubmitBtnClickListener.onChildSubmitBtnClick(mDatas.get(groupPosition).getOrderId(), mImageMap.get(groupPosition).getImgs());
            }
        });

        holder.mNineRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        if (mImageMap != null && mImageMap.containsKey(groupPosition)) {
            if (!mImageMap.get(groupPosition).getImgs().isEmpty()) {
                mImageMap.get(groupPosition).getAdapter().setDatas(mImageMap.get(groupPosition).getImgs());
            }
        } else {
            PhotoAdapter mPhotoAdapter = new PhotoAdapter(holder.mNineRecyclerView, groupPosition);
            if (!mDatas.get(groupPosition).getOrderPic().isEmpty()) {
                ArrayList<ImageItem>  imgs = new ArrayList<>();
                String[] strs = mDatas.get(groupPosition).getOrderPic().split(",|;");
                for (int i = 0, len = strs.length; i < len; i++) {
                    ImageItem item = new ImageItem(strs[i]);
                    item.setLocal(false);
                    imgs.add(0, item);
                }
                mImageMap.put(groupPosition, new ChildImages(imgs, mPhotoAdapter));
                mPhotoAdapter.setDatas(imgs);
            }else {
                mImageMap.put(groupPosition, new ChildImages(new ArrayList<ImageItem>(), mPhotoAdapter));
                mPhotoAdapter.setDatas(new ArrayList<ImageItem>());
            }
//            mImageMap.put(groupPosition, new ChildImages(new ArrayList<ImageItem>(), mPhotoAdapter));
//            mPhotoAdapter.setDatas(new ArrayList<ImageItem>());
            mPhotoAdapter.setOnItemImageViewClickListener(new PhotoAdapter.onItemImageViewClickListener() {
                @Override
                public void deleItem(int position, int groupPosition) {
                    mImageMap.get(groupPosition).getImgs().remove(position);
                    mImageMap.get(groupPosition).getAdapter().setDatas(mImageMap.get(groupPosition).getImgs());
                }
            });
        }
        holder.mNineRecyclerView.setAdapter(mImageMap.get(groupPosition).getAdapter());
        mImageMap.get(groupPosition).getAdapter().setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if (mImageMap.get(groupPosition).getImgs().size() == position) {
                    openCamera(groupPosition, mImageMap.get(groupPosition).getAdapter());
                } else {
                    LookBanners(mImageMap.get(groupPosition).getImgs(), position);
                }
            }
        });
        return convertView;
    }

    /**
     * 打开相机
     *
     * @param gropPosition
     */
    private void openCamera(int gropPosition, PhotoAdapter mPhotoAdapter) {
        ArrayList<ImageItem> imgLists = (ArrayList<ImageItem>) mImageMap.get(gropPosition).getImgs();
        Album.camera(mContext)
//        Album.album(mContext)
//                .multipleChoice()
//                .columnCount(4)
                  .image()
                // .filePath()
                .requestCode(REQUEST_CODE)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        mImageMap.remove(gropPosition);
                        ImageItem item = new ImageItem(result);
                        item.setLocal(true);
                        imgLists.add(0, item);
                        mImageMap.put(gropPosition, new ChildImages(imgLists, mPhotoAdapter));
                        //mImgesAdapter.addInfo(item);
                        mPhotoAdapter.setDatas(imgLists);
                    }
                }).start();
//                .onResult(new Action<ArrayList<AlbumFile>>() {
//                    @Override
//                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
//                        mImageMap.remove(gropPosition);
//                        ImageItem item = new ImageItem(result.get(0).getThumbPath());
//                        imgLists.add(0, item);
//                        mImageMap.put(gropPosition, new ChildImages(imgLists, mPhotoAdapter));
//                        //mImgesAdapter.addInfo(item);
//                        mPhotoAdapter.setDatas(imgLists);
//                    }
//                })
//                .onCancel(new Action<String>() {
//                    @Override
//                    public void onAction(int requestCode, @NonNull String result) {
//                    }
//                })
//                .start();
    }

    /**
     * 查看大图
     *
     * @param datas 每组数据
     * @param index 每组数据中对应的index
     */
    private void LookBanners(ArrayList<ImageItem> datas, int index) {
        GPreviewBuilder.from((Activity) mContext)
                .setData(datas)
                .setCurrentIndex(index)
                .setSingleFling(true)
                .setDrag(false)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .start();
    }

    /**
     * 二级菜单是否可选（true为可选，false为不可选，也就是响应和不响应点击事件）
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        @Bind(R.id.Numbered_unique)
        TextView mNumbered_unique;  //编号
        @Bind(R.id.groupIcon)
        ImageView mgroupimage;  //向上向下箭头
        @Bind(R.id.group_header)
        AvatarImageView group_header; //用户头像
        @Bind(R.id.group_lotteryName)
        TextView group_lotteryName;//球类
        @Bind(R.id.group_lotteryPerion)
        TextView group_lotteryPerion;//期数
        @Bind(R.id.group_orderId)
        TextView group_orderId;//订单编号
        @Bind(R.id.group_user)
        TextView group_user;

        public GroupViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    class ChildViewHolder {
        @Bind(R.id.mChildListView)
        SubListView mChildListView;
        @Bind(R.id.mNineRecyclerView)
        RecyclerView mNineRecyclerView;
        @Bind(R.id.childplayType)
        TextView childplayType;//玩法
        @Bind(R.id.childamount)
        TextView childamount;
        @Bind(R.id.childorderState)
        TextView childorderState;
        @Bind(R.id.childmultiple)
        TextView childmultiple;
        @Bind(R.id.child_btn_submit)
        TextView child_btn_submit;
        @Bind(R.id.Layout_orderNote)
        LinearLayout Layout_orderNote;
        @Bind(R.id.tv_orderNote)
        TextView tv_orderNote;
        public ChildViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
