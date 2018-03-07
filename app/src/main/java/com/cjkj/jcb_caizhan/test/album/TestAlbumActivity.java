package com.cjkj.jcb_caizhan.test.album;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.widget.album.Utils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;
import java.util.ArrayList;
import butterknife.OnClick;

/**
 * Created by 1 on 2018/3/6.
 */
public class TestAlbumActivity  extends RxBaseActivity {

    private static final int RESUST_CODE = 1;
    private static final int RESUST_CROP_CODE = 2;

    ArrayList<String> imageList = new ArrayList<>();

    @OnClick({R.id.Album_header,R.id.Album})
    public void BtnClick(View v){
        if(v.getId() == R.id.Album_header){
            //头像裁剪
            Album.image(this) // 选择图片。
                    .multipleChoice()
                    .requestCode(RESUST_CODE)
                    .camera(true)
                    .columnCount(4)
                    .selectCount(1)
                    //  .checkedList()
                    //  .filterSize()
                    //  .filterMimeType()
                    //  .afterFilterVisibility() // 显示被过滤掉的文件，但它们是不可用的。
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                            imageList.clear();
                            for (AlbumFile album : result){
                                imageList.add(album.getThumbPath());
                            }
                            Crop(imageList);
                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(int requestCode, @NonNull String result) {
                        }
                    })
                    .start();

        }else if(v.getId() == R.id.Album){
            //图片多选
            Album.image(this) // 选择图片。
                    .multipleChoice()
                    .requestCode(RESUST_CODE)
                    .camera(true)
                    .columnCount(4)
                    .selectCount(9)
                    //  .checkedList()
                    //  .filterSize()
                    //  .filterMimeType()
                    //  .afterFilterVisibility() // 显示被过滤掉的文件，但它们是不可用的。
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {

                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(int requestCode, @NonNull String result) {
                        }
                    })
                    .start();
        }

    }

    /**
     * 裁剪
     */
    private void Crop(ArrayList<String> imageList){
        String cropDirectory = Utils.getAppRootPath(this).getAbsolutePath();
        Durban.with(this)
                // 裁剪界面的标题。
                .title("Crop")
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                // 图片路径list或者数组。
                .inputImagePaths(imageList)
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
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESUST_CROP_CODE: {
                // Analyze the list of paths after cropping.
                if (resultCode != RESULT_OK) {
                    ArrayList<String> mImageList = Durban.parseResult(data);

                } else {

                }
                break;
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_activity_album;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }


}
