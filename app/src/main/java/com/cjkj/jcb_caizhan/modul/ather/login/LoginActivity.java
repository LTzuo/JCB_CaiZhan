package com.cjkj.jcb_caizhan.modul.ather.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.ather.HomeActivity;
import com.cjkj.jcb_caizhan.modul.ather.regist.ForgetPasswordActivity;
import com.cjkj.jcb_caizhan.modul.ather.regist.RegistActivity;
import com.cjkj.jcb_caizhan.modul.ather.shop_certification.ShopCertificationActivity;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.utils.ac_manager.ActivityManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends RxBaseActivity implements LoginContract.ILogView {

    LoginPressenter mLoginRresenter;

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.delete_username)
    ImageButton delete_username;

    public static LoginActivity mLoginActivity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mLoginActivity = this;
        mLoginRresenter = new LoginPressenter(this);
        et_username.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && et_username.getText().length() > 0) {
                delete_username.setVisibility(View.VISIBLE);
            } else {
                delete_username.setVisibility(View.GONE);
            }

        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 如果用户名清空了 清空密码 清空记住密码选项
                et_pwd.setText("");
                if (s.length() > 0) {
                    // 如果用户名有内容时候 显示删除按钮
                    delete_username.setVisibility(View.VISIBLE);
                } else {
                    // 如果用户名有内容时候 显示删除按钮
                    delete_username.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void initToolBar() {

    }

    @OnClick({R.id.delete_username,R.id.btn_login,R.id.forgetpwd,R.id.login_tv_phone,R.id.login_tv_regin})
    public void BtnClick(View v) {
        if(v.getId() == R.id.delete_username){
            et_username.setText("");
            et_pwd.setText("");
            delete_username.setVisibility(View.GONE);
            et_username.setFocusable(true);
            et_username.setFocusableInTouchMode(true);
            et_username.requestFocus();
        }else if(v.getId() == R.id.btn_login){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString()) &&
                    AppValidationMgr.checkPwd(et_pwd.getText().toString(),et_pwd.getText().toString())) {
                mLoginRresenter.userLogin("0",et_username.getText().toString(),et_pwd.getText().toString());
            }
        }else if(v.getId() == R.id.forgetpwd){
            startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
        }else if(v.getId() == R.id.login_tv_phone){
           startActivity(new Intent(LoginActivity.this,ShortmessageLoginActivity.class));
        }else if(v.getId() == R.id.login_tv_regin){
            startActivity(new Intent(LoginActivity.this,RegistActivity.class));
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
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    public void VerificationCodeSussesfuly(String msg) {
        //正常登录，不走此方法
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginRresenter.unSubscribe();
    }
}
