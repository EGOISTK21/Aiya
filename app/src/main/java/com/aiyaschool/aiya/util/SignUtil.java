package com.aiyaschool.aiya.util;

import java.util.regex.Pattern;

/**
 * Created by EGOISTK21 on 2017/4/14.
 */

public class SignUtil {

    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        return Pattern.compile("^1\\d{10}$").matcher(phoneNumber).matches();
    }

    public static boolean isValidCode(CharSequence code) {
        return Pattern.compile("\\d{4}$").matcher(code).matches();
    }

    public static String formatPhoneNumber(CharSequence phoneNumber) {
        return "86-" + phoneNumber;
    }
}
