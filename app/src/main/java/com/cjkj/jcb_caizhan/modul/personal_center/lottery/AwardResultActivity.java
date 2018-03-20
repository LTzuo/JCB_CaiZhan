package com.cjkj.jcb_caizhan.modul.Personal_Center.lottery;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.cjkj.jcb_caizhan.R;
import com.cjkj.jcb_caizhan.base.RxBaseActivity;
import com.cjkj.jcb_caizhan.core.Constants;
import com.cjkj.jcb_caizhan.modul.Personal_Center.lottery.lottery_thread.LotteryThreadListActivity;
import com.cjkj.jcb_caizhan.utils.IntentUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 个人中心-开奖结果
 */
public class AwardResultActivity extends RxBaseActivity {

//    @Bind(R.id.toolbar)
//    Toolbar mToolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.menu_custom)
    ImageView menu_custom;

    @Bind(R.id.mWebView)
    WebView mWebView;

    @OnClick({R.id.menu_custom,R.id.imgback})
    public void BtnClick(View v){
        if(v.getId() == R.id.menu_custom){
            IntentUtils.Goto(this,LotteryThreadListActivity.class);
        }else if(v.getId() == R.id.imgback){
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回前一个页面
            }else{
                finish();
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_award_result;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        WebSettings ws = mWebView.getSettings();
        // 是否允许脚本支持
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setSaveFormData(false);
        ws.setSavePassword(false);
        ws.setAppCacheEnabled(true);
        ws.setAppCacheMaxSize(1024);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setUseWideViewPort(true);
        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setDatabasePath(this.getApplicationContext().getCacheDir().getAbsolutePath());
        // 优先使用缓存
        // ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 自动加载图片
        // ws.setLoadsImagesAutomatically(true);
        // ws.setSupportZoom(false);
        // 是否允许缩放
        ws.setBuiltInZoomControls(false);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
        mWebView.loadUrl(Constants.AWARDRESULT_URL);
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Log.i(TAG, "-MyWebViewClient->shouldOverrideUrlLoading()--");
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // Log.i(TAG, "-MyWebViewClient->onPageStarted()--");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // Log.i(TAG, "-MyWebViewClient->onPageFinished()--");
            super.onPageFinished(view, url);
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
            // view.loadUrl("file:///android_asset/h.html");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回前一个页面
                return true;
            }else{
                finish();
            }
        }
        return false;
    }

    @Override
    public void initToolBar() {
//        mToolbar.setTitle("");
        toolbar_title.setText("开奖结果");
//        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
//        setSupportActionBar(mToolbar);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        mToolbar.setPopupTheme(R.style.ToolBarPopupThemeDay);
        menu_custom.setImageDrawable(getResources().getDrawable(R.drawable.zoushi));
        menu_custom.setVisibility(View.VISIBLE);
    }
}
