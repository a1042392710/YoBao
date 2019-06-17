package com.jjz.energy.base;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjz.energy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Features:
 * @author: create by chenhao on 2019/3/28
 */
public class BaseWebActivity extends BaseActivity {

    @BindView(R.id.ll_toolbar_left)
    LinearLayout llToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.webView)
    WebView webView;
    /**
     * 标题
     */
    private String title;
    /**
     * 网页url
     */
    private String url;

    public static final String WEB_TITLE = "title";
    public static final String WEB_URL = "url";

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int layoutId() {
        return R.layout.act_base_web;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra(WEB_TITLE);
        url = getIntent().getStringExtra(WEB_URL);
        tvToolbarTitle.setText(title);
        webView.loadUrl(url);
        WebSettings settings = webView.getSettings();
        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                //等待证书响应
//                handler.proceed();
//            }
//        });
    }




    @OnClick({R.id.ll_toolbar_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_toolbar_left:
                finish();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
