package com.aiyaschool.aiya.util;

import android.widget.Toast;

import com.aiyaschool.aiya.MyApplication;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class ToastUtil {

    private static Toast sToast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);

    private ToastUtil() {

    }

    public static void show(String s) {
        sToast.setText(s);
        sToast.show();
    }

    public static void cancel() {
        sToast.cancel();
    }

}
