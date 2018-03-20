package com.cjkj.jcb_caizhan.modul.Personal_Center.documentary;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.AbsRecyclerViewAdapter;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.modul.Personal_Center.documentary.details.DocDetailsActivity;
import com.cjkj.jcb_caizhan.utils.IntentUtils;
import butterknife.Bind;

/**
 * 个人中心跟单方案查询
 */
public class DocumentaryActivity extends RxBaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    DocumentaryAdapter mDocumentaryAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_documentary;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mDocumentaryAdapter = new DocumentaryAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDocumentaryAdapter);

        mDocumentaryAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                IntentUtils.Goto(DocumentaryActivity.this, DocDetailsActivity.class);
            }
        });
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("");
        toolbar_title.setText("跟单方案查询");
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
    }

}
