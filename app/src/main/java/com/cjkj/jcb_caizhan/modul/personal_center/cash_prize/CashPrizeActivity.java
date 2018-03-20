package com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.app.LTZFragmentManager;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.Personal_Center.cash_prize.caizhong.CaiZhong_All_Fragment;
import com.cjkj.jcb_caizhan.widget.VerticalDrawerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人中心-委托兑奖
 */
public class CashPrizeActivity extends RxBaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.img_custom)
    ImageView img_custom;

    @Bind(R.id.mVerticalDrawerLayout)
    VerticalDrawerLayout mDrawerLayout;

    @Bind(R.id.mCustomGridView)
    GridView mCustomGridView;

    CustomGridAdapter mCustomGridAdapter;

    boolean isCustomOpen = false;

    int index = 0;

    private LTZFragmentManager mFragmentManager;

    @OnClick({R.id.imgback, R.id.Layout_custom})
    public void onBtnClick(View v) {
        if (v.getId() == R.id.imgback) {
            finish();
        } else if (v.getId() == R.id.Layout_custom) {
            if (!isCustomOpen) {
                openCustom();
            } else {
                closeCustom();
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_prize;
    }

    private void openCustom() {
        isCustomOpen = true;
        mDrawerLayout.openDrawerView();
    }

    private void closeCustom() {
//        if(mFragmentManager != null){
//            mFragmentManager.swichFragment(index);
//        }
        mCustomGridAdapter.SelsectItem(index);
        toolbar_title.setText(mCustomGridAdapter.getSelectItem());
        isCustomOpen = false;
        mDrawerLayout.closeDrawer();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CaiZhong_All_Fragment.newIntance());
//        fragmentList.add(CaiZhong_3D_Fragment.newIntance());
//        fragmentList.add(CaiZhong_7lc_Fragment.newIntance());
//        fragmentList.add(CaiZhong_dlt_Fragment.newIntance());
//        fragmentList.add(CaiZhong_pl3_Fragment.newIntance());
//        fragmentList.add(CaiZhong_pl5_Fragment.newIntance());
//        fragmentList.add(CaiZhong_7xc_Fragment.newIntance());
//        fragmentList.add(CaiZhong_jczq_Fragment.newIntance());
//        fragmentList.add(CaiZhong_jclq_Fragment.newIntance());
//        fragmentList.add(CaiZhong_sfgg_Fragment.newIntance());
//        fragmentList.add(CaiZhong_zqsf_Fragment.newIntance());
//        fragmentList.add(CaiZhong_rx9c_Fragment.newIntance());
//        fragmentList.add(CaiZhong_zqdc_Fragment.newIntance());
        mFragmentManager = new LTZFragmentManager(this,fragmentList);
        mFragmentManager.swichFragment(0);
        mCustomGridAdapter = new CustomGridAdapter(this);
        mCustomGridView.setAdapter(mCustomGridAdapter);
        mCustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                closeCustom();
            }
        });
    }

    @Override
    public void initToolBar() {
        mCustomGridAdapter.SelsectItem(0);
        toolbar_title.setText(mCustomGridAdapter.getSelectItem());
        img_custom.setVisibility(View.VISIBLE);

        mDrawerLayout.setDrawerListener(new VerticalDrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                img_custom.setRotation(slideOffset*180);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentManager.removeFragments();
    }
}
