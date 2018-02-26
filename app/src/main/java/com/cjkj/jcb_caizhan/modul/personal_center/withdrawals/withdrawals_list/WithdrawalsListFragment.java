package com.cjkj.jcb_caizhan.modul.personal_center.withdrawals.withdrawals_list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

/**
 * 个人中心-提现记录
 * Created by 1 on 2018/2/24.
 */
public class WithdrawalsListFragment extends RxLazyFragment{

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    WithdrawalsListAdapter mAdapter;

    public static WithdrawalsListFragment newIntance() {
        return new WithdrawalsListFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_withdrawals_list;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getContext(), R.color.bg_f3f2f7)));
        mAdapter = new WithdrawalsListAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("双色球即将开奖,奖池金额高达3亿元");
        }
        mAdapter.setInfo(dataList);

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                IntentUtils.Goto(getActivity(),WithdrawalsDetailsActivity.class);
            }
        });
    }
}
