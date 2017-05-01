package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.User;

/**
 * Created by EGOISTK21 on 2017/4/12.
 */

public class DBUtil {

    private static final String USERINFO = "UserInfo";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    private static final String USERNAME = "username";
    private static final String BIRTHDAY = "birthday";
    private static final String SCHOOL = "school";
    private static final String MAJOR = "major";
    private static final String SIGNNAME = "signname";
    private static final String HEIGHT = "height";
    private static final String HOMETOWN = "hometown";
    private static final String CONSTELLATION= "constellation";
    private static final String PHOTO = "photo";


    public DBUtil(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(USERINFO,Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

    }

    public void saveUser(User user) {
        mEditor.putString(USERNAME, user.getUsername());
        mEditor.putString("usersig", user.getUsersig());
        mEditor.putString("logintoken", user.getLogintoken());
        mEditor.putString("accesstoken", user.getAccessToken());
        mEditor.apply();
    }

    public void setUserName(String userName){
        mEditor.putString(USERNAME,userName);
        mEditor.apply();
    }
    public String getUserName() {
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(USERNAME, null);
        }
        return s;
    }

    public void setBirthday(String birthday){
        mEditor.putString(BIRTHDAY,birthday);
        mEditor.apply();
    }

    public String getBirthday(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(BIRTHDAY, null);
        }
        return s;
    }

    public void setSchool(String school){
        mEditor.putString(SCHOOL,school);
        mEditor.apply();
    }

    public String getSchool(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(SCHOOL, null);
        }
        return s;
    }

    public void setMajor(String major){
        mEditor.putString(MAJOR,major);
        mEditor.apply();
    }

    public String getMajor(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(MAJOR, null);
        }
        return s;
    }

    public void setSignName(String signName){
        mEditor.putString(SIGNNAME,signName);
        mEditor.apply();
    }

    public String getSignName(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(SIGNNAME, null);
        }
        return s;
    }

    public void setHeight(String height){
        mEditor.putString(HEIGHT,height);
        mEditor.apply();
    }

    public String getHeight(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(HEIGHT, null);
        }
        return s;
    }

    public void setHometown(String hometown){
        mEditor.putString(HOMETOWN,hometown);
        mEditor.apply();
    }

    public String getHometown(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(HOMETOWN, null);
        }
        return s;
    }


    public void setConstellation(String constellation){
        mEditor.putString(CONSTELLATION,constellation);
        mEditor.apply();
    }

    public String getConstellation(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(CONSTELLATION, null);
        }
        return s;
    }

    public void setPhoto(String photo){
        mEditor.putString(PHOTO,photo);
        mEditor.apply();
    }

    public String getPhoto(){
        String s = null;
        if (mSharedPreferences != null) {
            s = mSharedPreferences.getString(PHOTO, null);
        }
        return s;
    }


}
