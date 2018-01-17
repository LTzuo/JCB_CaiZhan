package com.cjkj.jcb_caizhan.ui.mine.lottery;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.adapter.mine.lottery.RecycleLotteryDetailsAdapter;
import com.cjkj.jcb_caizhan.adapter.mine.lottery.RecycleLotteryNumberAdapter;
import com.cjkj.jcb_caizhan.base.BaseActivity;
import com.cjkj.jcb_caizhan.base.MobBaseEntity;
import com.cjkj.jcb_caizhan.entity.mine.lottery.MobLotteryEntity;
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
public class LotteryDetailActivity extends BaseActivity {

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
    protected int getContentViewLayoutID() {
        return R.layout.activity_lottery_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initIntent();

        initView();

        initToolBar(mToolbar, lotteryName, R.drawable.ic_back_white);

        loadData();
    }

    private void loadData() {
        Call<MobBaseEntity<MobLotteryEntity>> call = RetrofitHelper.getMineApi().querylotteryDetail(ApiConstants.URL_APP_Key,lotteryName);
        call.enqueue(new Callback<MobBaseEntity<MobLotteryEntity>>() {
            @Override
            public void onResponse(Call<MobBaseEntity<MobLotteryEntity>> call, Response<MobBaseEntity<MobLotteryEntity>> response) {
                if (response.isSuccess()) {
                    MobBaseEntity body = response.body();
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
            public void onFailure(Call<MobBaseEntity<MobLotteryEntity>> call, Throwable t) {
                // KLog.e("querylotteryList-----onFailure：" + t.toString());
                //数据错误
                // myCallBack.onFail(what, NET_FAIL);
            }
        });
    }

    public void initToolBar(Toolbar toolbar, String title, int icon) {
        toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setNavigationIcon(icon);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
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

    private void initIntent() {
        lotteryName = getIntent().getStringExtra(IntentKey_LotteryName);
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewLotteryDetails.setLayoutManager(linearLayoutManager2);
        mRecyclerViewLotteryDetails.setItemAnimator(new DefaultItemAnimator());
    }

    private void initMyToolBar() {

            initToolBar(mToolbar, lotteryName, R.drawable.ic_back_white);

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
