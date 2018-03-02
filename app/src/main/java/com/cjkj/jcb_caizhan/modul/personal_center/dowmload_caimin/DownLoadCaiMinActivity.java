package com.cjkj.jcb_caizhan.modul.personal_center.dowmload_caimin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;

import butterknife.Bind;

/**
 * 个人中心-下载我的彩民端展示页
 */
public class DownLoadCaiMinActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.img_qr)
    ImageView img_qr;

    @Override
    public int getLayoutId() {
        return R.layout.activity_down_load_cai_min;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Glide.with(this)
                .load("http://www.hzwestlake.gov.cn/ewebeditor/uploadfile/20170504120633549.jpg")
                .into(img_qr);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("下载我的彩民端");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
