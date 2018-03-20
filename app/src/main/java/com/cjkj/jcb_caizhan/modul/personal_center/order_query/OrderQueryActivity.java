package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.menu.LotteryGridAdapter;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.menu.StateGridAdapter;
import com.cjkj.jcb_caizhan.widget.NoScollGridView;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人中心-订单查询
 */
public class OrderQueryActivity extends RxBaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.menu_custom)
    ImageView menu_custom;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerlayout;
    @Bind(R.id.menu)
    FrameLayout menu;

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;
    OrderQuaryExAdapter mAdapter;

    @Bind(R.id.mLotteryGridView)
    NoScollGridView mLotteryGridView;
    @Bind(R.id.mStateGridView)
    NoScollGridView mStateGridView;

    @Bind(R.id.menu_start_time)
    TextView menu_start_time;
    @Bind(R.id.menu_end_time)
    TextView menu_end_time;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_query;
    }

    @OnClick({R.id.menu_custom,R.id.tv_cancle,R.id.tv_ok,R.id.imgback,R.id.menu_start_time,R.id.menu_end_time})
    public void BtnClick(View v) {
        if (v.getId() == R.id.menu_custom) {
            mDrawerlayout.openDrawer(menu);
        } else if(v.getId() == R.id.tv_cancle){
            mDrawerlayout.closeDrawer(menu);
        }else if(v.getId() == R.id.tv_ok){
            mDrawerlayout.closeDrawer(menu);
        }else if (v.getId() == R.id.imgback) {
            finish();
        }else if(v.getId() == R.id.menu_start_time){
            showDateDialog(0);
        }else if(v.getId() == R.id.menu_end_time){
            showDateDialog(1);
        }
    }

    //日期选择器
    private void showDateDialog(int sys){
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                       if(sys == 0){
                           menu_start_time.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                       }else{
                           menu_end_time.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                       }
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setDoneText("确认")
                .setCancelText("取消");
        // .setThemeDark(true);
        cdp.show(getSupportFragmentManager(), "日期选择");
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initDrawerMenu();
        initEx();
        loadData();
    }

    //初始化菜单
    private void initDrawerMenu() {
        LotteryGridAdapter mLotteryGridAdapter = new LotteryGridAdapter(this);
        mLotteryGridView.setAdapter(mLotteryGridAdapter);
        mLotteryGridAdapter.SelsectItem(0);
        mLotteryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mLotteryGridAdapter.SelsectItem(i);
            }
        });

        StateGridAdapter mStateGridAdapter = new StateGridAdapter(this);
        mStateGridView.setAdapter(mStateGridAdapter);
        mStateGridAdapter.SelsectItem(0);
        mStateGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mStateGridAdapter.SelsectItem(i);
            }
        });
    }

    private void initEx() {
        mAdapter = new OrderQuaryExAdapter(this);
        mExpandableListView.setAdapter(mAdapter);
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
    }

    @Override
    public void loadData() {
//        List<ExBaseGroupBean> mDatas = new ArrayList<>();
//        mDatas.add(new ExBaseGroupBean());
    }

    @Override
    public void initToolBar() {
        toolbar_title.setText("订单查询");
        menu_custom.setImageDrawable(getResources().getDrawable(R.drawable.shaixuan));
        menu_custom.setVisibility(View.VISIBLE);
    }

}
