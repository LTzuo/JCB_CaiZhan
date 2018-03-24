package com.cjkj.jcb_caizhan.modul.Personal_Center.launch_crowd;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.utils.DateHelper;
import com.cjkj.jcb_caizhan.utils.DecCalUtil;
import com.cjkj.jcb_caizhan.utils.LubanUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.cjkj.jcb_caizhan.widget.NineGridView.NineGridAdapter;
import com.cjkj.jcb_caizhan.widget.NoScollGridView;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.dilusense.customkeyboard.KeyboardNumber;
import com.dilusense.customkeyboard.KeyboardUtils;
import com.previewlibrary.GPreviewBuilder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qing.soft.keyboardlib.CustomKeyboardHelper;
import cn.yhq.dialog.core.DialogBuilder;

/**
 * 个人中心-发起众筹
 */
public class LaunchCrowdfundingActivity extends RxBaseActivity implements NineGridAdapter.onItemImageViewClickListener, LunchCrowdContract.ILunchCrowdView {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    NineGridAdapter mNineGridAdapter;
    GridLayoutManager mGridLayoutManager;
    private List<ImageItem> Imgs = new ArrayList<>();

    private static final int REQUEST_CODE = 1;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar_custom)
    TextView toolbar_custom;

    @Bind(R.id.mTagGrid)
    NoScollGridView mTagGrid;
    @Bind(R.id.edit_tag)
    EditText edit_tag;

    @Bind(R.id.tv_goto_date)
    TextView tv_goto_date;
    @Bind(R.id.tv_goto_time)
    TextView tv_goto_time;

    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.edit_context)
    EditText edit_context;
    @Bind(R.id.tv_price)
    TextView tv_price;
    @Bind(R.id.tv_hint)
    TextView tv_hint;

    CustomKeyboardHelper helper;

    LunchCrowdPresenter mPresenter;

    @OnClick({R.id.tv_launch, R.id.tv_goto_date, R.id.tv_goto_time,R.id.toolbar_custom})
    public void onBtnClick(View v) {
        if (v.getId() == R.id.tv_launch) {
            LaunchCrowd();
        } else if (v.getId() == R.id.tv_goto_date) {
            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                    .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                        @Override
                        public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(year + "-");
                            if (String.valueOf(monthOfYear).length() == 1) {
                                buffer.append("0" + (monthOfYear + 1) + "-");
                            } else
                                buffer.append((monthOfYear + 1) + "-");
                            if (String.valueOf(dayOfMonth).length() == 1) {
                                buffer.append("0" + dayOfMonth);
                            } else
                                buffer.append(dayOfMonth);
                            tv_goto_date.setText(buffer.toString());
                        }
                    })
                    .setFirstDayOfWeek(Calendar.SUNDAY)
                    // .setPreselectedDate(towDaysAgo.getYear(), towDaysAgo.getMonthOfYear() - 1, towDaysAgo.getDayOfMonth())
                    // .setDateRange(minDate, null)
                    .setDoneText("确认")
                    .setCancelText("取消");
            // .setThemeDark(true);
            cdp.show(getSupportFragmentManager(), "日期选择");
        } else if (v.getId() == R.id.tv_goto_time) {
            RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                    .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                            StringBuffer buffer = new StringBuffer();
                            if (String.valueOf(hourOfDay).length() == 1) {
                                buffer.append("0" + hourOfDay);
                            } else
                                buffer.append(hourOfDay);
                            if (String.valueOf(minute).length() == 1) {
                                buffer.append(":" + "0" + minute);
                            } else
                                buffer.append(":" + minute);
                            dialog.dismiss();
                            tv_goto_time.setText(buffer.toString());
                        }
                    })
                    .setStartTime(10, 10)
                    .setDoneText("确认")
                    .setCancelText("取消");
            //.setThemeDark(true);
            rtpd.show(getSupportFragmentManager(), "时间选择");
        }else if(v.getId() == R.id.toolbar_custom){
            Intent intent = new Intent(LaunchCrowdfundingActivity.this,CrowdHistoryActivity.class);
            startActivity(intent);
        }
    }

    private void LaunchCrowd() {
        if (edit_tag.getText().toString().isEmpty()) {
            ToastUtil.ShortToast("请输入或选择份数");
            return;
        }
        if (edit.getText().toString().isEmpty()) {
            ToastUtil.ShortToast("请输入总金额");
            return;
        }
        if (edit_context.getText().toString().isEmpty()) {
            ToastUtil.ShortToast("请输入方案介绍");
            return;
        }
        if (Imgs.isEmpty()) {
            ToastUtil.ShortToast("请上传众筹照片");
            return;
        }
        Map<String, Object> maps = new HashMap<String, Object>();
        for (int i = 0; i < Imgs.size(); i++) {
            if (Imgs.get(i).isLocal()) {
                File file = LubanUtils.Compress(this, Imgs.get(i).getUrl());
                maps.put(Constants.ImgCrowArray[i], file);
            } else {
                maps.put(Constants.ImgCrowArray[i], Imgs.get(i).getUrl());
            }
        }
        maps.put("uSessionId", SPUtil.get(this, Constants.key_uSessionId, "").toString());
        maps.put("orderPart", edit_tag.getText().toString());
        maps.put("amount", edit.getText().toString());
        maps.put("endTime", tv_goto_date.getText().toString() + " " + tv_goto_time.getText().toString());
        maps.put("content", edit_context.getText().toString());
        maps.put("putType", "0");//putType  0 新建   1调整

        DialogBuilder.alertDialog(LaunchCrowdfundingActivity.this).setMessage("该订单会在 ( " + tv_goto_date.getText().toString() + " " + tv_goto_time.getText().toString()
                + " ) 自动终止众筹，确定发起吗？")
                .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.putCrowd(maps);
                            }
                        }).start();
                    }
                }).create().show();

    }

    private void init() {
        edit_tag.setText("");
        edit.setText("");
        edit_context.setText("");
        Imgs.clear();
        mNineGridAdapter.setDatas(Imgs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch_crowdfunding;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mPresenter = new LunchCrowdPresenter(this);
        initTagGrid();
        initTvDate();
        initRecyclerView();
        initListeners();
    }

    private void initListeners() {
        edit_tag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Calculation(charSequence.toString(), edit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Calculation(edit_tag.getText().toString(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void Calculation(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) {
            tv_price.setText("0.00");
            tv_hint.setText("技术服务费2%");
            return;
        }
        double v1 = Double.valueOf(s1);
        double v2 = Double.valueOf(s2);
        double value1 = DecCalUtil.div(v2, v1, 3);
        double value2 = DecCalUtil.mul(v2, 0.002);
        tv_price.setText(value1 + "");
        tv_hint.setText("技术服务费" + value2 + "元");
    }

    //初始化时间选择
    private void initTvDate() {
        Date date = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 3);

        tv_goto_date.setText(DateHelper.getInstance().getDataString_2(now.getTime()));
        tv_goto_time.setText(DateHelper.getInstance().getNowTime(date));
    }

    //初始化金额键盘
    private void initKeyboard() {
        KeyboardNumber keyboardIdentity = new KeyboardNumber(this);
        KeyboardUtils.bindEditTextEvent(keyboardIdentity, edit);
    }

    //初始化份数
    private void initTagGrid() {
        TagGridAdapter mTagGridAdapter = new TagGridAdapter(this);
        mTagGrid.setAdapter(mTagGridAdapter);
        mTagGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mTagGridAdapter.SelsectItem(i);
                edit_tag.setText(mTagGridAdapter.getSelect());
            }
        });
    }

    /**
     * 打开相册
     *
     * @param count
     */
    private void openCamera(int count) {
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
                            Imgs.add(0, item);
                        }
                        mNineGridAdapter.setDatas(Imgs);
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
                .setData(Imgs)
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
        for (int i = firstCompletelyVisiblePos; i < Imgs.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.item_img);
                thumbView.getGlobalVisibleRect(bounds);
            }
            Imgs.get(i).setBounds(bounds);
        }
    }

    @Override
    public void initRecyclerView() {
        Imgs = new ArrayList<>();
        mNineGridAdapter = new NineGridAdapter(mRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mNineGridAdapter.setDatas(Imgs);
        mNineGridAdapter.setOnItemImageViewClickListener(this);
        mNineGridAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if (mNineGridAdapter.getItemCount() == 1) {
                    openCamera(3);
                } else {
                    if (Imgs.size() < 3) {
                        if (Imgs.size() == 1 && position == 1) {
                            openCamera(2);
                        } else if (Imgs.size() == 2 && position == 2) {
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
        toolbar_title.setText("众筹");
        toolbar_custom.setText("历史");
        toolbar_custom.setVisibility(View.VISIBLE);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    @Override
    public void Successful(String msg) {
        ToastUtil.ShortToast(msg);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 1000 * 1);


    }

    @Override
    public void Faild(String msg) {
        ToastUtil.ShortToast(msg);

    }


    @Override
    public void deleItem(int position) {
        Imgs.remove(position);
        mNineGridAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Imgs.clear();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

}