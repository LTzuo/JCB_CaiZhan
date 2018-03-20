package com.cjkj.jcb_caizhan.modul.ather.regist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Personal_Center.personcl_information.modify.ModifyActivity;
import com.cjkj.jcb_caizhan.modul.ather.login.LoginActivity;
import com.cjkj.jcb_caizhan.modul.ather.shop_certification.ShopCertificationActivity;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.utils.ac_manager.ActivityManager;
import com.lucenlee.countdownlibrary.CountdownButton;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册业
 */
public class RegistActivity extends RxBaseActivity implements RegistContract.IRegistView {

    private RegistPresenter mRegistPresenter;

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
        Intent intent = new Intent(this, ShopCertificationActivity.class);
        startActivity(intent);
        if(LoginActivity.mLoginActivity != null){
            ActivityManager.getInstance().finishActivity(LoginActivity.mLoginActivity);
        }
        ActivityManager.getInstance().finishActivity(this);
    }

    @Override
    public void InitPwdSusseful(boolean b) {

    }

    @Bind(R.id.checkbox)
    SmoothCheckBox checkbox;
    @Bind(R.id.activity_main_btn_countdown)
    CountdownButton mBtnCountDown;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_VerificationCode)
    EditText et_VerificationCode;
    @Bind(R.id.et_pwd)
    EditText et_pwd;
    @Bind(R.id.et_confirm_pwd)
    EditText et_confirm_pwd;
    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRegistPresenter = new RegistPresenter(this);
    }

    @Override
    public void initToolBar() {

    }

    @OnClick({R.id.btn_newregist,R.id.activity_main_btn_countdown,R.id.seeProtocol})
    public void BtnClick(View v){
        if(v.getId() == R.id.btn_newregist){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString()) &&
              AppValidationMgr.checkVerCode(et_VerificationCode.getText().toString())&&
                    AppValidationMgr.checkPwd(et_pwd.getText().toString(),et_confirm_pwd.getText().toString()) &&
                    AppValidationMgr.checkBoxcheck(checkbox.isChecked())){
                mRegistPresenter.userRegist(et_username.getText().toString(),et_VerificationCode.getText().toString(),et_pwd.getText().toString());
            }
        }else if(v.getId() == R.id.activity_main_btn_countdown){
            if(AppValidationMgr.checkPhoneNum(et_username.getText().toString())){
                if(AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_SessionId,"").toString())){
                    mRegistPresenter.getVerificationCode(SPUtil.get(this, Constants.key_SessionId,"").toString(),et_username.getText().toString(),0);
                    mBtnCountDown.startDown();
                }
            }
        }
//    else if(v.getId() == R.id.seeProtocol){
//            WebUtils.openInternal(RegistActivity.this, ApiConstants.XIEYIURL,getBaseContext().getString(R.string.USERXIEYITITLE));
////          WebUtils.openInternal(RegistActivity.this, "file:///android_asset/index.html");
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistPresenter.unSubscribe();
    }
}
