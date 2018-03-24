package com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.BaseFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.OrderQuaryExAdapter;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.OrderQueryContract;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.OrderQueryPressenter;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity.Lottery;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity.LotterySaxParser;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.entity.OrderEntity;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.menu_adapter.GirdDropDownAdapter;
import com.cjkj.jcb_caizhan.modul.Personal_Center.order_query.menu_adapter.ListDropDownAdapter;
import com.cjkj.jcb_caizhan.utils.DateHelper;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.yyydjk.library.DropDownMenu;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 数字彩订单查询
 * Created by 1 on 2018/3/24.
 */
public class OrderQueryFragment extends BaseFragment implements OrderQueryContract.IOrderQueryView {

    ExpandableListView mExpandableListView;
    OrderQuaryExAdapter mAdapter;

    @Bind(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private String headers[] = {"彩种", "状态","时间"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter lotteryAdapter;
    private ListDropDownAdapter stateAdapter;

    List<Lottery> lotterys = new ArrayList<>();
    List<Lottery> states = new ArrayList<>();

    TextView tv_start_time,tv_end_time;

    OrderQueryPressenter mPressenter;
    int index = 1;

    int orderType = 0;//订单类型，0数字彩，1传统足球，2竞彩
    String lotteryTypeid = "";//彩种，""未全部彩种 4双色球、5是3d、6七乐彩、7大乐透、8排列三、9排列五、10七星彩
    String orderState = "99";//订单状态，默认99全部

    public static OrderQueryFragment newIntance(){
        return new OrderQueryFragment();
    }

    private void initEx() {
        mAdapter = new OrderQuaryExAdapter(getContext());
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
    protected int bindLayout() {
         return R.layout.frragment_orderquery;
    }

    @Override
    public void loadData() {
        mPressenter.getOrders(SPUtil.get(getContext(), Constants.key_uSessionId,"").toString(),index,orderType,lotteryTypeid,
                tv_start_time.getText().toString(),tv_end_time.getText().toString(),orderState);
    }

    @Override
    public void onFirstUserVisible() {
        mPressenter = new OrderQueryPressenter(this);
        try {
            InputStream is = getContext().getAssets().open("lotterys_number.xml");
            InputStream is1 = getContext().getAssets().open("states_number.xml");
            LotterySaxParser parser = new LotterySaxParser();
            LotterySaxParser parser1 = new LotterySaxParser();
            lotterys = parser.parse(is);
            states = parser1.parse(is1);
        }catch (Exception e){
            e.printStackTrace();
            ToastUtil.ShortToast("异常");
        }
        initViews();
        //initEx();
        loadData();
    }

    @Override
    public void onUserVisible() {

    }

    private void initViews(){
        //彩种菜单
        final ListView lotteryView = new ListView(getContext());
        lotteryAdapter = new GirdDropDownAdapter(getContext(), lotterys);
        lotteryView.setDividerHeight(0);
        lotteryView.setAdapter(lotteryAdapter);

        //状态菜单
        final ListView stateView = new ListView(getContext());
        stateView.setDividerHeight(0);
        stateAdapter = new ListDropDownAdapter(getContext(), states);
        stateView.setAdapter(stateAdapter);

        //自定义时间选择菜单
        final View timesView = getActivity().getLayoutInflater().inflate(R.layout.drowmenu_layout, null);
        TextView ok = ButterKnife.findById(timesView, R.id.ok);
         tv_start_time = ButterKnife.findById(timesView, R.id.start_time);
         tv_end_time = ButterKnife.findById(timesView, R.id.end_time);

        tv_start_time.setText(DateHelper.getInstance().getDataString_2(new Date()));
        tv_end_time.setText(DateHelper.getInstance().getDataString_2(new Date()));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                loadData();
                mDropDownMenu.closeMenu();
            }
        });

        tv_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(0);
            }
        });

        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog(1);
            }
        });

        //init popupViews
        popupViews.add(lotteryView);
        popupViews.add(stateView);
        popupViews.add(timesView);

        //add item click event
        lotteryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lotteryAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : lotterys.get(position).getName());
                mDropDownMenu.closeMenu();
                lotteryTypeid = lotterys.get(position).getValue();
                loadData();
            }
        });

        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : states.get(position).getName());
                mDropDownMenu.closeMenu();
                orderState = states.get(position).getValue();
                loadData();
            }
        });

        //内容
        LinearLayout contentView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.content_orderquery,null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExpandableListView = ButterKnife.findById(contentView, R.id.mExpandableListView);
        initEx();
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    //日期选择器
    private void showDateDialog(int sys){
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(year+"-");
                        if(String.valueOf(monthOfYear + 1).length() == 1){
                            buffer.append("0"+(monthOfYear + 1) +"-");
                        }else{
                            buffer.append((monthOfYear + 1)+"-");
                        }
                        if(String.valueOf(dayOfMonth).length() == 1){
                            buffer.append("0"+dayOfMonth);
                        }else{
                            buffer.append(dayOfMonth);
                        }
                        if(sys == 0){
                            tv_start_time.setText(buffer.toString());
                        }else{
                            tv_end_time.setText(buffer.toString());
                        }
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setDoneText("确认")
                .setCancelText("取消");
        // .setThemeDark(true);
        cdp.show(getFragmentManager(), "日期选择");
    }

    @Override
    public void SuccessFul(List<OrderEntity> mDatas) {
       mAdapter.setInfo(mDatas);
    }

    @Override
    public void Fild(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void onUserInvisible() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        }
    }

}
