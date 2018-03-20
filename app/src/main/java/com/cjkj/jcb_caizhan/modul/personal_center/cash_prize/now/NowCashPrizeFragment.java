package com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.now;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 委托兑奖-当前委托
 * Created by 1 on 2018/2/28.
 */
public class NowCashPrizeFragment extends RxLazyFragment implements CashPrizeExAdapter.CheckBoxSelectListener{

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;
    @Bind(R.id.layout_checkbox_select)
    LinearLayout layout_checkbox_select;

    private CashPrizeExAdapter mCashPrizeExAdapter;

    public static NowCashPrizeFragment newIntance() {
        return new NowCashPrizeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_now_cash_prize;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mCashPrizeExAdapter = new CashPrizeExAdapter(getActivity(), R.layout.child_now_cash_prize, R.layout.group_now_cash_prize);
        mExpandableListView.setAdapter(mCashPrizeExAdapter);
        mCashPrizeExAdapter.setOnCheckBoxSelectListener(this);
        //添加测试数据
        addTestData();
    }

    private void addTestData() {
        for (int i = 0; i < 15; i++) {
            GroupCashPrizeEntity groupData = new GroupCashPrizeEntity();
            groupData.setGroupName("双色球");
            groupData.setStage("第"+"201812"+i+"期");
            mCashPrizeExAdapter.getGroupData().add(groupData);
        }
        for (int i = 0; i < mCashPrizeExAdapter.getGroupCount(); i++) {
            List<ChildCashPrizeEntity> temp = new ArrayList<>();
                ChildCashPrizeEntity childData = new ChildCashPrizeEntity();
                childData.setChildName("第" + i + "组子内容");
                temp.add(childData);
            mCashPrizeExAdapter.getChildrenData().add(temp);
        }
        mCashPrizeExAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckBoxSelect(boolean isSelect) {
        if(isSelect){
            layout_checkbox_select.setVisibility(View.VISIBLE);
        }else
            layout_checkbox_select.setVisibility(View.GONE);

    }
}
