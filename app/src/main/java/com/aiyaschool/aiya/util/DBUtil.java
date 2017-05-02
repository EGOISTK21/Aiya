package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class DBUtil {

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;

    private DBUtil() {

    }

    public static void setPhone(String phone) {
        if (sSharedPreferences == null) {
            sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        sEditor.putString("phone", phone);
        sEditor.apply();
    }

    public static void setLoginToken(String loginToken) {
        if (sSharedPreferences == null) {
            sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        sEditor.putString("loginToken", loginToken);
        sEditor.apply();
    }

    public static String getPhone() {
        if (sSharedPreferences == null) {
            sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        return sSharedPreferences.getString("phone", null);
    }

    public static String getLoginToken() {
        if (sSharedPreferences == null) {
            sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        return sSharedPreferences.getString("loginToken", null);
    }

    public static void clearLoginToken() {
        if (sSharedPreferences == null) {
            sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        sEditor.remove("loginToken");
        sEditor.apply();
    }

    public static void clearAll() {
        if (sSharedPreferences == null) {
            sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        sEditor.clear();
        sEditor.apply();
    }
}
