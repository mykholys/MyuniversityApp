package com.mykholy.myuniversity.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SPreferences {
    protected final String TOKEN = "token";
    protected final String LOGIN = "login";
    protected final String USERNAME = "userName";
    protected final String PHONE = "phone";
    protected final String LANGUAGE = "language";

    private Context context;
    private SharedPreferences sp;

    public SPreferences(Context context){
        this.context = context;
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }


    //------token------
    public void setToken(String token){
        sp.edit().putString(TOKEN, token).apply();
    }
    public String getToken(){
        return sp.getString(TOKEN, "Nan");
    }
    //-----------------



    //------login------
    public void setLogIn(boolean login){
        sp.edit().putBoolean(LOGIN, login).apply();
    }
    public boolean isLoggedIn(){
        return sp.getBoolean(LOGIN, false);
    }
    //-----------------



    //------username------
    public void setUserName(String userName){
        sp.edit().putString(USERNAME, userName).apply();
    }
    public String getUserName(){
        return sp.getString(USERNAME, "Nan");
    }
    //-----------------



    //------phone------
    public void setPhone(String phone){
        sp.edit().putString(PHONE, phone).apply();
    }
    public String getPhone(){
        return sp.getString(PHONE, "Nan");
    }
    //-----------------

    //------language------
    public void setLanguage(String language){
        sp.edit().putString(LANGUAGE, language).apply();
    }
    public String getLanguage(){
        return sp.getString(LANGUAGE, "en");
    }
    //-----------------

}
