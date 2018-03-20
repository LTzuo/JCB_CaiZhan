package com.cjkj.jcb_caizhan.modul.ather.shop_certification;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import java.util.ArrayList;
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
   // private Map<Integer, String> Imgs = new HashMap<>();

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

    String spro, scity;

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
            String cardPic, sitePic, siteCPic = "", siteLPic = "", siteSPic = "";
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
            if (LubanUtils.imgsMap.isEmpty() || !LubanUtils.imgsMap.containsKey(CARDPIC_RESUSTCODE)) {
                ToastUtil.ShortToast("请上传手持身份证照");
                return;
            }
            cardPic = LubanUtils.getImgString(CARDPIC_RESUSTCODE);
            ToastUtil.ShortToast(cardPic);
            if (LubanUtils.imgsMap.isEmpty()  || !LubanUtils.imgsMap.containsKey(SITEPIC_RESUSTCODE)) {
                ToastUtil.ShortToast("请上店铺证照");
                return;
            }
           // sitePic = BitmapUtil.filepath2StrByBase64(Imgs.get(SITEPIC_RESUSTCODE));
            if (!LubanUtils.imgsMap.containsKey(SITECPIC_RESUSTCODE) && !LubanUtils.imgsMap.containsKey(SITELPIC_RESUSTCODE) && !LubanUtils.imgsMap.containsKey(SITESPIC_RESUSTCODE)) {
                ToastUtil.ShortToast("福彩、体彩、竞彩代销证照片至少上传一个");
                return;
            }
            if (LubanUtils.imgsMap.containsKey(SITECPIC_RESUSTCODE)) {
             //   siteCPic = Imgs.get(SITECPIC_RESUSTCODE).isEmpty() ? "" : BitmapUtil.filepath2StrByBase64(Imgs.get(SITECPIC_RESUSTCODE));
            }
            if (LubanUtils.imgsMap.containsKey(SITELPIC_RESUSTCODE)) {
              //  siteLPic = Imgs.get(SITELPIC_RESUSTCODE).isEmpty() ? "" : BitmapUtil.filepath2StrByBase64(Imgs.get(SITELPIC_RESUSTCODE));
            }
            if (LubanUtils.imgsMap.containsKey(SITESPIC_RESUSTCODE)) {
             //   siteSPic = Imgs.get(SITESPIC_RESUSTCODE).isEmpty() ? "" : BitmapUtil.filepath2StrByBase64(Imgs.get(SITESPIC_RESUSTCODE));
            }
            if (AppValidationMgr.isNotEmpty(SPUtil.get(this, Constants.key_uSessionId, "").toString())) {
                mPressenter.siteAuth(SPUtil.get(this, Constants.key_uSessionId, "").toString(), invater,
                        siteName, linkName, spro, scity, edt_siteAddress.getText().toString(),
                        cardPic, "", "", "", "", "0");
                      //  sitePic, siteCPic, siteLPic, siteSPic, cardPic, "0");
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
                        if (requestCode == CARDPIC_RESUSTCODE) {
                            LubanUtils.saveImgToMap(ShopCertificationActivity.this,result.get(0).getPath(),CARDPIC_RESUSTCODE);
                           // Imgs.put(CARDPIC_RESUSTCODE, result.get(0).getPath());
                        } else if (requestCode == SITEPIC_RESUSTCODE) {
                            LubanUtils.saveImgToMap(ShopCertificationActivity.this,result.get(0).getPath(),SITEPIC_RESUSTCODE);
//                            Imgs.put(SITEPIC_RESUSTCODE, imgPath);
                        } else if (resultCode == SITECPIC_RESUSTCODE) {
                            LubanUtils.saveImgToMap(ShopCertificationActivity.this,result.get(0).getPath(),SITECPIC_RESUSTCODE);
//                            Imgs.put(SITECPIC_RESUSTCODE, imgPath);
                        } else if (resultCode == SITELPIC_RESUSTCODE) {
                            LubanUtils.saveImgToMap(ShopCertificationActivity.this,result.get(0).getPath(),SITELPIC_RESUSTCODE);
//                            Imgs.put(SITELPIC_RESUSTCODE, imgPath);
                        } else {
                            LubanUtils.saveImgToMap(ShopCertificationActivity.this,result.get(0).getPath(),SITESPIC_RESUSTCODE);
//                            Imgs.put(SITESPIC_RESUSTCODE, imgPath);
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
        LubanUtils.imgsMap.clear();
//        Imgs.clear();
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
    public void siteAuthSessuful() {

    }

    @Override
    public void siteFild(String msg) {
       ToastUtil.ShortToast(msg);
    }
}
