package com.aiyaschool.aiya.message.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class SDKUtils {
    public static String getSDPath() {
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            return Environment.getExternalStorageDirectory() + File.separator;
        }
        return null;
    }
}
