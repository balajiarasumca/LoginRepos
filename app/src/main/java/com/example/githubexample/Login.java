package com.example.githubexample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Login extends AppCompatActivity
{
    Button btn_login;
    ImageView  img_Git_Logo;
    PreferenceShare preferenceShare;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceShare=new PreferenceShare(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        img_Git_Logo = (ImageView) findViewById(R.id.img_git_logo);
        btn_login.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/helvetica.ttf"));
        img_Git_Logo.setColorFilter(Color.WHITE);

        String accessToken=preferenceShare.getAccess();
        if(!TextUtils.isEmpty(accessToken))
        {
            Intent intent_repo=new Intent(Login.this,RepActivity.class);
            startActivity(intent_repo);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 reqLoginPage();
            }
        });
    }

    public void reqLoginPage()
    {
        Intent intent=new Intent(Login.this,Authentication.class);
        startActivity(intent);

    }
 }
