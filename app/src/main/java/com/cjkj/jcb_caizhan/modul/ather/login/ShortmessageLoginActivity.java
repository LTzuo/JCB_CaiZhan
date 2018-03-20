package com.cjkj.jcb_caizhan.modul.ather.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.ather.HomeActivity;
import com.cjkj.jcb_caizhan.modul.ather.shop_certification.ShopCertificationActivity;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.utils.ac_manager.ActivityManager;
import com.lucenlee.countdownlibrary.CountdownButton;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 短信验证登录业
 */
public class ShortmessageLoginActivity extends RxBaseActivity implements LoginContract.ILogView{

    LoginPressenter mLoginRresenter;

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_vercode)
    EditText et_vercode;
    @Bind(R.id.activity_main_btn_countdown)
    CountdownButton mBtnCountDown;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shortmessage_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mLoginRresenter = new LoginPressenter(this);
    }

    @Override
    public void initToolBar() {

    }

    @OnClick({R.id.activity_main_btn_countdown,R.id.btn_login})
    public void btnClick(View v) {
        if(v.getId() == R.id.btn_login){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString()) &&
                    AppValidationMgr.checkVerCode(et_vercode.getText().toString())) {
                mLoginRresenter.userShortMessageLogin("1",et_username.getText().toString(),et_vercode.getText().toString());
            }
        }
       else if(v.getId() == R.id.activity_main_btn_countdown){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString())){
                if(AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_SessionId,"").toString())){
                    mLoginRresenter.getVerificationCode(SPUtil.get(this, Constants.key_SessionId,"").toString(),et_username.getText().toString(),1);
                    mBtnCountDown.startDown();
                }
            }
        }
    }

    @Override
    public void ShowFail(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void LoginSussesful(boolean b) {
        if(b){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ShopCertificationActivity.class);
            startActivity(intent);
        }
        if(LoginActivity.mLoginActivity != null){
            ActivityManager.getInstance().finishActivity(LoginActivity.mLoginActivity);
        }
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    public void VerificationCodeSussesfuly(String msg) {
        //短信验证登录，走此方法
        ToastUtil.ShortToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginRresenter.unSubscribe();
    }
}
