package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.now;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.test.table.TableListViewTestAdapter;
import com.cjkj.jcb_caizhan.test.table.TableTextEntity;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.cjkj.jcb_caizhan.widget.exlistview.CommonExpandableListAdapter;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 委托兑奖-当前委托适配器
 * Created by 1 on 2018/2/28.
 */
public class CashPrizeExAdapter extends CommonExpandableListAdapter<ChildCashPrizeEntity,GroupCashPrizeEntity>{

    private Map<Integer,Boolean> map=new HashMap<>();// 存放已被选中的CheckBox

    public CashPrizeExAdapter(Context mContext, int childResource, int groupResource){
        super(mContext,childResource,groupResource);
    }

    CheckBoxSelectListener mCheckBoxSelectListener;
    public interface CheckBoxSelectListener{
        void onCheckBoxSelect(boolean isSelect);
    }

    public void setOnCheckBoxSelectListener(CheckBoxSelectListener mCheckBoxSelectListener){
        this.mCheckBoxSelectListener = mCheckBoxSelectListener;
    }

    /**
     * 多选框被选中
     */
    private void showCheckBoxSelectDialog(){
        boolean isSelect = false;
        for (Integer key : map.keySet()) {
            if(map.get(key)){
                isSelect = true;
            }
        }
        mCheckBoxSelectListener.onCheckBoxSelect(isSelect);
    }

    @Override
    protected void getChildView(ViewHolder holder, int groupPositon, int childPositon, boolean isLastChild, ChildCashPrizeEntity data) {
        //球类订单列表展示
        SubListView childlistView = holder.getView(R.id.childlistView);
        ChildListViewAdapter mChildListViewAdapter = new ChildListViewAdapter(getContext());
        childlistView.setAdapter(mChildListViewAdapter);
        //合买列表展示
        SubListView childTablelistView = holder.getView(R.id.childTablelistView);
        TableListViewTestAdapter mTableTestAdapter = new TableListViewTestAdapter(getContext());
//        View headView = LayoutInflater.from(getContext()).inflate(R.layout.item_table_test, null,false);
//        childTablelistView.addHeaderView(headView);
        childTablelistView.setAdapter(mTableTestAdapter);
        List<TableTextEntity> mDatas = new ArrayList<>();
        mDatas.add(new TableTextEntity("合买人","等级","出资","份额","奖金/加奖"));
        mDatas.add(new TableTextEntity("林天佐","营长","2000万","20.0%","20万"));
        mDatas.add(new TableTextEntity("林亮","军长","9000万","80.0%","90万"));
        mTableTestAdapter.setInfo(mDatas);
    }

    @Override
    protected void getGroupView(ViewHolder holder, int groupPositon, boolean isExpanded, GroupCashPrizeEntity data) {
        SmoothCheckBox group_checkbox = holder.getView(R.id.group_checkbox);
        TextView group_name = holder.getView(R.id.group_name);
        TextView group_stage = holder.getView(R.id.group_stage);
        ImageView arrowImage = holder.getView(R.id.groupIcon);
        AvatarImageView group_img = holder.getView(R.id.group_img);

        group_name.setText(data.getGroupName());
        group_stage.setText(data.getStage());
        arrowImage.setImageResource(isExpanded ? R.drawable.shou_jt : R.drawable.xl_jt);

        Glide.with(getContext())
                .load(R.mipmap.flight)
                .placeholder(R.drawable.default_lottery)
                .error(R.drawable.default_lottery)
                .into(group_img);

        group_checkbox.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if(b==true){
                    map.put(groupPositon,true);
                }else {
                    map.remove(groupPositon);
                }
                showCheckBoxSelectDialog();
            }
        });

        if(map!=null&&map.containsKey(groupPositon)){
            group_checkbox.setChecked(true);
        }else {
            group_checkbox.setChecked(false);
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

}
