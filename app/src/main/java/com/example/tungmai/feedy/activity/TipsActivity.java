package com.example.tungmai.feedy.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;

/**
 * Created by TungMai on 5/19/2017.
 */

public class TipsActivity extends AppCompatActivity {

    public static final String INTENT_TIPS = "intent tips";
    private WebView webView;
    private String idTips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        idTips = getIntent().getStringExtra(INTENT_TIPS);

        initViews();
    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.web_view);

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(ConnectSever.LINK_SERVER_GET_TIPS + idTips);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
