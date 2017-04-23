package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.User;

/**
 * Created by EGOISTK21 on 2017/4/12.
 */

public class DBUtil {

    private static SharedPreferences preferences;

    private DBUtil() {
    }

    public static void saveUser(User user) {
        preferences = MyApplication.getInstance()
                .getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", user.getUsername());
        editor.putString("usersig", user.getUsersig());
        editor.putString("logintoken", user.getLoginToken());
        editor.putString("accesstoken", user.getAccesstoken());
        editor.commit();
        //editor.apply();
    }

    public static String getUserName() {
        String s = null;
        if (preferences != null) {
            s = preferences.getString("username", null);
        }
        return s;
    }
}
