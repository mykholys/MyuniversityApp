package com.mykholy.myuniversity.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SPreferences {
    protected final String TOKEN = "token";
    protected final String TYPE_TOKEN = "type_token";
    protected final String REFRESH_TOKEN = "refresh_token";
    protected final String LOGIN = "login";
    protected final String USERNAME = "userName";
    protected final String PHONE = "phone";
    protected final String LANGUAGE = "language";
    protected final String NOTIFICATION_COUNT = "notification_count";

    //student
    private final String STUDENT_ID = "student_id";
    private final String STUDENT_NAME = "student_name";
    private final String STUDENT_IMAGE = "student_image";
    private final String STUDENT_ACADEMIC_YEAR = "student_academic_year";
    private final String STUDENT_DEPT_ID = "student_dept_id";
    private final String STUDENT_DEPT_NAME = "student_dept_name";


    private Context context;
    private SharedPreferences sp;

    public SPreferences(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }


    //------token------
    public void setToken(String token) {
        sp.edit().putString(TOKEN, token).apply();
    }

    public String getToken() {
        return sp.getString(TOKEN, "Nan");
    }

    //-----------------
    //------type token------
    public void setType_Token(String type_token) {
        sp.edit().putString(TYPE_TOKEN, type_token).apply();
    }

    public String getType_Token() {
        return sp.getString(TYPE_TOKEN, "Nan");
    }
    //-----------------

    //------ Refresh token------
    public void setRefreshToken(String refresh) {
        sp.edit().putString(REFRESH_TOKEN, refresh).apply();
    }

    public String getRefreshToken() {
        return sp.getString(REFRESH_TOKEN, "Nan");
    }
    //-----------------


    //------login------
    public void setLogIn(boolean login) {
        sp.edit().putBoolean(LOGIN, login).apply();
    }

    public boolean isLoggedIn() {
        return sp.getBoolean(LOGIN, false);
    }
    //-----------------


    //------username------
    public void setUserName(String userName) {
        sp.edit().putString(USERNAME, userName).apply();
    }

    public String getUserName() {
        return sp.getString(USERNAME, "Nan");
    }
    //-----------------


    //student


    public int getSTUDENT_ID() {
        return sp.getInt(STUDENT_ID, 0);

    }

    public void setSTUDENT_ID(int student_id) {
        sp.edit().putInt(STUDENT_ID, student_id).apply();

    }

    public String getSTUDENT_NAME() {
        return sp.getString(STUDENT_NAME, "Nan");

    }

    public void setSTUDENT_NAME(String student_name) {
        sp.edit().putString(STUDENT_NAME, student_name).apply();
    }

    public String getSTUDENT_IMAGE() {
        return sp.getString(STUDENT_IMAGE, "Nan");

    }

    public void setSTUDENT_IMAGE(String student_image) {
        sp.edit().putString(STUDENT_IMAGE, student_image).apply();

    }

    public int getSTUDENT_ACADEMIC_YEAR() {
        return sp.getInt(STUDENT_ACADEMIC_YEAR, 0);

    }

    public void setSTUDENT_ACADEMIC_YEAR(int academic_year) {
        sp.edit().putInt(STUDENT_ACADEMIC_YEAR, academic_year).apply();

    }

    public int getSTUDENT_DEPT_ID() {
        return sp.getInt(STUDENT_DEPT_ID, 0);

    }

    public void setSTUDENT_DEPT_ID(int dept_id) {
        sp.edit().putInt(STUDENT_DEPT_ID, dept_id).apply();

    }

    public String getSTUDENT_DEPT_NAME() {
        return sp.getString(STUDENT_DEPT_NAME, "Nan");

    }

    public void setSTUDENT_DEPT_NAME(String dept_name) {
        sp.edit().putString(STUDENT_DEPT_NAME, dept_name).apply();

    }

    //------language------
    public void setLanguage(String language) {
        sp.edit().putString(LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return sp.getString(LANGUAGE, "en");
    }
    //-----------------

    //------notification------
    public void setNotificationCount(int notificationCount) {
        sp.edit().putInt(NOTIFICATION_COUNT, notificationCount).apply();
    }

    public int getNotificationCount() {
        return sp.getInt(NOTIFICATION_COUNT, 0);
    }

    //-----------------
    public void setClearAll() {
        sp.edit().clear().apply();
    }


}
