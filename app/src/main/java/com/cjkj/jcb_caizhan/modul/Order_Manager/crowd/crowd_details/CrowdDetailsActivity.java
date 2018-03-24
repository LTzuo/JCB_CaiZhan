package com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.crowd_details;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdContract;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.crowd.CrowdPresenter;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.UserListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.over_ticket.UserTableListViewAdapter;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.LubanUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.CustomTextView;
import com.cjkj.jcb_caizhan.widget.NineGridView.GridAdapter;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.previewlibrary.GPreviewBuilder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import cn.carbs.android.avatarimageview.library.AvatarImageView;
import cn.yhq.dialog.core.DialogBuilder;

/**
 * 众筹详情
 */
public class CrowdDetailsActivity extends RxBaseActivity implements CrowdContract.ICrowdView {

    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_TICKET_CODE = 2;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.mCustomTextView)
    CustomTextView mCustomTextView;
    @Bind(R.id.img_avatar)
    AvatarImageView img_avatar;
    @Bind(R.id.tv_autherSiteName)
    TextView tv_autherSiteName;
    @Bind(R.id.tv_autherName)
    TextView tv_autherName;
    @Bind(R.id.tv_autherDestAddr)
    TextView tv_autherDestAddr;
    @Bind(R.id.edt_content)
    EditText edt_content;

    @Bind(R.id.mGridRecyclerView)
    RecyclerView mGridRecyclerView;

    @Bind(R.id.mTicketGridRecyclerView)
    RecyclerView mTicketGridRecyclerView;

    @Bind(R.id.TablelistView)
    SubListView mTablelistView;
    @Bind(R.id.tv_hint_participateIn)
    TextView tv_hint_participateIn;

    GridAdapter mGridAdapter;
    GridAdapter mTicketGridAdapter;
    UserTableListViewAdapter mTableTestAdapter;

    CrowdPresenter mCrowdPresenter;

    String orderId;
    CrowdEntity entity;
    String updateType = "0";
    /************
     * 组合按钮
     ********************/
