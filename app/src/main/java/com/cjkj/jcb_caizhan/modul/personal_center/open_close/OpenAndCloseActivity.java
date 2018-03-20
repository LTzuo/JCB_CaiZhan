package com.cjkj.jcb_caizhan.modul.Personal_Center.open_close;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 开闭店设置
 * Created by 1 on 2018/3/2.
 */
public class OpenAndCloseActivity extends RxBaseActivity  {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_custom)
    TextView toolbar_custom;

    @Bind(R.id.tv_opentime)
    TextView tv_opentime;
    @Bind(R.id.tv_closetime)
    TextView tv_closetime;

    @OnClick({R.id.open_time,R.id.close_time,R.id.toolbar_custom})
    public void onBtnClick(View v){
        if(v.getId() == R.id.open_time  || v.getId() == R.id.close_time){
           showTimePicker(v);
        }else if(v.getId() == R.id.toolbar_custom){
            ToastUtil.ShortToast("保存");
        }
    }

    private void showTimePicker(View v){
        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                        StringBuffer buffer = new StringBuffer();
                        if(String.valueOf(hourOfDay).length() == 1){
                            buffer.append("0"+hourOfDay);
                        }else
                            buffer.append(hourOfDay);
                        if(String.valueOf(minute).length() == 1){
                            buffer.append(":"+"0"+minute);
                        }else
                            buffer.append(":"+minute);
                        dialog.dismiss();
                        if(v.getId() == R.id.open_time){
                            tv_opentime.setText(buffer.toString());
                        }else if(v.getId() == R.id.close_time){
                            tv_closetime.setText(buffer.toString());
                        }
                    }
                })
                .setStartTime(8, 00)
                .setDoneText("确认")
                .setCancelText("取消");
        rtpd.show(getSupportFragmentManager(), "时间选择");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_close;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("开闭店设置");
        toolbar_custom.setText("保存");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
        toolbar_custom.setVisibility(View.VISIBLE);
    }
}
