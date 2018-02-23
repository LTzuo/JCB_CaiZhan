package com.cjkj.jcb_caizhan.modul.order_manager.order.ssq;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.HomeViewPagerAdapter;
import com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.error_ticket.SSQ_Error_Ticket;
import com.cjkj.jcb_caizhan.modul.order_manager.order.ssq.wait_ticket.SSQ_Wait_Ticket;
import com.cjkj.jcb_caizhan.widget.ScrollViewPager;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 订单管理-订单（双色球）
 */
public class SSQActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.id_page_vp)
    ScrollViewPager mViewPager;
    @Bind(R.id.tv_wait)
    TextView tv_wait;
    @Bind(R.id.tv_error)
    TextView tv_error;

    @OnClick({R.id.tv_wait,R.id.tv_error})
    public void OnBtnClick(View v){
        if(v.getId() == R.id.tv_wait){
            mViewPager.setCurrentItem(0,false);
        }else if(v.getId() == R.id.tv_error){
            mViewPager.setCurrentItem(1,false);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ssq;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initViewPager();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        tv_wait.setTextColor(getResources().getColor(R.color.white));
                        tv_error.setTextColor(getResources().getColor(R.color.base_color_2));
                        tv_wait.setBackground(getResources().getDrawable(R.drawable.shape_ssq_lift_bg));
                        tv_error.setBackground(null);
                        break;
                    case 1:
                        tv_wait.setTextColor(getResources().getColor(R.color.base_color_2));
                        tv_error.setTextColor(getResources().getColor(R.color.white));
                        tv_wait.setBackground(null);
                        tv_error.setBackground(getResources().getDrawable(R.drawable.shape_ssq_right_bg));
                        break;
                    default:
                        break;
                }

            }

            /***
             * position:当前页面，及点击滑动页面 offset：当前页面偏移的百分比 offsetPixels：当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void initViewPager() {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(2);
        adapter.addFragment(SSQ_Wait_Ticket.newIntance());
        adapter.addFragment(SSQ_Error_Ticket.newIntance());
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("双色球");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
