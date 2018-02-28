package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize.now;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.widget.exlistview.CommonExpandableListAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 委托兑奖-当前委托
 * Created by 1 on 2018/2/28.
 */
public class NowCashPrizeFragment extends RxLazyFragment {

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;

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
        //添加测试数据
        addTestData();
    }

    private void addTestData() {
        for (int i = 0; i < 5; i++) {
            GroupCashPrizeEntity groupData = new GroupCashPrizeEntity();
            groupData.setGroupName( "分组-" + i);
            mCashPrizeExAdapter.getGroupData().add(groupData);
        }
        for (int i = 0; i < mCashPrizeExAdapter.getGroupCount(); i++) {
            List<ChildCashPrizeEntity> temp = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                ChildCashPrizeEntity childData = new ChildCashPrizeEntity();
                childData.setChildName("第" + i + "组内容-" + j);
                temp.add(childData);
            }
            mCashPrizeExAdapter.getChildrenData().add(temp);
        }

        mCashPrizeExAdapter.notifyDataSetChanged();
    }


}
