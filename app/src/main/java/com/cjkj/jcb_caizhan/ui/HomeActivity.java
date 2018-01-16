package com.cjkj.jcb_caizhan.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.adapter.pager.HomeViewPagerAdapter;
import com.cjkj.jcb_caizhan.fragment.MineFragment;
import com.cjkj.jcb_caizhan.fragment.orderManager.OrdermMnagePageFragment;
import com.cjkj.jcb_caizhan.tabbarhelper.BottomNavigationViewHelper;
import com.cjkj.jcb_caizhan.tabbarhelper.NoScrollViewPager;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 彩站端首页
 */
public class HomeActivity extends AppCompatActivity {

    private NoScrollViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;
    private static Boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_news:
                                viewPager.setCurrentItem(0,false);
                                break;
                            case R.id.item_lib:
                                viewPager.setCurrentItem(1,false);
                                break;
                            case R.id.item_find:
                                viewPager.setCurrentItem(2,false);
                                break;
                            case R.id.item_more:
                                viewPager.setCurrentItem(3,false);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        viewPager .setOffscreenPageLimit(4);
        adapter.addFragment(OrdermMnagePageFragment.newInstance());
        adapter.addFragment(OrdermMnagePageFragment.newInstance());
        adapter.addFragment(OrdermMnagePageFragment.newInstance());
        adapter.addFragment(MineFragment.newInstance());
        viewPager.setAdapter(adapter);
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Snackbar.make(this.bottomNavigationView, "再按一次退出", Snackbar.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp(); // 调用双击退出函数
        }
        return false;
    }

}