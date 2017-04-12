package com.aiyaschool.aiya.message.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class DialogUtil {


    //请稍候progressDialog
    public static MaterialDialog buildProgressDialog(Context context) {
        return new MaterialDialog.Builder(context)
                .content("请等待")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .build();
    }
}

