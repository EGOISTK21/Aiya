package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.UpLoad;

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

    static void setUpLoad(UpLoad upLoad) {
        if (upLoad != null) {
            sEditor.putString("upurl", upLoad.getUpurl());
            sEditor.putString("imgname", upLoad.getImgname());
            sEditor.apply();
        }
    }

    static UpLoad getUpLoad() {
        return new UpLoad(sSharedPreferences.getString("upurl", ""), sSharedPreferences.getString("upurl", ""));
    }

    static void clearUpLoad() {
        sEditor.remove("upurl");
        sEditor.remove("upurl");
        sEditor.apply();
    }

    static void setLoginToken(String loginToken) {
        if (loginToken != null) {
            sEditor.putString("loginToken", loginToken);
            sEditor.apply();
        }
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
