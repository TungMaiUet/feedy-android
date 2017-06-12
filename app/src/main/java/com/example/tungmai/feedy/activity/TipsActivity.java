package com.example.tungmai.feedy.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.api.ConnectSever;

/**
 * Created by TungMai on 5/19/2017.
 */

public class TipsActivity extends AppCompatActivity {

    public static final String INTENT_TIPS = "intent tips";
    private WebView webView;
    private String idTips;
    private Toolbar toolbar;

    ProgressDialog prDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        idTips = getIntent().getStringExtra(INTENT_TIPS);

        initViews();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Mẹo nấu ăn");


        webView = (WebView) findViewById(R.id.web_view);

        webView.setWebViewClient(new MyBrowser());
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.loadUrl("http://hinhnendepnhat.net/anh-dep-thien-nhien");
//        webView.loadUrl(ConnectSever.LINK_SERVER_GET_TIPS + idTips);
        webView.loadUrl("http://192.168.0.103:3000/tips/gettips/" + idTips);
//        webView.loadDataWithBaseURL(ConnectSever.LINK_SERVER_GET_TIPS + idTips, null, "text/html", "utf-8", null);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prDialog = new ProgressDialog(TipsActivity.this);
            prDialog.setMessage("Please wait ...");
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (prDialog != null) {
                prDialog.dismiss();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
