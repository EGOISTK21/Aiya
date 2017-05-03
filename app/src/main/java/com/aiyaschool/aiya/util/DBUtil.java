package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class DBUtil {

    private static SharedPreferences sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
    private static SharedPreferences.Editor sEditor = sSharedPreferences.edit();

    private DBUtil() {

    }

    static void setPhone(String phone) {
        sEditor.putString("phone", phone);
        sEditor.apply();
    }

    static String getPhone() {
        return sSharedPreferences.getString("phone", "");
    }

    static void setTempToken(String tempToken) {
        sEditor.putString("tempToken", tempToken);
        sEditor.apply();
    }

    static String getTempToken() {
        return sSharedPreferences.getString("tempToken", "");
    }

    static void clearTempToken() {
        sEditor.remove("tempToken");
        sEditor.apply();
    }

    static void setLoginToken(String loginToken) {
        sEditor.putString("loginToken", loginToken);
        sEditor.apply();
    }

    static String getLoginToken() {
        return sSharedPreferences.getString("loginToken", "");
    }

    static void clearLoginToken() {
        sEditor.remove("loginToken");
        sEditor.apply();
    }

    public static void clearAll() {
        sEditor.clear();
        sEditor.apply();
    }
}
