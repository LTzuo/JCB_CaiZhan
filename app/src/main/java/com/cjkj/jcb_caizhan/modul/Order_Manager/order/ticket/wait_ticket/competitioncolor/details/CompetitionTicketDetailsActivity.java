package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.competitioncolor.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.OrderDetailListEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.utils.FastJsonUtil;
import com.cjkj.jcb_caizhan.utils.LubanUtils;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.cjkj.jcb_caizhan.widget.NineGridView.NineGridAdapter;
import com.cjkj.jcb_caizhan.widget.SubListView;
import com.previewlibrary.GPreviewBuilder;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.yhq.dialog.core.DialogBuilder;
import cn.yhq.dialog.core.IDialog;

/**
 * 竞彩等待打票详情
 */
public class CompetitionTicketDetailsActivity extends RxBaseActivity implements OddsContract.IOddsView, NineGridAdapter.onItemImageViewClickListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.lotteryName)
    TextView lotteryName;
    @Bind(R.id.lotteryPerion)
    TextView lotteryPerion;
    @Bind(R.id.serialNo)
    TextView serialNo;
    @Bind(R.id.orderState)
    TextView orderState;
    @Bind(R.id.amount)
    TextView amount;
    @Bind(R.id.userNickName)
    TextView userNickName;
    @Bind(R.id.orderId)
    TextView orderId;
    @Bind(R.id.playType)
    TextView playType;

    @Bind(R.id.Layout_sure_odds)
    LinearLayout Layout_sure_odds;
    @Bind(R.id.Layout_submit_ticket)
    LinearLayout Layout_submit_ticket;
    @Bind(R.id.mGridRecyclerView)
    RecyclerView mGridRecyclerView;
    @Bind(R.id.Layout_SubListView)
    LinearLayout Layout_SubListView;
    @Bind(R.id.Layout_GridView)
    LinearLayout Layout_GridView;

    GridLayoutManager mGridLayoutManager;

    @Bind(R.id.mSubListView)
    SubListView mSubListView;
    @Bind(R.id.tv_ticktt_title)
    TextView tv_ticktt_title;
    @Bind(R.id.tv_ticktt_hint)
    TextView tv_ticktt_hint;

    TicketEntity mDatas;
    String lotteryTypeid;

    CompetitionDetailsSubListViewAdapter mSubListViewAdapter;

    OddsPresenter mOddsPresenter;

    private IDialog loaddialog;

    private List<ImageItem> imgs = new ArrayList<>();
    NineGridAdapter mNineGridAdapter;
    private static final int REQUEST_CODE = 1;

    @OnClick({R.id.Layout_sure_odds, R.id.Layout_submit_ticket})
    public void OnBtnClick(View v) {
        if (v.getId() == R.id.Layout_sure_odds) {
            showDialog();
        } else if (v.getId() == R.id.Layout_submit_ticket) {
            if (imgs.isEmpty()) {
                ToastUtil.ShortToast("请上传相关票据");
                return;
            }
            if (loaddialog != null) {
                loaddialog.dismiss();
            }
            Map<String, Object> maps = new HashMap<String, Object>();
            for (int i = 0; i < imgs.size(); i++) {
                if (imgs.get(i).isLocal()) {
                    File file = LubanUtils.Compress(this, imgs.get(i).getUrl());
                    maps.put(Constants.ImgArray[i], file);
                } else {
                    maps.put(Constants.ImgArray[i], imgs.get(i).getUrl());
                }
            }
            maps.put("uSessionId", SPUtil.get(this, Constants.key_uSessionId, "").toString());
            maps.put("lotteryTypeid", lotteryTypeid);
            maps.put("orderId", mDatas.getOrderId());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mOddsPresenter.putOrderPics(maps);
                }
            }).start();
        }
    }

    private void showDialog() {
        DialogBuilder.alertDialog(CompetitionTicketDetailsActivity.this).setMessage("赔率确认后不可更改，你是否继续")
                .setOnPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SureOrderOdds();
                    }
                }).create().show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_competition_ticket_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mOddsPresenter = new OddsPresenter(this);
        Intent intent = this.getIntent();
        mDatas = intent.getParcelableExtra("TicketEntity");
        lotteryTypeid = intent.getStringExtra("lotteryTypeid");
        initRecyclerView();
        loadData();
    }

    @Override
    public void initRecyclerView() {
        mSubListViewAdapter = new CompetitionDetailsSubListViewAdapter(this);
        mSubListView.setAdapter(mSubListViewAdapter);

        imgs = new ArrayList<>();
        if (!mDatas.getOrderPic().isEmpty()) {
            String[] strs = mDatas.getOrderPic().split(",|;");
            for (int i = 0, len = strs.length; i < len; i++) {
                ImageItem item = new ImageItem(strs[i]);
                item.setLocal(false);
                imgs.add(0, item);
            }
        }
        mNineGridAdapter = new NineGridAdapter(mGridRecyclerView);
        mGridLayoutManager = new GridLayoutManager(this, 4);
        mGridRecyclerView.setLayoutManager(mGridLayoutManager);
        mNineGridAdapter.setDatas(imgs);
        mNineGridAdapter.setOnItemImageViewClickListener(this);
        mNineGridAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if (imgs.size() == position) {
                    openCamera();
                } else {
                    LookBanners(position);
                }

            }
        });
        mGridRecyclerView.setAdapter(mNineGridAdapter);
    }

    @Override
    public void loadData() {
        lotteryName.setText(mDatas.getLotteryName());
        lotteryPerion.setText(mDatas.getLotteryPerion());
        serialNo.setText(mDatas.getSerialNo());
        orderState.setText(mDatas.getOrderState());
        amount.setText(mDatas.getAmount());
        userNickName.setText(mDatas.getUserNickName());
        orderId.setText(mDatas.getOrderId());
        playType.setText(mDatas.getPlayType() + (mDatas.getOrderTimes().equals("0") ? "" : "(倍数" + mDatas.getOrderTimes() + ")"));

        if (mDatas.getOrderState().equals("已确认赔率")) {
            Layout_sure_odds.setVisibility(View.GONE);
            Layout_submit_ticket.setVisibility(View.VISIBLE);
            Layout_SubListView.setVisibility(View.GONE);
            Layout_GridView.setVisibility(View.VISIBLE);
            tv_ticktt_title.setText("打票");
            tv_ticktt_hint.setText("(上传相关票据照片)");
        } else if (mDatas.getOrderState().equals("打票错误")) {
            tv_ticktt_title.setText("打票错误");
            tv_ticktt_hint.setText("(错误说明:" + mDatas.getOrderNote() + ")");
            Layout_sure_odds.setVisibility(View.VISIBLE);
            Layout_submit_ticket.setVisibility(View.GONE);
            Layout_SubListView.setVisibility(View.VISIBLE);
            Layout_GridView.setVisibility(View.GONE);
        } else {
            Layout_sure_odds.setVisibility(View.VISIBLE);
            Layout_submit_ticket.setVisibility(View.GONE);
            Layout_SubListView.setVisibility(View.VISIBLE);
            Layout_GridView.setVisibility(View.GONE);
        }
        Log.i("LOG", mDatas.getOrderDetailList());
        List<OrderDetailListEntity> ordeDetailList = FastJsonUtil.getBeanList(mDatas.getOrderDetailList(), OrderDetailListEntity.class);
        mSubListViewAdapter.setInfo(ordeDetailList);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("订单详情");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    //确认赔率
    private void SureOrderOdds() {
        if (loaddialog == null) {
            loaddialog = DialogBuilder.loadingDialog(this).show();
        }
        mOddsPresenter.sureOrderOdds(SPUtil.get(CompetitionTicketDetailsActivity.this, Constants.key_uSessionId, "").toString(),
                "1", mDatas.getOrderId(), "", "");
    }

    @Override
    public void ChengeOddsSuccessful(String msg) {
        //修改打票返回在这里没有返回
    }

    @Override
    public void SureOddsSuccessful(String msg) {
        if (loaddialog != null) {
            loaddialog.dismiss();
        }
        Layout_sure_odds.setVisibility(View.GONE);
        Layout_submit_ticket.setVisibility(View.VISIBLE);
        Layout_SubListView.setVisibility(View.GONE);
        Layout_GridView.setVisibility(View.VISIBLE);
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void SureFaild(String msg) {
        if (loaddialog != null) {
            loaddialog.dismiss();
        }
        ToastUtil.ShortToast(msg);
    }

    @Override
    public void ChengeFaild(String msg) {
        //修改打票返回在这里没有返回
    }

    @Override
    public void putOrderPicsSuccessful(String msg) {
        if (loaddialog != null) {
            loaddialog.dismiss();
        }
        ToastUtil.ShortToast(msg);
        finish();
    }

    @Override
    public void putOrderPicsFaild(String msg) {
        if (loaddialog != null) {
            loaddialog.dismiss();
        }
        ToastUtil.ShortToast(msg);
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        Album.camera(this)
                .image()
                .requestCode(REQUEST_CODE)
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(int requestCode, @NonNull String result) {
                        ImageItem item = new ImageItem(result);
                        item.setLocal(true);
                        imgs.add(0, item);
                        mNineGridAdapter.setDatas(imgs);
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
        GPreviewBuilder.from(this)
                .setData(imgs)
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
        for (int i = firstCompletelyVisiblePos; i < imgs.size(); i++) {
            View itemView = mGridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView.findViewById(R.id.item_img);
                thumbView.getGlobalVisibleRect(bounds);
            }
            imgs.get(i).setBounds(bounds);
        }
    }

    @Override
    public void deleItem(int position) {
        imgs.remove(position);
        mNineGridAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOddsPresenter.unSubscribe();
        imgs.clear();
    }
}
