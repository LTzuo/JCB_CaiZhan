package com.cjkj.jcb_caizhan.modul.personal_center.lottery;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.base.BaseEntity;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.network.RetrofitHelper;
import java.util.List;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 彩票详情
 */
public class LotteryDetailActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_awardDateTime)
    TextView mTvAwardDateTime;
    @Bind(R.id.tv_sales)
    TextView mTvSales;
    @Bind(R.id.tv_pool)
    TextView mTvPool;
    @Bind(R.id.tv_period)
    TextView mTvPeriod;
    @Bind(R.id.recyclerViewLotteryDetails)
    RecyclerView mRecyclerViewLotteryDetails;


    private MobLotteryEntity mMobLotteryEntity = new MobLotteryEntity();
    public static final String IntentKey_LotteryName = "IntentKey_LotteryName";

    private String lotteryName = "";
    private RecycleLotteryNumberAdapter mRecycleLotteryNumberAdapter;
    private RecycleLotteryDetailsAdapter mRecycleLotteryDetailsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lottery_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        lotteryName = getIntent().getStringExtra(IntentKey_LotteryName);
        initView();
        loadData();
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(lotteryName);// 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    public  void loadData() {
        Call<BaseEntity<MobLotteryEntity>> call = RetrofitHelper.getMineApi().querylotteryDetail(ApiConstants.URL_APP_Key,lotteryName);
        call.enqueue(new Callback<BaseEntity<MobLotteryEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<MobLotteryEntity>> call, Response<BaseEntity<MobLotteryEntity>> response) {
                if (response.isSuccess()) {
                    BaseEntity body = response.body();
                    if (body != null) {
                        if (body.getMsg().equals("success")) {
//                            KLog.i("querylotteryList---success：" + body.toString());
//                            myCallBack.onSuccessList(what, body.getResult());
                            mMobLotteryEntity = (MobLotteryEntity) body.getResult();

                            //处理数据
                            mTvPeriod.setText("第" + mMobLotteryEntity.getPeriod() + "期");
                            mTvAwardDateTime.setText("开奖时间: " + mMobLotteryEntity.getAwardDateTime());
                            mTvPool.setText(String.valueOf(mMobLotteryEntity.getPool()));
                            mTvSales.setText(String.valueOf(mMobLotteryEntity.getSales()));

                            //开奖号码
                            initNumberAdpater();

                            //中奖信息
                            initDetailsAdpater();
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
            public void onFailure(Call<BaseEntity<MobLotteryEntity>> call, Throwable t) {
                // KLog.e("querylotteryList-----onFailure：" + t.toString());
                //数据错误
                // myCallBack.onFail(what, NET_FAIL);
            }
        });
    }




    private void initNumberAdpater() {
        List<String> lotteryNumber = mMobLotteryEntity.getLotteryNumber();
        if (lotteryNumber != null && lotteryNumber.size() > 0) {
            mRecycleLotteryNumberAdapter = new RecycleLotteryNumberAdapter(this, lotteryNumber);
            mRecyclerView.setAdapter(mRecycleLotteryNumberAdapter);
        }
    }

    private void initDetailsAdpater() {
        List<MobLotteryEntity.LotteryDetailsBean> lotteryDetails = mMobLotteryEntity.getLotteryDetails();
        if (lotteryDetails != null && lotteryDetails.size() > 0) {
            mRecycleLotteryDetailsAdapter = new RecycleLotteryDetailsAdapter(this, lotteryDetails);
            mRecyclerViewLotteryDetails.setAdapter(mRecycleLotteryDetailsAdapter);
        }
    }



    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewLotteryDetails.setLayoutManager(linearLayoutManager2);
        mRecyclerViewLotteryDetails.setItemAnimator(new DefaultItemAnimator());
    }




}
