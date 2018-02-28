package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.now;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.widget.exlistview.CommonExpandableListAdapter;

/**
 * 委托兑奖-当前委托适配器
 * Created by 1 on 2018/2/28.
 */
public class CashPrizeExAdapter extends CommonExpandableListAdapter<ChildCashPrizeEntity,GroupCashPrizeEntity>{

    public CashPrizeExAdapter(Context mContext, int childResource, int groupResource){
        super(mContext,childResource,groupResource);
    }

    @Override
    protected void getChildView(ViewHolder holder, int groupPositon, int childPositon, boolean isLastChild, ChildCashPrizeEntity data) {
        TextView textView = holder.getView(R.id.childtxt);//孩子名字
        textView.setText(data.getChildName());
    }

    @Override
    protected void getGroupView(ViewHolder holder, int groupPositon, boolean isExpanded, GroupCashPrizeEntity data) {
        TextView textView = holder.getView(R.id.grouptxt);//分组名字
        ImageView arrowImage = holder.getView(R.id.groupIcon);//分组箭头
        textView.setText(data.getGroupName());
        //根据分组是否展开设置自定义箭头方向
        arrowImage.setImageResource(isExpanded ? R.drawable.shou_jt : R.drawable.xl_jt);
    }


}
