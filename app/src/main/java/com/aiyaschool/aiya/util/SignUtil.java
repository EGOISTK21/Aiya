package com.aiyaschool.aiya.util;

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

    public static void setPhone(String phone) {
        if (isValidPhone(phone)) {
            DBUtil.setPhone(phone);
        }
    }

    public static String getPhone() {
        return DBUtil.getPhone();
    }

    public static void setTempToken(String tempToken) {
        DBUtil.setTempToken(tempToken);
    }

    public static String getTempToken() {
        return DBUtil.getTempToken();
    }

    public static void clearTempToken() {
        DBUtil.clearTempToken();
    }

    public static void setLoginToken(String loginToken) {
        DBUtil.setLoginToken(loginToken);
    }

    public static String getLoginToken() {
        return DBUtil.getLoginToken();
    }

    public static void clearLoginToken() {
        DBUtil.clearLoginToken();
    }

}
