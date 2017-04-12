package com.aiyaschool.aiya.message.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class Utils {

    public static boolean isSDExist(Context context) {
        String SDState = Environment.getExternalStorageState();
        if (!SDState.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
