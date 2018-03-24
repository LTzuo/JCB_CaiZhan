package com.cjkj.jcb_caizhan.modul.ather.shop_certification;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.utils.AppValidationMgr;
import com.cjkj.jcb_caizhan.utils.LubanUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.SnackbarUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.utils.ac_manager.ActivityManager;
import com.google.gson.JsonObject;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import cn.yhq.dialog.core.DialogBuilder;

/**
 * 彩站认证
 */
public class ShopCertificationActivity extends RxBaseActivity implements ShopCertificationContract.IShopCertificationView {

    private static final int CARDPIC_RESUSTCODE = 10;//手持身份证照
    private static final int SITEPIC_RESUSTCODE = 11;//店铺照
    private static final int SITECPIC_RESUSTCODE = 12;//福彩代销证照
    private static final int SITELPIC_RESUSTCODE = 13;//体彩代销证照
    private static final int SITESPIC_RESUSTCODE = 14;//竞彩代销证照

    //该map只存放路径
    private Map<Integer, Image> imgsMap = new HashMap<>();

    @Bind(R.id.hint)
    TextView hint;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.edt_siteName)
    EditText edt_siteName;//店铺名称
    @Bind(R.id.edt_linkMan)
    EditText edt_linkMan;//店主姓名
    @Bind(R.id.edt_invater)
    EditText edt_invater;//邀请人手机号
    @Bind(R.id.edt_siteAddress)
    EditText edt_siteAddress;//详细地址
    @Bind(R.id.tv_adress)
    TextView tv_adress;//显示所在地址
    @Bind(R.id.img_card)
    ImageView img_card;
    @Bind(R.id.img_sitePic)
    ImageView img_sitePic;
    @Bind(R.id.img_siteCPic)
    ImageView img_siteCPic;
    @Bind(R.id.img_siteLPic)
    ImageView img_siteLPic;
    @Bind(R.id.img_siteSPic)
    ImageView img_siteSPic;
    @Bind(R.id.tv_submit)
    TextView tv_submit;
    @Bind(R.id.layout_choice_address)
    LinearLayout layout_choice_address;
    @Bind(R.id.layout_card)
    LinearLayout layout_card;
    @Bind(R.id.layout_siteCPic)
    LinearLayout layout_siteCPic;
    @Bind(R.id.layout_siteLPic)
    LinearLayout layout_siteLPic;
    @Bind(R.id.layout_siteSPic)
    LinearLayout layout_siteSPic;
    @Bind(R.id.layout_sitePic)
    LinearLayout layout_sitePic;
    @Bind(R.id.Layout_invater)
    LinearLayout Layout_invater;

    String spro, scity,authType = "0";

    private static Boolean isExit = false;

    ShopCertificationPressenter mPressenter;

    @OnClick({R.id.layout_choice_address, R.id.layout_card, R.id.layout_sitePic, R.id.layout_siteCPic,
            R.id.layout_siteLPic, R.id.layout_siteSPic, R.id.tv_submit})
    public void OnBtnClick(View v) {
        if (v.getId() == R.id.layout_choice_address) {
            showAddressSelectDialog();
        } else if (v.getId() == R.id.layout_card) {
            openCamera(CARDPIC_RESUSTCODE);
        } else if (v.getId() == R.id.layout_sitePic) {
            openCamera(SITEPIC_RESUSTCODE);
        } else if (v.getId() == R.id.layout_siteCPic) {
            openCamera(SITECPIC_RESUSTCODE);
        } else if (v.getId() == R.id.layout_siteLPic) {
            openCamera(SITELPIC_RESUSTCODE);
        } else if (v.getId() == R.id.layout_siteSPic) {
            openCamera(SITESPIC_RESUSTCODE);
        } else if (v.getId() == R.id.tv_submit) {
            String siteName = edt_siteName.getText().toString();
            String linkName = edt_linkMan.getText().toString();
            String invater = edt_invater.getText().toString();
            String address = tv_adress.getText().toString();
            String siteAddress = edt_siteAddress.getText().toString();
            if (siteName.isEmpty()) {
                ToastUtil.ShortToast("请输入店铺名称");
                return;
            }
            if (linkName.isEmpty()) {
                ToastUtil.ShortToast("请输入店主姓名");
                return;
            }
            if (!invater.isEmpty() && !AppValidationMgr.isPhone(invater)) {
                ToastUtil.ShortToast("邀请人手机号格式不正确");
                return;
            }
            if (address.isEmpty()) {
                ToastUtil.ShortToast("请选择所在地址");
                return;
            }
            if (siteAddress.isEmpty()) {
                ToastUtil.ShortToast("请输入详细地址");
                return;
            }
            if (imgsMap.isEmpty() || !imgsMap.containsKey(CARDPIC_RESUSTCODE)) {
                ToastUtil.ShortToast("请上传手持身份证照");
                return;
            }
            if (imgsMap.isEmpty() || !imgsMap.containsKey(SITEPIC_RESUSTCODE)) {
                ToastUtil.ShortToast("请上店铺证照");
                return;
            }
            if (!imgsMap.containsKey(SITECPIC_RESUSTCODE) && !imgsMap.containsKey(SITELPIC_RESUSTCODE) && !imgsMap.containsKey(SITESPIC_RESUSTCODE)) {
                ToastUtil.ShortToast("福彩、体彩、竞彩代销证照片至少上传一个");
                return;
            }

            Map<String, Object> maps = new HashMap<String, Object>();
            if (AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_uSessionId, "").toString())) {
                maps.put("uSessionId", SPUtil.get(this, Constants.key_uSessionId, "").toString());
                maps.put("invater", invater);
                maps.put("siteName", siteName);
                maps.put("linkMan", linkName);
                maps.put("sitePro", spro);
                maps.put("siteCity", scity);
                maps.put("siteAddress", edt_siteAddress.getText().toString());
                maps.put("authType", authType);

                if (!imgsMap.isEmpty() && imgsMap.containsKey(CARDPIC_RESUSTCODE)) {
                    if (imgsMap.get(CARDPIC_RESUSTCODE).isLocal()) {
                        File file = LubanUtils.Compress(this, imgsMap.get(CARDPIC_RESUSTCODE).getImgPath());
                        maps.put("cardPic", file);
                    } else {
                        maps.put("cardPic", imgsMap.get(CARDPIC_RESUSTCODE).getImgPath());
                    }
                }

                if (!imgsMap.isEmpty() && imgsMap.containsKey(SITEPIC_RESUSTCODE)) {
                    if (imgsMap.get(SITEPIC_RESUSTCODE).isLocal()) {
                        File file = LubanUtils.Compress(this, imgsMap.get(SITEPIC_RESUSTCODE).getImgPath());
                        maps.put("sitePic", file);
                    } else {
                        maps.put("sitePic", imgsMap.get(SITEPIC_RESUSTCODE).getImgPath());
                    }
                }

                if (!imgsMap.isEmpty() && imgsMap.containsKey(SITECPIC_RESUSTCODE)) {
                    if (imgsMap.get(SITECPIC_RESUSTCODE).isLocal()) {
                        File file = LubanUtils.Compress(this, imgsMap.get(SITECPIC_RESUSTCODE).getImgPath());
                        maps.put("siteCPic", file);
                    } else {
                        maps.put("siteCPic", imgsMap.get(SITECPIC_RESUSTCODE).getImgPath());
                    }
                }

                if (!imgsMap.isEmpty() && imgsMap.containsKey(SITELPIC_RESUSTCODE)) {
                    if (imgsMap.get(SITELPIC_RESUSTCODE).isLocal()) {
                        File file = LubanUtils.Compress(this, imgsMap.get(SITELPIC_RESUSTCODE).getImgPath());
                        maps.put("siteLPic", file);
                    } else {
                        maps.put("siteLPic", imgsMap.get(SITELPIC_RESUSTCODE).getImgPath());
                    }
                }

                if (!imgsMap.isEmpty() && imgsMap.containsKey(SITESPIC_RESUSTCODE)) {
                    if (imgsMap.get(SITESPIC_RESUSTCODE).isLocal()) {
                        File file = LubanUtils.Compress(this, imgsMap.get(SITESPIC_RESUSTCODE).getImgPath());
                        maps.put("siteSPic", file);
                    } else {
                        maps.put("siteSPic", imgsMap.get(SITESPIC_RESUSTCODE).getImgPath());
                    }
                }
                mPressenter.siteAuth(maps);
            }
        }
    }

    private void showAddressSelectDialog() {
        BottomDialog dialog = new BottomDialog(ShopCertificationActivity.this);
        dialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
            @Override
            public void onAddressSelected(Province province, City city, County county, Street street) {
                spro = province.name;
                scity = city.name;
                StringBuffer buffer = new StringBuffer();
                buffer.append(null == province ? "" : province.name);
                buffer.append(null == city ? "" : city.name);
                buffer.append(null == county ? "" : county.name);
                buffer.append(null == street ? "" : street.name);
                tv_adress.setText(buffer.toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openCamera(int resultCode) {
        Album.image(this)
                .singleChoice()
//                .multipleChoice()
                .widget(Widget.newDarkBuilder(this).title("图片")
                        .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                        .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                        .mediaItemCheckSelector(Color.LTGRAY, Color.RED) // 图片或者视频选择框的选择器。
                        .bucketItemCheckSelector(Color.WHITE, Color.RED) // 切换文件夹时文件夹选择框的选择器。
                        .build())
                .requestCode(resultCode)
                .camera(true)
                .columnCount(4)
//                .selectCount(1)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        String imgPath = result.get(0).getThumbPath();
                        Image img = new Image();
                        img.setLocal(true);
                        img.setImgPath(imgPath);
                        if (requestCode == CARDPIC_RESUSTCODE) {
                            imgsMap.put(CARDPIC_RESUSTCODE, img);
                        } else if (requestCode == SITEPIC_RESUSTCODE) {
                            imgsMap.put(SITEPIC_RESUSTCODE, img);
                        } else if (resultCode == SITECPIC_RESUSTCODE) {
                            imgsMap.put(SITECPIC_RESUSTCODE, img);
                        } else if (resultCode == SITELPIC_RESUSTCODE) {
                            imgsMap.put(SITELPIC_RESUSTCODE, img);
                        } else {
                            imgsMap.put(SITESPIC_RESUSTCODE, img);
                        }
                        Glide.with(ShopCertificationActivity.this)
                                .load(imgPath)
                                .centerCrop()
                                .crossFade()
                                .into(requestCode == CARDPIC_RESUSTCODE ? img_card : requestCode == SITEPIC_RESUSTCODE ? img_sitePic :
                                        requestCode == SITECPIC_RESUSTCODE ? img_siteCPic : requestCode == SITELPIC_RESUSTCODE ? img_siteLPic : img_siteSPic);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_shopcertification;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ActivityManager.getInstance().finishAllActivityByWhitelist(ShopCertificationActivity.class);
        mPressenter = new ShopCertificationPressenter(this);
        loadData();
    }

    @Override
    public void loadData() {
        mPressenter.getSiteAuth(SPUtil.get(ShopCertificationActivity.this, Constants.key_uSessionId, "").toString());
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("店铺认证");
//        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPressenter.unSubscribe();
        imgsMap.clear();
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            SnackbarUtil.showMessage(this.mToolbar, "再按一次退出");
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

    @Override
    public void getSiteAuthSessuful(JsonObject json) {
        String resultText = "";
        if (json.has("resultText") && AppValidationMgr.isNotEmpty(json.get("resultText").toString())) {
            resultText = json.get("resultText").getAsString();
        }
        if (json.has("result") && AppValidationMgr.isNotEmpty(json.get("result").toString())) {
            if (json.get("result").getAsInt() == 0 ) {
               //未知啊
            } else if( json.get("result").getAsInt() == 1){
                //用户刚注册，需要认证彩站
                tv_submit.setEnabled(true);
                hint.setText(json.get("resultText").getAsString());
            }else if (json.get("result").getAsInt() == 2) {
                //审核中
                hint.setText(json.get("resultText").getAsString());
                Layout_invater.setVisibility(View.GONE);
                tv_submit.setText("审核中，请勿提交");
                tv_submit.setEnabled(false);
                edt_siteName.setEnabled(false);
                edt_linkMan.setEnabled(false);
                tv_adress.setEnabled(false);
                edt_siteAddress.setEnabled(false);
                edt_siteAddress.setEnabled(false);
                layout_choice_address.setEnabled(false);
                layout_card.setEnabled(false);
                layout_sitePic.setEnabled(false);
                layout_siteCPic.setEnabled(false);
                layout_siteLPic.setEnabled(false);
                layout_siteSPic.setEnabled(false);
            } else if (json.get("result").getAsInt() == 3) {
                //认证没通过
                authType = "1";
                hint.setText(json.get("resultText").getAsString());
                tv_submit.setText("重新提交");
                tv_submit.setEnabled(true);
            } else {
                ToastUtil.ShortToast(resultText);
            }
        }
        if (json.has("siteName") && AppValidationMgr.isNotEmpty(json.get("siteName").toString())) {
            edt_siteName.setText(json.get("siteName").getAsString());
        }
        if (json.has("linkMan") && AppValidationMgr.isNotEmpty(json.get("linkMan").toString())) {
            edt_linkMan.setText(json.get("linkMan").getAsString());
        }
        if (json.has("sitePro") && AppValidationMgr.isNotEmpty(json.get("sitePro").toString()) ||
                json.has("siteCity") && AppValidationMgr.isNotEmpty(json.get("siteCity").toString())) {
            spro = json.get("sitePro").getAsString();
            scity = json.get("siteCity").getAsString();
            tv_adress.setText(json.get("sitePro").getAsString() + json.get("siteCity").getAsString());
        }
        if (json.has("siteAddress") && AppValidationMgr.isNotEmpty(json.get("siteAddress").toString())) {
            edt_siteAddress.setText(json.get("siteAddress").getAsString());
        }
        if (json.has("cardPic") && AppValidationMgr.isNotEmpty(json.get("cardPic").toString())) {
            Image img = new Image();
            img.setImgPath(json.get("cardPic").getAsString());
            img.setLocal(false);
            imgsMap.put(CARDPIC_RESUSTCODE, img);
            Glide.with(ShopCertificationActivity.this)
                    .load(json.get("cardPic").getAsString())
                    .centerCrop()
                    .crossFade()
                    .into(img_card);
        }
        if (json.has("sitePic") && AppValidationMgr.isNotEmpty(json.get("sitePic").toString())) {
            Image img = new Image();
            img.setImgPath(json.get("sitePic").getAsString());
            img.setLocal(false);
            imgsMap.put(SITEPIC_RESUSTCODE, img);
            Glide.with(ShopCertificationActivity.this)
                    .load(json.get("sitePic").getAsString())
                    .centerCrop()
                    .crossFade()
                    .into(img_sitePic);
        }
        if (json.has("siteCPic") && AppValidationMgr.isNotEmpty(json.get("siteCPic").toString())) {
            Image img = new Image();
            img.setImgPath(json.get("siteCPic").getAsString());
            img.setLocal(false);
            imgsMap.put(SITECPIC_RESUSTCODE, img);
            Glide.with(ShopCertificationActivity.this)
                    .load(json.get("siteCPic").getAsString())
                    .centerCrop()
                    .crossFade()
                    .into(img_siteCPic);
        }
        if (json.has("siteLPic") && AppValidationMgr.isNotEmpty(json.get("siteLPic").toString())) {
            Image img = new Image();
            img.setImgPath(json.get("siteLPic").getAsString());
            img.setLocal(false);
            imgsMap.put(SITELPIC_RESUSTCODE, img);
            Glide.with(ShopCertificationActivity.this)
                    .load(json.get("siteLPic").getAsString())
                    .centerCrop()
                    .crossFade()
                    .into(img_siteLPic);
        }
        if (json.has("siteSPic") && AppValidationMgr.isNotEmpty(json.get("siteSPic").toString())) {
            Image img = new Image();
            img.setImgPath(json.get("siteSPic").getAsString());
            img.setLocal(false);
            imgsMap.put(SITESPIC_RESUSTCODE, img);
            Glide.with(ShopCertificationActivity.this)
                    .load(json.get("siteSPic").getAsString())
                    .centerCrop()
                    .crossFade()
                    .into(img_siteSPic);
        }
    }

    @Override
    public void siteAuthSessuful(String msg) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogBuilder.alertDialog(ShopCertificationActivity.this).setMessage(msg)
//                        .setCancelable(false)
                        .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();
            }
        }, 1000 * 2);
    }

    @Override
    public void siteFild(String msg) {
        ToastUtil.ShortToast(msg);
    }
}
