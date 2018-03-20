package com.cjkj.jcb_caizhan.modul.ather.regist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Personal_Center.personcl_information.modify.ModifyActivity;
import com.cjkj.jcb_caizhan.modul.ather.HomeActivity;
import com.cjkj.jcb_caizhan.modul.ather.login.LoginActivity;
import com.cjkj.jcb_caizhan.modul.ather.shop_certification.ShopCertificationActivity;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.utils.ac_manager.ActivityManager;
import com.lucenlee.countdownlibrary.CountdownButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 忘记密码业
 */
public class ForgetPasswordActivity extends RxBaseActivity implements RegistContract.IRegistView {

    private RegistPresenter mRegistPresenter;
    @Bind(R.id.activity_main_btn_countdown)
    CountdownButton mBtnCountDown;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_vercode)
    EditText et_vercode;
    @Bind(R.id.et_new_pwd)
    EditText et_new_pwd;
    @Bind(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;

    @Override
    public void ShowFail(String failMessage) {
        ToastUtil.ShortToast(failMessage);
    }

    @Override
    public void VerificationCodeSussesfuly(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void UserRegistSussenfuly() {
//        ActivityManager.getInstance().finishActivity(H5MainActivity.class);
//        startActivity(new Intent(ForgetPasswordActivity.this, H5MainActivity.class));
    }

    @Override
    public void InitPwdSusseful(boolean b) {
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
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRegistPresenter = new RegistPresenter(this);
    }

    @Override
    public void initToolBar() {

    }

    @OnClick({R.id.activity_main_btn_countdown,R.id.btn_confirm})
    public void onClick(View v) {
        if(v.getId() == R.id.activity_main_btn_countdown){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString())){
                if(AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_SessionId,"").toString())){
                    mRegistPresenter.getVerificationCode(SPUtil.get(this, Constants.key_SessionId,"").toString(),et_username.getText().toString(),2);
                    mBtnCountDown.startDown();
                }
            }
        }
        else if(v.getId() == R.id.btn_confirm){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString()) &&
                    AppValidationMgr.checkVerCode(et_vercode.getText().toString())&&
                    AppValidationMgr.checkPwd(et_new_pwd.getText().toString(),et_confirm_pwd.getText().toString())){
                mRegistPresenter.initPWD(et_username.getText().toString(),et_vercode.getText().toString(),et_new_pwd.getText().toString());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistPresenter.unSubscribe();
    }
}
