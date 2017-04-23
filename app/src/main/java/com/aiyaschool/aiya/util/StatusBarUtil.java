package com.aiyaschool.aiya.util;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.aiyaschool.aiya.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 为API20及以下的设备设置状态栏颜色
 * Created by EGOISTK21 on 2017/4/8.
 */

public class StatusBarUtil {

    private StatusBarUtil() {
    }

    public static void init(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }
}
