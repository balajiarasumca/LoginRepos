package com.example.githubexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authentication extends AppCompatActivity {

    WebView webView_authentication;
    LinearLayout ll_webProgress;

    PreferenceShare preferenceShare;

    String authenticateUrl;

    String clientId="eab64db6c26eb7ce12b0";
    String clientSecret="239388e15cf49ef655ede33e28c1cb846693d4b7";
    String redirectUrl="example://callback";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        preferenceShare=new PreferenceShare(Authentication.this);
        authenticateUrl="https://github.com/login/oauth/authorize"+"?client_id="+clientId+"&scope=user,public_repo&redirect_uri="+redirectUrl;

        webView_authentication= (WebView) findViewById(R.id.web_authentication);
        ll_webProgress = (LinearLayout) findViewById(R.id.ll_web_progress);

        clearCookies();

        webView_authentication.setWebViewClient(new AuthClient());
        webView_authentication.loadUrl(authenticateUrl);

    }

    private class AuthClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            ll_webProgress.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            ll_webProgress.setVisibility(View.GONE);

            if(url.contains("?code="))
            {
                String code = url.substring(url.lastIndexOf("?code=") + 1);
                String[] token_code = code.split("=");
                String tokenFetchedIs = token_code[1];
                String[] cleanToken = tokenFetchedIs.split("&");

                reqAccessToken(cleanToken[0]);
            }

        }
    }

    private void clearCookies() {

        CookieManager cookieManager = CookieManager.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {

                @Override
                public void onReceiveValue(Boolean aBoolean) {

                }
            });
        } else {
            cookieManager.removeAllCookie();
        }
    }

    public void reqAccessToken(String code)
    {
        final GithubService service=GithubClient.getApiServicePrimary();
        Call<AccessToken> reqAccessToken=service.loginResponse(clientId,clientSecret,code);

        reqAccessToken.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response)
            {
                String accesstoken = response.body().getAccesstoken();
                preferenceShare.putAccess(accesstoken);

                Intent intent_repo=new Intent(Authentication.this,RepActivity.class);
                startActivity(intent_repo);
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }
}
