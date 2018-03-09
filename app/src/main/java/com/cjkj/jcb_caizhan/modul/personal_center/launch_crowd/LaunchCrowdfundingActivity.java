package com.cjkj.jcb_caizhan.modul.personal_center.launch_crowd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.widget.FlowlayoutTags;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.dilusense.customkeyboard.KeyboardNumber;
import com.dilusense.customkeyboard.KeyboardUtils;
import com.previewlibrary.GPreviewBuilder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qing.soft.keyboardlib.CustomKeyboardHelper;

/**
 * 个人中心-发起众筹
 */
public class LaunchCrowdfundingActivity extends RxBaseActivity implements NineGridAdapter.onItemImageViewClickListener {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    NineGridAdapter mNineGridAdapter;
    GridLayoutManager mGridLayoutManager;
    private List<ImageItem> datas = new ArrayList<>();

    private static final int REQUEST_CODE = 1;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.fltag)
    FlowlayoutTags mFlowlayoutTag;
    @Bind(R.id.edit)
    EditText edit;

    CustomKeyboardHelper helper;

    @OnClick({R.id.tv_launch})
    public void onBtnClick(View v) {
        if (v.getId() == R.id.tv_launch) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch_crowdfunding;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        String[] mVals = getResources().getStringArray(R.array.FlowlayoutTagValues);
        mFlowlayoutTag.removeAllViews();
        mFlowlayoutTag.setTags(mVals);
        mFlowlayoutTag.setTagsUncheckedColorAnimal(false);
        mFlowlayoutTag.setOnTagClickListener(new FlowlayoutTags.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                ArrayList<Integer> tagList = mFlowlayoutTag.getCheckedTagsIndexArrayList();
                String mCategory = "";
                for (int i = 0; i < tagList.size(); i++) {
                    mCategory += mVals[tagList.get(i)];
                }
                ToastUtil.ShortToast(mCategory);
            }
        });
//        helper = new CustomKeyboardHelper(this, R.xml.keyboardnumber);
//        helper.registerEditText(edit);
        KeyboardNumber keyboardIdentity = new KeyboardNumber(this);
        KeyboardUtils.bindEditTextEvent(keyboardIdentity, edit);
        initRecyclerView();
    }

    private void initCamera() {

    }

    /**
     * 打开相册
     *
     * @param count
     */
    private void openCamera(int count) {
        // 自由配置选项
//        ISListConfig config = new ISListConfig.Builder()
//                .multiSelect(true)
//                .rememberSelected(false)
//                .btnTextColor(Color.WHITE)
//                .statusBarColor(getResources().getColor(R.color.colorPrimary))
//                .title("图片")
//                .titleColor(Color.WHITE)
//                .titleBgColor(getResources().getColor(R.color.colorPrimary))
//                .cropSize(1, 1, 200, 200)
//                .needCrop(false)
//                .needCamera(true)
//                .maxNum(count)
//                .build();
//        ISNav.getInstance().toListActivity(this, config, REQUEST_CODE);
        Album.image(this) // 选择图片。
                .multipleChoice()
                .widget(Widget.newDarkBuilder(this).title("图片")
                        .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                        .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                        .mediaItemCheckSelector(Color.LTGRAY, Color.RED) // 图片或者视频选择框的选择器。
                        .bucketItemCheckSelector(Color.WHITE, Color.RED) // 切换文件夹时文件夹选择框的选择器。
                        .build())
                .requestCode(REQUEST_CODE)
                .camera(true)
                .columnCount(4)
                .selectCount(count)
                //.checkedList()
                //  .filterSize()
                //  .filterMimeType()
                //  .afterFilterVisibility() // 显示被过滤掉的文件，但它们是不可用的。
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                       // List<String> pathList = data.getStringArrayListExtra("result");
                        for (AlbumFile bean : result) {
                            ImageItem item = new ImageItem(bean.getThumbPath());
                            datas.add(0, item);
                        }
                        mNineGridAdapter.setDatas(datas);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                    }
                })
                .start();
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void LookBanners(int position) {
        computeBoundsBackward(position);
        GPreviewBuilder.from(LaunchCrowdfundingActivity.this)
                .setData(datas)
                .setCurrentIndex(position)
                .setSingleFling(true)
                .setDrag(false)
                .setType(GPreviewBuilder.IndicatorType.Number)
                .start();
    }

    /**
     * * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < datas.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.item_img);
                thumbView.getGlobalVisibleRect(bounds);
            }
            datas.get(i).setBounds(bounds);
        }
    }

    @Override
    public void initRecyclerView() {
        datas = new ArrayList<>();
        mNineGridAdapter = new NineGridAdapter(mRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mNineGridAdapter.setDatas(datas);
        mNineGridAdapter.setOnItemImageViewClickListener(this);
        mNineGridAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if (mNineGridAdapter.getItemCount() == 1) {
                    openCamera(3);
                } else {
                    if (datas.size() < 3) {
                        if (datas.size() == 1 && position == 1) {
                            openCamera(2);
                        } else if (datas.size() == 2 && position == 2) {
                            openCamera(1);
                        } else {
                            LookBanners(position);
                        }
                    } else {
                        LookBanners(position);
                    }
                }
            }
        });
        mRecyclerView.setAdapter(mNineGridAdapter);
    }

//    @Override
//    public void onBackPressed() {
//        if (helper.isCustomKeyboardVisible()) {
//            helper.hideCustomKeyboard();
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("发起众筹");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            List<String> pathList = data.getStringArrayListExtra("result");
//            for (String bean : pathList) {
//                ImageItem item = new ImageItem(bean);
//                datas.add(0, item);
//            }
//            mNineGridAdapter.setDatas(datas);
//        }
    }

    @Override
    public void deleItem(int position) {
        datas.remove(position);
        mNineGridAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datas.clear();
    }

}