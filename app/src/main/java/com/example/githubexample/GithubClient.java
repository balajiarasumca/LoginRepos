package com.example.githubexample;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {

    private static final String GITURL_PRIMARY = "https://github.com/";
    private static final String GITURL_DIG = "https://api.github.com/";

    private static Retrofit getPrimaryClient()
    {
        return new Retrofit.Builder()
                .baseUrl(GITURL_PRIMARY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit getDigClient()
    {
        return new Retrofit.Builder()
                .baseUrl(GITURL_DIG)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static GithubService getApiServicePrimary
            ()
    {
        return getPrimaryClient().create(GithubService.class);
    }
    public static GithubService getGitService()
    {
        return getDigClient().create(GithubService.class);
    }
}
