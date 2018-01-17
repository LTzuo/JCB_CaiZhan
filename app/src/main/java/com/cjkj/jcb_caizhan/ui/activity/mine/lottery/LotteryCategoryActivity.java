package com.cjkj.jcb_caizhan.ui.activity.mine.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.adapter.mine.lottery.RecycleLotteryAdapter;
import com.cjkj.jcb_caizhan.base.BaseActivity;
import com.cjkj.jcb_caizhan.base.MobBaseEntity;
import com.cjkj.jcb_caizhan.listeners.OnItemClickListener;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import java.util.ArrayList;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 彩票分类
 */
public class LotteryCategoryActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ArrayList<String> mDatas = new ArrayList<>();

    private RecycleLotteryAdapter mRecycleLotteryAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_lottery_category;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initMyToolBar();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadData();
    }

    private void loadData() {
        Call<MobBaseEntity<ArrayList<String>>> call = RetrofitHelper.getMineApi().querylotteryList(ApiConstants.URL_APP_Key);
        call.enqueue(new Callback<MobBaseEntity<ArrayList<String>>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<ArrayList<String>>> call, Response<MobBaseEntity<ArrayList<String>>> response) {
                if (response.isSuccess()) {
                    MobBaseEntity<ArrayList<String>> body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
//                            KLog.i("querylotteryList---success：" + body.toString());
//                            myCallBack.onSuccessList(what, body.getResult());
                            mDatas = body.getResult();
                            initAdapter();
                        } else {
//                            myCallBack.onFail(what, body.getMsg());
                        }
                    } else {
                     //   myCallBack.onFail(what, GET_DATA_FAIL);
                    }
                } else {
                   // myCallBack.onFail(what, GET_DATA_FAIL);
                }
            }

            @Override
            public void onFailure(Call<MobBaseEntity<ArrayList<String>>> call, Throwable t) {
               // KLog.e("querylotteryList-----onFailure：" + t.toString());
                //数据错误
               // myCallBack.onFail(what, NET_FAIL);
            }
        });
    }

    private void initAdapter() {
        mRecycleLotteryAdapter = new RecycleLotteryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mRecycleLotteryAdapter);

        mRecycleLotteryAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转详情页面
                String name = mDatas.get(position);
                Intent intent = new Intent(LotteryCategoryActivity.this, LotteryDetailActivity.class);
                intent.putExtra(LotteryDetailActivity.IntentKey_LotteryName, name);
                startActivity(intent);
            }
        });
    }



    private void initMyToolBar() {
         initToolBar(mToolbar, "彩票", R.drawable.ic_back_white);
    }

    public void initToolBar(Toolbar toolbar, String title, int icon) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setNavigationIcon(icon);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
