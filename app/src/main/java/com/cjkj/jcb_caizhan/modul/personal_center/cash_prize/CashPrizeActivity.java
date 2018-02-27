package com.cjkj.jcb_caizhan.modul.personal_center.cash_prize;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人中心-委托兑奖
 */
public class CashPrizeActivity extends RxBaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.img_custom)
    ImageView img_custom;

    @Bind(R.id.mCustomGridView)
    GridView mCustomGridView;

    CustomGridAdapter mCustomGridAdapter;

    boolean isCustomOpen = false;

    @OnClick({R.id.imgback,R.id.Layout_custom})
    public void onBtnClick(View v){
        if(v.getId() == R.id.imgback){
            finish();
        }else if(v.getId() == R.id.Layout_custom){
            if(!isCustomOpen){
                openCustom();
            }else{
                closeCustom();
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_prize;
    }

    private void openCustom(){
        isCustomOpen = true;
        img_custom.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_selected_icon));
        mCustomGridView.setVisibility(View.VISIBLE);
    }

    private void closeCustom(){
        isCustomOpen = false;
        img_custom.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
        mCustomGridView.setVisibility(View.GONE);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mCustomGridAdapter = new CustomGridAdapter(this);
        mCustomGridView.setAdapter(mCustomGridAdapter);

        mCustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCustomGridAdapter.SelsectItem(i);
                toolbar_title.setText(mCustomGridAdapter.getSelectItem());
                closeCustom();
            }
        });
    }

    @Override
    public void initToolBar() {
        toolbar_title.setText("全部彩种");
    }

}
