package com.example.githubexample;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService
{
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> loginResponse(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("code")String code);

    @Headers("Accept: application/json")
    @GET("user")
    Call<ResponseBody> getUser2(@Query("access_token") String accessToken);

    @Headers("Accept: application/json")
    @GET("users/{user}/repos")
    Call<ResponseBody> getRepos(@Path("user") String username);

}
