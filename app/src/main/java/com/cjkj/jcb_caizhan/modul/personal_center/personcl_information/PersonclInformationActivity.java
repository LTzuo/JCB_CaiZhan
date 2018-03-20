package com.cjkj.jcb_caizhan.modul.Personal_Center.personcl_information;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.Personal_Center.personcl_information.modify.ModifyActivity;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.Album.Utils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * 个人中心-个人信息
 */
public class PersonclInformationActivity extends RxBaseActivity {

    private static final int RESUST_CODE = 1;
    private static final int RESUST_CROP_CODE = 2;
    ArrayList<String> imageList = new ArrayList<>();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.mAvatarImageView)
    AvatarImageView mAvatarImageView;

    Intent mIntent;

    @OnClick({R.id.Layout_header,R.id.Layout_wechat,R.id.Layout_phongnumber,R.id.Layout_ShopNotices,
            R.id.Layout_identityauthentication,R.id.Layout_Shopcertification,R.id.Layout_chengepwd,
            R.id.Layout_txwd,R.id.Layout_address,R.id.Layout_alipay})
    public void OnBtnClick(View v) {
        if (v.getId() == R.id.Layout_header) {
            openCamera();
        }else if(v.getId() == R.id.Layout_wechat){
            mIntent.putExtra("modify",0);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_phongnumber){
            mIntent.putExtra("modify",1);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_ShopNotices){
            mIntent.putExtra("modify",2);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_identityauthentication){
            mIntent.putExtra("modify",3);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_Shopcertification){
            mIntent.putExtra("modify",4);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_chengepwd){
            mIntent.putExtra("modify",5);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_txwd){
            mIntent.putExtra("modify",6);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_address){
            mIntent.putExtra("modify",7);
            startActivity(mIntent);
        }else if(v.getId() == R.id.Layout_alipay){
            mIntent.putExtra("modify",8);
            startActivity(mIntent);
        }
    }

    private void openCamera() {
        Album.image(this)
                .multipleChoice()
                .widget(Widget.newDarkBuilder(this).title("图片")
                        .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                        .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                        .mediaItemCheckSelector(Color.LTGRAY, Color.RED) // 图片或者视频选择框的选择器。
                        .bucketItemCheckSelector(Color.WHITE, Color.RED) // 切换文件夹时文件夹选择框的选择器。
                        .build())
                .requestCode(RESUST_CODE)
                .camera(true)
                .columnCount(4)
                .selectCount(1)
                //  .checkedList()
//                  .filterSize()
                //  .filterMimeType()
                //  .afterFilterVisibility() // 显示被过滤掉的文件，但它们是不可用的。
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                        imageList.clear();
                        for (AlbumFile album : result) {
                            imageList.add(album.getThumbPath());
                        }
                        Crop(imageList.get(0));
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
        return R.layout.activity_personcl_information;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mIntent = new Intent(this, ModifyActivity.class);
    }

    /**
     * 裁剪
     */
    private void Crop(String imagePath) {
        String cropDirectory = Utils.getAppRootPath(this).getAbsolutePath();
        Durban.with(this)
                // 裁剪界面的标题。
                .title("裁剪")
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                // 图片路径list或者数组。
                .inputImagePaths(imagePath)
                // 图片输出文件夹路径。
                .outputDirectory(cropDirectory)
                // 裁剪图片输出的最大宽高。
                .maxWidthHeight(500, 500)
                // 裁剪时的宽高比。
                .aspectRatio(1, 1)
                // 图片压缩格式：JPEG、PNG。
                .compressFormat(Durban.COMPRESS_JPEG)
                // 图片压缩质量，请参考：Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                .compressQuality(90)
                // 裁剪时的手势支持：ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .controller(
                        Controller.newBuilder()
                                .enable(false) // 是否开启控制面板。
                                .rotation(true) // 是否有旋转按钮。
                                .rotationTitle(true) // 旋转控制按钮上面的标题。
                                .scale(true) // 是否有缩放按钮。
                                .scaleTitle(true) // 缩放控制按钮上面的标题。
                                .build()) // 创建控制面板配置。
                .requestCode(RESUST_CROP_CODE)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case RESUST_CROP_CODE: {
                List<String> mImageList = Durban.parseResult(data);
                Glide.with(this)
                        .load(mImageList.get(0))
                        .centerCrop()
                        .crossFade()
                        .into(mAvatarImageView);
                break;
            }
        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("个人信息");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }
}
