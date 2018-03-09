package com.cjkj.jcb_caizhan.test.search;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.personal_center.order_query.OrderQuaryExAdapter;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.Material_Searchview.interfaces.onSearchListener;
import com.cjkj.jcb_caizhan.widget.Material_Searchview.interfaces.onSimpleSearchActionsListener;
import com.cjkj.jcb_caizhan.widget.Material_Searchview.widgets.MaterialSearchView;

import butterknife.Bind;

/**
 * 带搜索功能的ToolBar
 */
public class TestSearchActivity extends RxBaseActivity implements onSimpleSearchActionsListener, onSearchListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;

    OrderQuaryExAdapter mAdapter;

    private boolean mSearchViewAdded = false;
    private MaterialSearchView mSearchView;
    private MenuItem searchItem;
    private WindowManager mWindowManager;
    private boolean searchActive = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_search;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new OrderQuaryExAdapter(this);
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

    private void initSearchView(){
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mSearchView = new MaterialSearchView(this);
        mSearchView.setOnSearchListener(this);
        mSearchView.setSearchResultsListener(this);
        mSearchView.setHintText("输入订单编号查询");
    }

    @Override
    public void loadData() {
//        List<ExBaseGroupBean> mDatas = new ArrayList<>();
//        mDatas.add(new ExBaseGroupBean());
    }
    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("订单查询");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
        initSearchView();
        mToolbar.post(new Runnable() {
            @Override
            public void run() {
                if (!mSearchViewAdded && mWindowManager != null) {
                    mWindowManager.addView(mSearchView,
                            MaterialSearchView.getSearchViewLayoutParams(TestSearchActivity.this));
                    mSearchViewAdded = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchItem = menu.findItem(R.id.search);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mSearchView.display();
                openKeyboard();
                return true;
            }
        });
        if(searchActive)
            mSearchView.display();
        return true;

    }

    private void openKeyboard(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mSearchView.getSearchView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                mSearchView.getSearchView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearch(String query) {
        if(!TextUtils.isEmpty(query)){
            ToastUtil.ShortToast(query);
        }
    }

    @Override
    public void searchViewOpened() {
        ToastUtil.ShortToast("Search View Opened");
    }

    @Override
    public void searchViewClosed() {
        ToastUtil.ShortToast("Search View Closed");
    }

    @Override
    public void onItemClicked(String item) {
        // Toast.makeText(HomeActivity.this,item + " clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScroll() {

    }

    @Override
    public void error(String localizedMessage) {

    }

    @Override
    public void onCancelSearch() {
        searchActive = false;
        mSearchView.hide();
    }

}
