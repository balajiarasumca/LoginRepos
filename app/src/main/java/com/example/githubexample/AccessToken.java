package com.example.githubexample;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName("access_token")
    private String accesstoken;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccesstoken() {
        return accesstoken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
