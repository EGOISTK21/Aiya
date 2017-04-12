package com.aiyaschool.aiya.message.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by ShootHzj on 2016/11/2.
 */

public class ScreenUtils {
    private static ScreenUtils instance = null;
    /* 供外界调用的实例方法 这里用到了同步*/

    public static synchronized ScreenUtils getInstance() {
        if(null == instance) {
            instance = new ScreenUtils();
        }
        return instance;
    }

    public int[] getScreenHeight(Context context){
        int[] wh = new int[2];
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        wh[0]=width;
        wh[1]=height;
        return wh;
    }
}

