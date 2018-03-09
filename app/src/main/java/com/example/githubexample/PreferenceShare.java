package com.example.githubexample;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceShare
{
    private static final String PREF_HEADER="github";
    private static final String PREF_ACCESS_TOKEN="token";

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEdit;

    public PreferenceShare(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(PREF_HEADER,Context.MODE_PRIVATE);
        prefEdit=preferences.edit();
    }

    public void putAccess(String accessToken)
    {
        prefEdit.putString(PREF_ACCESS_TOKEN,accessToken);
        prefEdit.commit();
    }

    public String getAccess()
    {
        return preferences.getString(PREF_ACCESS_TOKEN,null);
    }

    public void Logout()
    {
        prefEdit.putString(PREF_ACCESS_TOKEN,"");
        prefEdit.commit();
    }
}
