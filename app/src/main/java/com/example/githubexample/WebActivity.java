package com.example.githubexample;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    TextView txt_rep_title;
    ImageView imgBack;

    String url;
    String reptitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        url = getIntent().getStringExtra("URL");
        reptitle = getIntent().getStringExtra("REPNAME");

        webView = (WebView) findViewById(R.id.wv_Url);
        imgBack = (ImageView) findViewById(R.id.btnBack);
        txt_rep_title = (TextView)findViewById(R.id.txtRepoTittle);

        txt_rep_title.setText(reptitle);
        txt_rep_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf"));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }
    }
}
