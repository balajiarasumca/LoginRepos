package com.example.githubexample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepActivity extends AppCompatActivity {

    TextView txt_userName;
    ImageView img_profPic;
    RecyclerView git_list_view;
    ImageView img_Logout;

    ArrayList<WrapperGit> gitList;
    GitReposAdapter gitAdapter;
    PreferenceShare preferenceShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep);

        gitList=new ArrayList<>();
        preferenceShare=new PreferenceShare(this);

        String accessToken=preferenceShare.getAccess();

        txt_userName= (TextView) findViewById(R.id.txt_user_name);
        img_profPic= (ImageView) findViewById(R.id.img_profile_pic);
        git_list_view= (RecyclerView) findViewById(R.id.rec_git_list);
        img_Logout = (ImageView)findViewById(R.id.img_logout);
        img_Logout.setColorFilter(Color.parseColor("#C62828"));

        txt_userName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/helvetica.ttf"));

        git_list_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        gitAdapter=new GitReposAdapter(this,gitList);
        git_list_view.setAdapter(gitAdapter);

        reqUserData(accessToken);

        img_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferenceShare.Logout();
                startActivity(new Intent(RepActivity.this,Login.class));
                finish();

            }
        });

    }

    public void reqUserData(String token)
    {
        GithubService service=GithubClient.getGitService();
        final Call<ResponseBody> reqAccessToken=service.getUser2(token);
        reqAccessToken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject=convertToJson(response.body().byteStream());
                try
                {
                    String userName=jsonObject.getString("login");
                    String avatar_url=jsonObject.getString("avatar_url");
                    txt_userName.setText(userName);
                    Picasso.with(RepActivity.this).load(avatar_url).into(img_profPic);
                    reqUserRepos(userName);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void reqUserRepos(String username)
    {
        GithubService service=GithubClient.getGitService();
        Call<ResponseBody> getRep=service.getRepos(username);
        getRep.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONArray object=convertToJsonArray(response.body().byteStream());
                for(int i=0;i<object.length();i++)
                {
                    try {
                        JSONObject object1=object.getJSONObject(i);
                        String title=object1.getString("name");
                        String htmlUrl=object1.getString("html_url");
                        String description=object1.getString("description");
                        gitList.add(new WrapperGit(title,htmlUrl,description));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                gitAdapter=new GitReposAdapter(RepActivity.this,gitList);
                git_list_view.setAdapter(gitAdapter);
                gitAdapter.notifyDataSetChanged();

                gitAdapter.setListener(new GitReposAdapter.Onclick() {
                    @Override
                    public void onclick(int position) {

                        String url = gitList.get(position).getRepo_url();
                        String repname = gitList.get(position).getTitle();

                        Intent intent_url = new Intent(RepActivity.this,WebActivity.class);
                        intent_url.putExtra("URL",url);
                        intent_url.putExtra("REPNAME",repname);
                        startActivity(intent_url);

                    }
                });
             }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public JSONObject convertToJson(InputStream stream)
    {
        String line=null;
        StringBuilder sb=new StringBuilder();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stream));
        try {
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("check_git_responser",sb.toString());
        try {

                return new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray convertToJsonArray(InputStream stream)
    {
        String line=null;
        StringBuilder sb=new StringBuilder();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(stream));
        try {
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("check_git_responser",sb.toString());
        try {

            return new JSONArray(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
