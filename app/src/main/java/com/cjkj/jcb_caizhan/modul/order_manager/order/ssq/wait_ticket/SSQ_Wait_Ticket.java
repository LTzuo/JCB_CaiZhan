package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.wait_ticket;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

/**
 * 双色球—待打票
 * Created by 1 on 2018/2/23.
 */
public class SSQ_Wait_Ticket extends RxLazyFragment{

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ssq_wait_ticket;
    }

    public static SSQ_Wait_Ticket newIntance() {
        return new SSQ_Wait_Ticket();
    }

    @Override
    public void finishCreateView(Bundle state) {
        List<SSQ_ExAdapter.GroupItem> mGroupItems = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            SSQ_ExAdapter.GroupItem item = new SSQ_ExAdapter.GroupItem();
            item.title = "双色球" ;
            List<SSQ_ExAdapter.ChildItem> mChildItems = new ArrayList<>();
            for (int j = 0; j < 1; j++) {
                SSQ_ExAdapter.ChildItem item1 = new SSQ_ExAdapter.ChildItem();
                item1.message = "订单编号 " + j + "110110" + "" + i;
                mChildItems.add(item1);
            }
            item.mChildList = mChildItems;
            mGroupItems.add(item);
        }
        SSQ_ExAdapter yeAdapter = new SSQ_ExAdapter(getActivity(),mGroupItems);
        mExpandableListView.setAdapter(yeAdapter);
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
               // ToastUtil.ShortToast(groupPosition + "nd group's " + childPosition + "nd Item is clicked!");
                return false;
            }
        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mExpandableListView.getExpandableListAdapter().getGroupCount();
                for(int j = 0; j < count; j++){
                    if(j != groupPosition){
                        mExpandableListView.collapseGroup(j);
                    }
                }
            }
        });

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               // ToastUtil.ShortToast(groupPosition + "nd group is clicked");
                return false;
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
               // ToastUtil.ShortToast("the " + groupPosition + "nd group is collapsed");
            }
        });
    }
}
