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
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.bean.AiyaNotification;
import com.aiyaschool.aiya.bean.HitNotification;
import com.aiyaschool.aiya.bean.ReplyNotification;

/**
 * Created by EGOISTK21 on 2017/6/1.
 */

public class NotificationUtil {

    private static NotificationCompat.Builder sBuilder = new NotificationCompat.Builder(MyApplication.getInstance());
    private static NotificationManager notificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    private static Bundle sBundle;
    private static Intent sIntent;
    private static PendingIntent sPendingIntent;

    private NotificationUtil() {

    }

    public static void show(AiyaNotification notification, String title, String text, int id) {
        sBundle = new Bundle();
        switch (id) {
            case 111:
                sBundle.putInt("card_flag", 2);
                sBundle.putString("requestid", ((HitNotification) notification).getRequestid());
                sBundle.putString("fromuserid", ((HitNotification) notification).getFromuserid());
                sBundle.putParcelable("other detail", ((HitNotification) notification).getUser());
                break;
            case 222:
                sBundle.putInt("card_flag", 3);
                sBundle.putParcelable("other detail", ((ReplyNotification) notification).getUser());
                break;
            case 333:
                break;
            case 444:
                sIntent = new Intent(MyApplication.getInstance(), MainActivity.class);
                break;
        }
        if (id != 444) {
            sIntent = new Intent(MyApplication.getInstance(), OtherDetailActivity.class).putExtras(sBundle);
        }
        notificationManager.notify(id,
                sBuilder.setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(PendingIntent.getActivity(MyApplication.getInstance(), 0, sIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                        .build()
        );
    }
}
