package com.aiyaschool.aiya.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by EGOISTK21 on 2017/4/19.
 */

public class ToastUtil {

    private static Toast toast = null;

    private ToastUtil() {

    }

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.show();
    }

}
