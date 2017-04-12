package com.aiyaschool.aiya.message.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class ImageUtil {
    public static String takePic(Activity activity, int requestCode) {
        if (!Utils.isSDExist(activity)) {
            return null;
        }
        File dir = new File(Environment.getExternalStorageDirectory() + "/ShootHzj/Pic");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String photoPath = Environment.getExternalStorageDirectory() + "/ShootHzj/Pic/H." + System.currentTimeMillis() + ".jpg";
        try {
            File photoFile = new File(photoPath);
            // 将File对象转换为Uri并启动照相程序
            Uri imageUri = Uri.fromFile(photoFile);
            // "android.media.action.IMAGE_CAPTURE"
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 照相
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 指定图片输出地址
            activity.startActivityForResult(intent, requestCode); // 启动照相
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoPath;

    }

    public static boolean saveBitmapToFile(Bitmap bitmap, String path) {
        if (bitmap == null) {
            return false;
        }
        BufferedOutputStream os = null;
        boolean save = false;
        try {
            File file = FileUtils.openOrCreateFile(path, true);
            os = new BufferedOutputStream(new FileOutputStream(file));
            save = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
        } catch (Exception e) {
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
            }
        }
        return save;
    }

}
