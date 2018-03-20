package com.cjkj.jcb_caizhan.modul.Personal_Center.no_out_ticket;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.widget.ExListView.ExBaseGroupBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 个人中心-未出票
 */
public class NoOutTicketActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;

    NoOutTicketExAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_no_out_ticket;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        mAdapter = new NoOutTicketExAdapter(this);
        mExpandableListView.setAdapter(mAdapter);
//        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//               // ToastUtil.ShortToast(groupPosition + "nd group's " + childPosition + "nd Item is clicked!");
//                return false;
//            }
//        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mExpandableListView.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < count; j++) {
                    if (j != groupPosition) {
                        mExpandableListView.collapseGroup(j);
                    }
                }
            }
        });

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        loadData();
    }

    @Override
    public void loadData() {
        List<ExBaseGroupBean> mDatas = new ArrayList<>();
        mDatas.add(new ExBaseGroupBean());
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("未出票");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
