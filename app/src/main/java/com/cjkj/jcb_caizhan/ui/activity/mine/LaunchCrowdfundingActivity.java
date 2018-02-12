package com.cjkj.jcb_caizhan.ui.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.entity.mine.launch_crowdfunding.ImageItem;
import com.cjkj.jcb_caizhan.ui.activity.RxBaseActivity;
import com.cjkj.jcb_caizhan.ui.adapter.helper.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.ui.adapter.mine.launch_crowfunding.NineGridAdapter;
import com.cjkj.jcb_caizhan.ui.widget.FlowlayoutTags;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.hy.telegramgallery.GalleryActivity;
import com.previewlibrary.GPreviewBuilder;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import cn.qing.soft.keyboardlib.CustomKeyboardHelper;
import cn.yhq.dialog.core.DialogBuilder;

/**
 * 个人中心-发起众筹
 */
public class LaunchCrowdfundingActivity extends RxBaseActivity implements NineGridAdapter.onItemImageViewClickListener {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    NineGridAdapter mNineGridAdapter;
    GridLayoutManager mGridLayoutManager;
    private List<ImageItem> datas = new ArrayList<>();

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
            List<String> list = new ArrayList<>();
            list.add("相机");
            list.add("相册");
            DialogBuilder.alertDialog(LaunchCrowdfundingActivity.this).setMessage("jbjhb")
                    .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LaunchCrowdfundingActivity.this, "点击了确认按钮", Toast.LENGTH_LONG).show();
                        }
                    }).create();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch_crowdfunding;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        List<String> mVals = new ArrayList<>();
        mVals.add("200份");
        mVals.add("100份");
        mVals.add("50份");
        mVals.add("20份");
        mVals.add("10份");
        //设置数据源
        mFlowlayoutTag.removeAllViews();
        mFlowlayoutTag.setTags(mVals);
        mFlowlayoutTag.setTagsUncheckedColorAnimal(false);
        mFlowlayoutTag.setOnTagClickListener(new FlowlayoutTags.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                ArrayList<Integer> tagList = mFlowlayoutTag.getCheckedTagsIndexArrayList();
                String mCategory = "";
                for (int i = 0; i < tagList.size(); i++) {
                    mCategory += mVals.get(tagList.get(i));
                }
                ToastUtil.ShortToast(mCategory);
            }
        });
//        helper = new CustomKeyboardHelper(this, R.xml.keyboardnumber);
//        helper.registerEditText(edit);
        initTransferee();
    }

    /**
     * 打开相册
     *
     * @param count
     */
    private void AlbumCamera(int count) {
//        Album.image(this)
//                .multipleChoice()
//                .columnCount(4) // 页面列表的列数。
//                .selectCount(count)  // 最多选择几张图片。
//                .camera(true) // 是否在Item中出现相机。
//                .requestCode(2)
//                .onResult(new Action<ArrayList<AlbumFile>>() {
//                    @Override
//                    public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
//                        ToastUtil.ShortToast(""+result.size());
//                        for (AlbumFile bean : result) {
//                            ImageItem item = new ImageItem(bean.getPath());
//                            datas.add(0,item);
//                        }
//                        mNineGridAdapter.setDatas(datas);
//                    }
//                })
//                .onCancel(new Action<String>() {
//                    @Override
//                    public void onAction(int requestCode, @NonNull String result) {
//                        // 用户取消了操作。
//                        ToastUtil.ShortToast("取消");
//                    }
//                })
//                .start();
        GalleryActivity.openActivity(
                this,
                new String[]{"image/jepg","image/jpg", "image/png"},//过滤掉指定类型，遵守MIME Type类型规范。eg：new String[]{"image/gif","image/png"}
                false,//true 单选，false 多选
                count,//图片可选数量限制，当singlePhoto=false时生效
                2);//请求码

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

    /**
     * 初始化图片预览
     */
    private void initTransferee() {
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
                    AlbumCamera(3);
                } else {
                    if (datas.size() < 3) {
                        if (datas.size() == 1 && position == 1) {
                            AlbumCamera(2);
                        } else if (datas.size() == 2 && position == 2) {
                            AlbumCamera(1);
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

    @Override
    public void onBackPressed() {
        if (helper.isCustomKeyboardVisible()) {
            helper.hideCustomKeyboard();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("发起众筹");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    //接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2 && data != null){
            //照片路径集合返回值
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            for (String bean : photos) {
                ImageItem item = new ImageItem(bean);
                datas.add(0, item);
            }
            mNineGridAdapter.setDatas(datas);
        }

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