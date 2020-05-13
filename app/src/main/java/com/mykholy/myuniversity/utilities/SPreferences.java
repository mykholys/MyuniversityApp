package com.mykholy.myuniversity.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SPreferences {
    private final String TOKEN = "token";
    private final String TYPE_TOKEN = "type_token";
    private final String REFRESH_TOKEN = "refresh_token";
    private final String LOGIN = "login";
    private final String USERNAME = "userName";
    private final String PHONE = "phone";
    private final String LANGUAGE = "language";
    private final String NOTIFICATION_COUNT = "notification_count";

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
        return sp.getString(STUDENT_IMAGE, "avatar.png");

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
        sp.edit().remove(TOKEN).apply();
        sp.edit().remove(TYPE_TOKEN).apply();
        sp.edit().remove(REFRESH_TOKEN).apply();
        sp.edit().remove(LOGIN).apply();
        sp.edit().remove(USERNAME).apply();
        sp.edit().remove(LANGUAGE).apply();
        sp.edit().remove(NOTIFICATION_COUNT).apply();
        sp.edit().remove(STUDENT_ID).apply();
        sp.edit().remove(STUDENT_NAME).apply();
        sp.edit().remove(STUDENT_IMAGE).apply();
        sp.edit().remove(STUDENT_ACADEMIC_YEAR).apply();
        sp.edit().remove(STUDENT_DEPT_ID).apply();
        sp.edit().remove(STUDENT_DEPT_NAME).apply();


    }


}