//    @Bind(R.id.LayoutBtns)
//    FrameLayout LayoutBtns;
    @Bind(R.id.LayoutBtn2)
    LinearLayout LayoutBtn2;
    @Bind(R.id.LayoutBtn1)
    RelativeLayout LayoutBtn1;
    @Bind(R.id.LayoutBtn3)
    LinearLayout LayoutBtn3;
    @Bind(R.id.Layout_ticket)
    LinearLayout Layout_ticket;

    @Bind(R.id.mCtvWinning)
    CustomTextView mCtvWinning;
    @Bind(R.id.mCtvNoWinning)
    CustomTextView mCtvNoWinning;

    // private List<ImageItem> imgs = new ArrayList<>();

    @OnClick({R.id.mBtnChenge, R.id.mSureCtv, R.id.mCancleCtv, R.id.mStopCtv, R.id.mRevokeCtv,
            R.id.mCtvSubmit,R.id.mCtvWinning,R.id.mCtvNoWinning})
    public void OnBtnClick(View v) {
        if (v.getId() == R.id.mBtnChenge) {
            LayoutBtn1.setVisibility(View.GONE);
            LayoutBtn2.setVisibility(View.VISIBLE);
            //设置方案和图片可编辑
            mGridAdapter.setNineVisibility(true);
            edt_content.setEnabled(true);
        } else if (v.getId() == R.id.mSureCtv) {
            //确定修改众筹方案按钮
            if (edt_content.getText().toString().isEmpty()) {
                ToastUtil.ShortToast("请输入方案介绍");
                return;
            }
            if (mGridAdapter.getImages().isEmpty()) {
                ToastUtil.ShortToast("请上传修改的图片");
                return;
            }
            putCrowd(mGridAdapter.getImages(), edt_content.getText().toString());
        } else if (v.getId() == R.id.mCancleCtv) {
            //取消修改
            LayoutBtn1.setVisibility(View.VISIBLE);
            LayoutBtn2.setVisibility(View.GONE);
            //设置方案和图片不可可编辑
            mGridAdapter.setNineVisibility(false);
            edt_content.setEnabled(false);
            setCrowdDatas(entity);
        } else if (v.getId() == R.id.mStopCtv) {
            //终止众筹
            updateType = "1";
            updateCrowd(updateType);
        } else if (v.getId() == R.id.mRevokeCtv) {
            //撤销众筹
            ToastUtil.ShortToast("撤销众筹");
        } else if (v.getId() == R.id.mCtvSubmit) {
            //确定上传打票
            updateType = "2";
            if (mTicketGridAdapter.getImages().isEmpty()) {
                ToastUtil.ShortToast("请上传票据照片");
                return;
            }
            updateCrowd(updateType);
        } else if(v.getId() == R.id.mCtvWinning){
            if(entity.getState().equals("3")) return;
             //中奖
            updateType = "3";
            showEditTextDialog(updateType);
        }else if(v.getId() == R.id.mCtvNoWinning){
            //未中奖
            updateType = "4";
            updateCrowd(updateType);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_crowd_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra("orderId");
        mCrowdPresenter = new CrowdPresenter(this);
        initRefreshLayout();
        initRecyclerView();
        initTicketGridView();
        initTableListView();
        //mCustomTextView.setSolidColor();
    }

    //初始化上传打票布局
    private void initTicketGridView() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3);
        mTicketGridRecyclerView.setLayoutManager(mGridLayoutManager);
        mTicketGridAdapter = new GridAdapter(mGridRecyclerView);
        mTicketGridAdapter.setGridManager(mGridLayoutManager);
        mTicketGridRecyclerView.setAdapter(mTicketGridAdapter);
        //默认设置上传图片可编辑
        mTicketGridAdapter.setNineVisibility(true);
        mTicketGridAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if (mTicketGridAdapter.getImages().size() == position) {
                    openCamera(REQUEST_TICKET_CODE);
                } else {
                    mTicketGridAdapter.LookBanners(position);
                }
            }
        });
    }

    @Override
    public void initRecyclerView() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3);
        mGridRecyclerView.setLayoutManager(mGridLayoutManager);
        mGridAdapter = new GridAdapter(mGridRecyclerView);
        mGridAdapter.setGridManager(mGridLayoutManager);
        mGridRecyclerView.setAdapter(mGridAdapter);
        //默认设置方案显示图片可编辑
        mGridAdapter.setNineVisibility(false);

        mGridAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                mGridAdapter.LookBanners(position);
            }
        });
    }

    //初始化表格
    private void initTableListView() {
        mTableTestAdapter = new UserTableListViewAdapter(this);
        mTablelistView.setAdapter(mTableTestAdapter);
    }

    @Override
    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    public void loadData() {
        mCrowdPresenter.getCrowdDetils(SPUtil.get(CrowdDetailsActivity.this, Constants.key_uSessionId, "").toString(),
                "2", orderId);
        finishTask();
    }

    @Override
    public void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("众筹详情");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    /******
     * 众筹发起的数据
     ********/
    private void setCrowdDatas(CrowdEntity entity) {
        edt_content.setText(entity.getContent());
        List<ImageItem> imgs = new ArrayList<>();
        if (!entity.getCrowdPics().isEmpty()) {
            String[] strs = entity.getCrowdPics().split(",|;");
            for (int i = 0, len = strs.length; i < len; i++) {
                ImageItem item = new ImageItem(strs[i]);
                item.setLocal(false);
                imgs.add(0, item);
            }
        }
        mGridAdapter.setDatas(imgs);
    }

    /**
     * 修改众筹方案
     */
    private void putCrowd(List<ImageItem> Imgs, String content) {
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
        maps.put("content", content);
        maps.put("putType", "1");//putType  0 新建   1调整
        maps.put("orderId", entity.getOrderId());

        DialogBuilder.alertDialog(CrowdDetailsActivity.this).setMessage("确定要修改众筹方案吗？")
                .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCrowdPresenter.putCrowd(maps);
                    }
                }).create().show();
    }

    /**
     * 更新众筹方案(操作类型)
     * updateType (0撤单，1中止众筹，2已打票，3未中奖，4已中奖)
     */
    private void updateCrowd(String updateType) {
        String msg = "";
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("uSessionId", SPUtil.get(this, Constants.key_uSessionId, "").toString());
        maps.put("orderId", entity.getOrderId());
        maps.put("updateType", updateType);
        if (updateType.equals("1")) {
            //只传三个参数
            msg = "确定要终止众筹吗？";
        } else if (updateType.equals("2")) {
            //含有图片
            msg = "确定要打票吗？";
            for (int i = 0; i < mTicketGridAdapter.getImages().size(); i++) {
                if (mTicketGridAdapter.getImages().get(i).isLocal()) {
                    File file = LubanUtils.Compress(this, mTicketGridAdapter.getImages().get(i).getUrl());
                    maps.put(Constants.ImgCrowArray[i], file);
                } else {
                    maps.put(Constants.ImgCrowArray[i], mTicketGridAdapter.getImages().get(i).getUrl());
                }
            }
        }else if(updateType.equals("4")){
            //设置未中奖
            msg = "确定要设置未中奖吗吗？";
        }
        DialogBuilder.alertDialog(CrowdDetailsActivity.this).setMessage(msg)
                .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCrowdPresenter.updateCrowd(maps);
                    }
                }).create().show();
    }

    private void showEditTextDialog(String updateType){
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("uSessionId", SPUtil.get(this, Constants.key_uSessionId, "").toString());
        maps.put("orderId", entity.getOrderId());
        maps.put("updateType", updateType);
        DialogBuilder.editTextDialog(this).setTitle("中奖")
                .setOnEditTextDialogListener(new DialogBuilder.OnEditTextDialogListener() {
                    @Override
                    public void onEditTextCreated(EditText editText) {
                        editText.setHint("请输入中奖金额");
                        //  editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_VARIATION_NORMAL);
                        editText.addTextChangedListener(new TextWatcher() {
                            private int selectionStart;
                            private int selectionEnd;
                            private CharSequence temp;

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                // TODO Auto-generated method stub
                                temp = s;
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                // TODO Auto-generated method stub
                                if(s.toString().length() == 0)return;
                                selectionStart = editText.getSelectionStart();
                                selectionEnd = editText.getSelectionEnd();
//
                                if (!isOnlyPointNumber(editText.getText().toString())) {
                                    //删除多余输入的字（不会显示出来）
                                    s.delete(selectionStart - 1, selectionEnd);
                                    editText.setText(s);
                                }

                            }
                        });
                    }

                    @Override
                    public boolean onEditTextSelected(EditText editText, String text) {
                        if (editText.getText().toString().isEmpty()) {
                            ToastUtil.ShortToast("中奖金额不能为空");
                            return true;
                        }
                        maps.put("amount", text);
                        mCrowdPresenter.updateCrowd(maps);
                        hideKeyboard(editText);
                        return false;
                    }
                }).show();
    }

    //保留三位小数
    public static boolean isOnlyPointNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,3}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    public void DetilsSuccessFul(CrowdEntity entity) {
        this.entity = entity;
        Glide.with(this)
                .load(entity.getAutherSitePic())
                .priority(Priority.HIGH)
                .into(img_avatar);

        tv_autherSiteName.setText(entity.getAutherSiteName());
//        mCustomTextView.setText(entity.getCrowdState());
        tv_autherName.setText("发起人：" + entity.getAutherName());
        tv_autherDestAddr.setText("  电话：" + entity.getAutherDestAddr());

        setCrowdDatas(entity);
        //**************组合按钮以及方案状态设置*************************
        if (entity.getState().equals("0")) {
            //凑单中，众筹方案可修改
            LayoutBtn1.setVisibility(View.VISIBLE);
            LayoutBtn2.setVisibility(View.GONE);
            LayoutBtn3.setVisibility(View.GONE);
            mCustomTextView.setText("凑单中");
        } else if (entity.getState().equals("1")) {
            //待打票，方案不可修改只能打票
            LayoutBtn1.setVisibility(View.GONE);
            LayoutBtn2.setVisibility(View.GONE);
            LayoutBtn3.setVisibility(View.GONE);
            mCustomTextView.setText("等待打票");
            Layout_ticket.setVisibility(View.VISIBLE);
        } else if (entity.getState().equals("2")) {
            //打完票，待开奖
            mCustomTextView.setText("待开奖");
            LayoutBtn1.setVisibility(View.GONE);
            LayoutBtn2.setVisibility(View.GONE);
            LayoutBtn3.setVisibility(View.VISIBLE);
        } else if(entity.getState().equals("3")){
            //中奖
            LayoutBtn1.setVisibility(View.GONE);
            LayoutBtn2.setVisibility(View.GONE);
            LayoutBtn3.setVisibility(View.VISIBLE);
            mCustomTextView.setText("已中奖");
            mCtvWinning.setText(entity.getCrowdState());
            mCtvNoWinning.setVisibility(View.GONE);
        }else if(entity.getState().equals("4")){
            //未中奖
            LayoutBtn1.setVisibility(View.GONE);
            LayoutBtn2.setVisibility(View.GONE);
            LayoutBtn3.setVisibility(View.GONE);
            mCustomTextView.setText("未中奖");
        }else if(entity.getState().equals("5")){
            //已撤单
            LayoutBtn1.setVisibility(View.GONE);
            LayoutBtn2.setVisibility(View.GONE);
            LayoutBtn3.setVisibility(View.GONE);
            mCustomTextView.setText("已撤单");
        }
        //***************参与众筹*****************************
        tv_hint_participateIn.setText("总计" + entity.getOrderPart() + "份，剩余" + entity.getNoPart() + "份，每份" + entity.getPerAmount()
                + "元(含服务费" + entity.getServiceAmount() + "元)");

        List<UserListEntity> userList = new ArrayList<>();
        userList.addAll(FastJsonUtil.getBeanList(entity.getOrderList(), UserListEntity.class));
        userList.add(0, new UserListEntity("", "参与众筹", "等级", "出资", "份额", "奖金/加奖"));
        mTableTestAdapter.setInfo(userList);
    }

    @Override
    public void PutSuccessFul(String msg) {
        ToastUtil.ShortToast(msg);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LayoutBtn1.setVisibility(View.VISIBLE);
                LayoutBtn2.setVisibility(View.GONE);
                //设置方案和图片不可可编辑
                mGridAdapter.setNineVisibility(false);
                edt_content.setEnabled(false);
            }
        }, 1 * 1000);
    }

    @Override
    public void UpdateCrowdSuccessFul(String msg) {
        ToastUtil.ShortToast(msg);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (updateType.equals("1")) {
                    //终止众筹返回
                    LayoutBtn1.setVisibility(View.GONE);
                    LayoutBtn2.setVisibility(View.GONE);
                    LayoutBtn3.setVisibility(View.GONE);
                    mCustomTextView.setText("等待打票");
                    Layout_ticket.setVisibility(View.VISIBLE);
                }else if(updateType.equals("2")){
                    //打票返回
                    mCustomTextView.setText("待开奖");
                    Layout_ticket.setVisibility(View.GONE);
                    LayoutBtn1.setVisibility(View.GONE);
                    LayoutBtn2.setVisibility(View.GONE);
                    LayoutBtn3.setVisibility(View.VISIBLE);
                }else if(updateType.equals("3")){
                    //中奖返回
                    mCustomTextView.setText("已中奖");
                    mCtvNoWinning.setVisibility(View.GONE);
                } else if(updateType.equals("4")){
                    //未中奖返回
                    mCustomTextView.setText("未中奖");
                    LayoutBtn3.setVisibility(View.GONE);
                }
            }
        }, 1 * 1000);
    }

    @Override
    public void Filed(String msg) {
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void ListSuccessFul(List<CrowdEntity> crowdList) {
        //无返回
    }

    /**
     * 打开相机
     */
    private void openCamera(int result) {
        Album.camera(this)
                .image()
                .requestCode(result)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ImageItem item = new ImageItem(result);
                        item.setLocal(true);
                        mTicketGridAdapter.getImages().add(0, item);
                        mTicketGridAdapter.setDatas(mTicketGridAdapter.getImages());
                    }
                })
                .start();
    }


}
