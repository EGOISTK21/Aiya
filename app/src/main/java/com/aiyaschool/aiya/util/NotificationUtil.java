package com.aiyaschool.aiya.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.message.bean.HitNotification;

/**
 * Created by EGOISTK21 on 2017/6/1.
 */

public class NotificationUtil {

    private static NotificationCompat.Builder sBuilder = new NotificationCompat.Builder(MyApplication.getInstance());
    private static NotificationManager notificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

    private NotificationUtil() {

    }

    public static void show(HitNotification hitNotification, String title, String text, int id) {
        // 设置通知的点击行为：这里启动一个 Activity
        Bundle bundle = new Bundle();
        bundle.putInt("card_flag", 2);
        bundle.putString("requestid", hitNotification.getRequestid());
        bundle.putString("fromuserid", hitNotification.getFromuserid());
        bundle.putParcelable("other detail", hitNotification.getUser());
        Intent intent = new Intent(MyApplication.getInstance(), OtherDetailActivity.class).putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationManager.notify(id,
                sBuilder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .build()
        );
    }
}
