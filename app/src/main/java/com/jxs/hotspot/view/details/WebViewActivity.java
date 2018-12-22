package com.jxs.hotspot.view.details;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jxs.hotspot.R;
import com.jxs.hotspot.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/jxs/webView")
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.wv_content)
    WebView mWvContent;

    @Autowired
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }
    private void initView() {
        showBackButton();
        setTitleText("资讯详情");
        showTitleLine();
    }

    private void initData() {
        mWvContent.loadUrl(url);
    }

    public void initListener() {
        WebSettings settings = mWvContent.getSettings();
        settings.setJavaScriptEnabled(true);

        mWvContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPbLoading.setVisibility(View.GONE);
            }
        });

        mWvContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPbLoading.setProgress(newProgress);
            }
        });

        mWvContent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWvContent.canGoBack()) {
                        mWvContent.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
