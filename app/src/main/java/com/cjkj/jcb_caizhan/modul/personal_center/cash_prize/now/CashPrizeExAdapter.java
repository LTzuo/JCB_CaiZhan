package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.now;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.exlistview.CommonExpandableListAdapter;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import java.util.HashMap;
import java.util.Map;

/**
 * 委托兑奖-当前委托适配器
 * Created by 1 on 2018/2/28.
 */
public class CashPrizeExAdapter extends CommonExpandableListAdapter<ChildCashPrizeEntity,GroupCashPrizeEntity>{

    private Map<Integer,Boolean> map=new HashMap<>();// 存放已被选中的CheckBox

    public CashPrizeExAdapter(Context mContext, int childResource, int groupResource){
        super(mContext,childResource,groupResource);
    }

    @Override
    protected void getChildView(ViewHolder holder, int groupPositon, int childPositon, boolean isLastChild, ChildCashPrizeEntity data) {
        TextView textView = holder.getView(R.id.childtxt);
        textView.setText(data.getChildName());
    }

    @Override
    protected void getGroupView(ViewHolder holder, int groupPositon, boolean isExpanded, GroupCashPrizeEntity data) {
        SmoothCheckBox group_checkbox = holder.getView(R.id.group_checkbox);
        TextView group_name = holder.getView(R.id.group_name);
        TextView group_stage = holder.getView(R.id.group_stage);
        ImageView arrowImage = holder.getView(R.id.groupIcon);

        group_name.setText(data.getGroupName());
        group_stage.setText(data.getStage());
        arrowImage.setImageResource(isExpanded ? R.drawable.shou_jt : R.drawable.xl_jt);

        group_checkbox.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if(b==true){
                    map.put(groupPositon,true);
                }else {
                    map.remove(groupPositon);
                }
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
