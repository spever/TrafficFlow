package com.subzero.trafficflow.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.LogUtil;

/**
 * Created by hui on 2016/3/14.
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private WebView id_webview;
    private ImageView iv_back;

    @Override
    protected void initView() {

        setContentView(R.layout.activity_about_us);
        id_webview = (WebView) findViewById(R.id.id_webview);
        iv_back = (ImageView) findViewById(R.id.id_iv_title_back);
        iv_back.setOnClickListener(this);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {

        id_webview.getSettings().setJavaScriptEnabled(true);
        id_webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        id_webview.loadUrl(UrlConstants.ABOUT_US);

        id_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else if(url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                return true;
            }
        });



    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_iv_title_back) {
            finish();
        }
    }



}
