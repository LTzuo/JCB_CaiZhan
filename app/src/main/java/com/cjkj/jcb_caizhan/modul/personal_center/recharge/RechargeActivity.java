package com.cjkj.jcb_caizhan.modul.Personal_Center.recharge;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.widget.CheckBox.SmoothCheckBox;
import com.dilusense.customkeyboard.KeyboardNumber;
import com.dilusense.customkeyboard.KeyboardUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import loic.teillard.colortextview.ColorTextView;

/**
 * 个人中心-充值
 */
public class RechargeActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

//    @Bind(R.id.layout_colortv)
//    ViewGroup layout_colortv;
//    @Bind(R.id.textview1)
//    ColorTextView textView1;

    @Bind(R.id.SingleChoiceRecyclerView)
    RecyclerView mSingleChoiceListView;

    @Bind(R.id.edit)
    EditText editText;

    SingleChoiceAdapter mAdapter;

    List<SingleChoiceBean> mDatas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mDatas.add(new SingleChoiceBean(R.drawable.rechare_wx, "微信支付", true));
        mDatas.add(new SingleChoiceBean(R.drawable.rechare_zfb, "支付宝支付", false));
        mAdapter = new SingleChoiceAdapter(mSingleChoiceListView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mSingleChoiceListView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mSingleChoiceListView.setLayoutManager(linearLayoutManager);
        mSingleChoiceListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                mAdapter.ChengeItem(position);
            }
        });

        KeyboardNumber  keyboardIdentity = new KeyboardNumber(this);
        KeyboardUtils.bindEditTextEvent(keyboardIdentity, editText);

//        textView1.setText("●为防止信用卡套现的行为，本次充值的金额在15天后可提现充值金额的70%，剩余的30%可进行派奖使用，请谨慎充值。");
//        textView1.setTextArrColor("●为防止信用卡套现的行为，",getResources().getColor(R.color.black_alpha_60));
//        textView1.setTextArrColor("本次充值的金额在15天后可提现充值金额的70%，剩余的30%可进行派奖使用，请谨慎充值。",getResources().getColor(R.color.colorPrimary));
//        ColorTextView colorTextView = new ColorTextView(this);
//        colorTextView.addTextColor("●为防止信用卡套现的行为，",getResources().getColor(R.color.black_alpha_60));
//        colorTextView.addTextColor("本次充值的金额在15天后可提现充值金额的70%，剩余的30%可进行派奖使用，请谨慎充值。", getResources().getColor(R.color.colorPrimary));
//        colorTextView.setSpaces(true);
//        colorTextView.apply();
//        layout_colortv.addView(colorTextView);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");// 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar_title.setText("充值");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

    //单选列表适配器
    class SingleChoiceAdapter extends AbsRecyclerViewAdapter {

        public SingleChoiceAdapter(RecyclerView mrecyclerview) {
            super(mrecyclerview);
        }

        public void ChengeItem(int position){
            if(mDatas.get(position).isClick()){
                mDatas.get(position).setClick(false);
            }else{
                for(SingleChoiceBean bean : mDatas){
                  bean.setClick(false);
                }
                mDatas.get(position).setClick(true);
            }
            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(ClickableViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.item_icon.setImageResource(mDatas.get(position).getUrl());
                itemViewHolder.mItemText.setText(mDatas.get(position).getTitle());
                if(mDatas.get(position).isClick()){
                    itemViewHolder.item_choice.setChecked(true,true);
                }else{
                    itemViewHolder.item_choice.setChecked(false,true);
                }
//                itemViewHolder.item_choice.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
//                        ChengeItem(position);
//                    }
//                });
            }
            super.onBindViewHolder(holder, position);
        }

        @Override
        public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            bindContext(parent.getContext());
            return new ItemViewHolder(
                    LayoutInflater.from(getContext()).inflate(R.layout.item_recharge, null, false));
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        private class ItemViewHolder extends ClickableViewHolder {

            ImageView item_icon;
            TextView mItemText;
            SmoothCheckBox item_choice;
            public ItemViewHolder(View itemView) {
                super(itemView);
                item_icon = $(R.id.item_icon);
                mItemText = $(R.id.item_title);
                item_choice = $(R.id.item_choice);
            }
        }
    }

}
