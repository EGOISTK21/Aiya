package com.aiyaschool.aiya.util;

import java.util.regex.Pattern;

/**
 * Created by EGOISTK21 on 2017/4/14.
 */

public class SignUtil {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return Pattern.compile("^1\\d{10}$").matcher(phoneNumber).matches();
    }

    public static String formatPhoneNumber(CharSequence charSequence) {
        return "86-" + charSequence;
    }
}
