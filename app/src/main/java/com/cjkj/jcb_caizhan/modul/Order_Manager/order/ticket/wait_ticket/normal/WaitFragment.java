package com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.wait_ticket.normal;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxLazyFragment;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketActivity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketContract;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketEntity;
import com.cjkj.jcb_caizhan.modul.Order_Manager.order.ticket.TicketPressenter;
import com.cjkj.jcb_caizhan.network.ApiConstants;
import com.cjkj.jcb_caizhan.utils.SPUtil;
import com.cjkj.jcb_caizhan.utils.ToastUtil;
import com.cjkj.jcb_caizhan.widget.NineGridView.ImageItem;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 本期待打票
 * Created by 1 on 2018/2/23.
 */
public class WaitFragment extends RxLazyFragment implements TicketContract.ITicketView, WaitExAdapter.OnChildSubmitBtnClick {

    @Bind(R.id.mExpandableListView)
    ExpandableListView mExpandableListView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @Bind(R.id.loading)
    LoadingLayout loading;

    TicketPressenter mPressenter;

    WaitExAdapter mAdapter;

    String lotteryTypeid;

    int index = 1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ssq_wait_ticket;
    }

    public static WaitFragment newIntance() {
        return new WaitFragment();
    }

    @Override
    public void finishCreateView(Bundle state) {
        lotteryTypeid = ((TicketActivity) getActivity()).getLotteryTypeid();
        mPressenter = new TicketPressenter(this);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initstatusManagerLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initstatusManagerLayout() {
        loading.setStatus(LoadingLayout.Loading);
    }

    @Override
    protected void initRecyclerView() {
        mAdapter = new WaitExAdapter(getContext());
        mExpandableListView.setAdapter(mAdapter);
        mAdapter.setOnSubmitBtnClickListener(this);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mExpandableListView.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < count; j++) {
                    if (j != groupPosition) {
                        mExpandableListView.collapseGroup(j);
                    }
                }
            }
        });
        mRefreshLayout.autoRefresh();
        mRefreshLayout.autoLoadMore();
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(false).setColorSchemeColors(getResources()
                .getColor(R.color.colorPrimary)));//Material风格
        //mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()).setDrawableSize(20));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                index = 1;
                loadData();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                index++;
                loadData();
            }
        });
    }

    @Override
    protected void loadData() {
        if (!SPUtil.get(getContext(), Constants.key_uSessionId, "").toString().isEmpty()) {
            mPressenter.getCurrentOrders(SPUtil.get(getContext(), Constants.key_uSessionId, "").toString(),
                    lotteryTypeid, index, "0");
        }
    }

    @Override
    protected void finishTask() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index == 1)
                    mRefreshLayout.finishRefresh(true);
                else
                    mRefreshLayout.finishLoadMore();
            }
        }, 1 * 1000);
    }

    @Override
    public void Sussesful(List<TicketEntity> orderList) {
        if (index == 1) {
            if (orderList.isEmpty()) {
                loading.setStatus(LoadingLayout.Empty);
            } else {
                loading.setStatus(LoadingLayout.Success);
            }
            mAdapter.setInfo(orderList);
        } else {
            if (orderList.isEmpty()) {
                ToastUtil.ShortToast("没有更多数据了");
            }
            mAdapter.addInfo(orderList);
        }
        finishTask();
    }

    @Override
    public void ShowFail(String msg) {
        ToastUtil.ShortToast(msg);
        //  loading.setStatus(LoadingLayout.Loading);
        finishTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPressenter.unSubscribe();
    }

    @Override
    public void onChildSubmitBtnClick(String orderId, ArrayList<ImageItem> Imgs) {
        Map<String, Object> maps = new HashMap<String, Object>();
        // if(mImageMap.get(groupPosition).getImgs().size() == 1){
        File file = new File(Imgs.get(0).getUrl());
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        maps.put("orderPic1", file);
//        @Query("uSessionId") String uSessionId,
//        @Query("lotteryTypeid") String lotteryTypeid,
//        @Query("orderId") String orderId,
        maps.put("uSessionId", SPUtil.get(getContext(), Constants.key_uSessionId, "").toString());
        maps.put("lotteryTypeid", lotteryTypeid);
        maps.put("orderId", orderId);

        //  mPressenter.putOrderPics(maps);

        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadImgAndParameter(maps);
            }
        }).start();

    }


    private final static void uploadImgAndParameter(Map<String, Object> map) {
        OkHttpClient okHttpClient = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof File) {
                        File f = (File) entry.getValue();
                        builder.addFormDataPart(entry.getKey(), f.getName(), RequestBody.create(MediaType.parse("image/*"), f));
                    } else {
                        builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
                    }
                }

            }
        }
        //创建RequestBody
        RequestBody body = builder.build();

//        MultipartBody requestBody = builder.build();
        //构建Request请求
        final Request request = new Request.Builder()
                .url(ApiConstants.URL_BASE + "putOrderPics?")//地址
                .post(body)//添加请求体
//                .post(requestBody)//添加请求体
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            ToastUtil.ShortToast(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}