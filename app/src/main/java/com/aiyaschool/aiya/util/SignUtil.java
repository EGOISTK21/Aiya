package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.Intent;

import com.aiyaschool.aiya.activity.sign.SignActivity;

import java.util.regex.Pattern;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class SignUtil {

    private SignUtil() {
    }

    public static boolean isValidPhone(CharSequence phone) {
        return null != phone && Pattern.compile("^1\\d{10}$").matcher(phone).matches();
    }

    public static boolean isValidVerification(CharSequence verification) {
        return null != verification && Pattern.compile("\\d{4}$").matcher(verification).matches();
    }

    public static boolean isValidUsername(CharSequence username) {
        return null != username && Pattern.compile("[\\u4E00-\\u9FA5A-Za-z0-9_]{1,9}").matcher(username).matches();
    }

    public static void setPhone(String phone) {
        if (isValidPhone(phone)) {
            UserUtil.setPhone(phone);
        }
    }

    public static String getPhone() {
        return UserUtil.getUser().getPhone();
    }

    public static String getLoginToken() {
        return UserUtil.getUser().getLoginToken();
    }

    public static void clearLoginToken() {
        UserUtil.clearLoginToken();
    }

    public static void addAccessToken() {
        APIUtil.addAccessToken();
    }

    public static void removeAccessToken() {
        APIUtil.removeAccessToken();
    }

    public static void signOut(Context context) {
        SignUtil.clearLoginToken();
        SignUtil.removeAccessToken();
        context.startActivity(new Intent(context, SignActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}
