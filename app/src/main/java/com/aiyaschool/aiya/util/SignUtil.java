package com.aiyaschool.aiya.util;

import com.aiyaschool.aiya.bean.UpLoad;
import com.aiyaschool.aiya.bean.User;

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

    public static void setUser(User user) {
        DBUtil.setUser(user);
    }

    public static User getUser() {
        return DBUtil.getUser();
    }

    public static void setPhone(String phone) {
        if (isValidPhone(phone)) {
            DBUtil.setPhone(phone);
        }
    }

    public static String getPhone() {
        return DBUtil.getPhone();
    }

    public static UpLoad getUpLoad() {
        return DBUtil.getUpLoad();
    }

    public static void clearUpLoad() {
        DBUtil.clearUpLoad();
    }

    public static String getLoginToken() {
        return DBUtil.getLoginToken();
    }

    public static void clearLoginToken() {
        DBUtil.clearLoginToken();
    }

    public static void addAccessToken() {
        APIUtil.addAccessToken();
    }

    public static void removeAccessToken() {
        APIUtil.removeAccessToken();
    }

}
