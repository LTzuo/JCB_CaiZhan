package com.cjkj.jcb_caizhan.modul.Personal_Center.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.base.BaseEntity;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import com.cjkj.jcb_caizhan.widget.RecyclerView.OnItemClickListener;
import java.util.ArrayList;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 彩票分类
 */
public class LotteryCategoryActivity extends ActivityCompat {
//
//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;
//    @Bind(R.id.recyclerView)
//    RecyclerView mRecyclerView;
//    private ArrayList<String> mDatas = new ArrayList<>();
//
//    private RecycleLotteryAdapter mRecycleLotteryAdapter;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_lottery_category;
//    }
//
//    @Override
//    public void initViews(Bundle savedInstanceState) {
//        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        loadData();
//    }
//
//    @Override
//    public void initToolBar() {
//        mToolbar.setTitle("彩票");// 标题的文字需在setSupportActionBar之前，不然会无效
//        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
//    }
//
//    @Override
//    public void loadData() {
//        Call<BaseEntity<ArrayList<String>>> call = RetrofitHelper.getMineApi().querylotteryList(ApiConstants.URL_APP_Key);
//        call.enqueue(new Callback<BaseEntity<ArrayList<String>>>() {
//            @Override
//            public void onResponse(Call<BaseEntity<ArrayList<String>>> call, Response<BaseEntity<ArrayList<String>>> response) {
//                if (response.isSuccess()) {
//                    BaseEntity<ArrayList<String>> body = response.body();
//                    if (body != null) {
//                        if (body.getMsg().equals("success")) {
////                            KLog.i("querylotteryList---success：" + body.toString());
////                            myCallBack.onSuccessList(what, body.getResult());
//                            mDatas = body.getResult();
//                            initAdapter();
//                        } else {
////                            myCallBack.onFail(what, body.getMsg());
//                        }
//                    } else {
//                     //   myCallBack.onFail(what, GET_DATA_FAIL);
//                    }
//                } else {
//                   // myCallBack.onFail(what, GET_DATA_FAIL);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseEntity<ArrayList<String>>> call, Throwable t) {
//               // KLog.e("querylotteryList-----onFailure：" + t.toString());
//                //数据错误
//               // myCallBack.onFail(what, NET_FAIL);
//            }
//        });
//    }
//
//    private void initAdapter() {
//        mRecycleLotteryAdapter = new RecycleLotteryAdapter(this, mDatas);
//        mRecyclerView.setAdapter(mRecycleLotteryAdapter);
//
//        mRecycleLotteryAdapter.setOnItemClickLitener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                //跳转详情页面
//                String name = mDatas.get(position);
//                Intent intent = new Intent(LotteryCategoryActivity.this, LotteryDetailActivity.class);
//                intent.putExtra(LotteryDetailActivity.IntentKey_LotteryName, name);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//
//


}
