package com.cjkj.jcb_caizhan.modul.Personal_Center.personcl_information.modify;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import butterknife.Bind;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

/**
 * 个人中心-修改界面
 */
public class ModifyActivity extends RxBaseActivity implements View.OnClickListener{

    int modify;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    TextView tv_adress;
    LinearLayout layout_choice_address;

    @Override
    public int getLayoutId() {
        modify = getIntent().getIntExtra("modify",0);
        if(modify == 1){
            return R.layout.activity_modify_phonenumber;
        }else if(modify == 2){
            return R.layout.activity_modify_shopnotices;
        }else if(modify == 3){
            return R.layout.activity_modify_bindidcard;
        }else if(modify == 4){
            return R.layout.activity_modify_shopcertification;
        }else if(modify == 5){
            return R.layout.activity_modify_chengepwd;
        }else if(modify == 6){
            return R.layout.activity_modify_txpwd;
        }else if(modify == 7){
            return R.layout.activity_modify_address;
        }else if(modify == 8){
            return R.layout.activity_modify_alipay;
        }
        return R.layout.activity_modify_wechat;
    }

    private void showAddressSelectDialog(){
        BottomDialog dialog = new BottomDialog(ModifyActivity.this);
        dialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
            @Override
            public void onAddressSelected(Province province, City city, County county, Street street) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(null==province?"":province.name);
                buffer.append(null==city?"":city.name);
                buffer.append(null==county?"":county.name);
                buffer.append(null==street?"":street.name);
                tv_adress.setText(buffer.toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if(modify == 7){
            tv_adress = (TextView) findViewById(R.id.tv_adress);
            layout_choice_address = (LinearLayout) findViewById(R.id.layout_choice_address);
            layout_choice_address.setOnClickListener(this);
        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        if(modify == 0){
            toolbar_title.setText("微信号");
        }else if(modify == 1){
            toolbar_title.setText("手机号");
        }else if(modify == 2){
            toolbar_title.setText("店铺公告");
        }else if(modify == 3){
            toolbar_title.setText("身份认证");
        }else if(modify == 4){
            toolbar_title.setText("店铺认证");
        }else if(modify == 5){
            toolbar_title.setText("修改密码");
        }else if(modify == 6){
            toolbar_title.setText("修改提现密码");
        }else if(modify == 7){
            toolbar_title.setText("编辑店铺地址");
        }else if(modify == 8){
            toolbar_title.setText("支付宝");
        }
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_choice_address:
                showAddressSelectDialog();
                break;
        }
    }
}
