package com.aiyaschool.aiya.util;

import java.util.regex.Pattern;

/**
 * Created by EGOISTK21 on 2017/4/14.
 */

public class SignUtil {

    private SignUtil() {
    }

    public static boolean isValidPhoneNumber(CharSequence mobile) {
        return Pattern.compile("^1\\d{10}$").matcher(mobile).matches();
    }

    public static boolean isValidCode(CharSequence code) {
        return Pattern.compile("\\d{4}$").matcher(code).matches();
    }

    public static boolean isValidNick(CharSequence nick) {
        return Pattern.compile("[\\u4E00-\\u9FA5A-Za-z0-9_]{1,9}").matcher(nick).matches();
    }

}
