package com.aiyaschool.aiya.message.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ShootHzj on 2016/11/26.
 */

public class FileUtils {
    public static File openOrCreateFile(String filePath, boolean isForce) throws IOException {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
        if (!mkDirs(dir)) {
            return null;
        }
        if (!createFile(isForce, filePath)) {
            return null;
        }
        return new File(filePath);
    }
    public static boolean mkDirs(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            return dirFile.mkdirs();
        }
        return true;
    }
    public static boolean createFile(boolean isForce, String filePath) {
        File file = new File(filePath);
        try {
            if (file.exists() && isForce) {
                file.delete();
            }
            if (!file.exists()) {
                return file.createNewFile();
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
