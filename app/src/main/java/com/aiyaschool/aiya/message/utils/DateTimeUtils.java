package com.aiyaschool.aiya.message.utils;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class DateTimeUtils {
    public static String setChatTime(long when) {
        return formatTimeNew(when, false, true);
    }
    public static String formatTimeNew(long when, boolean isFull, boolean showTime) {
        if (isFull) {
            return timeStamp2Date(when, 7);
        } else {
            int TodayDiff = getTodayDiff(when);
            if (showTime) {
                if (TodayDiff == 0) {
                    return timeStamp2Date(when, 3);
                } else if (TodayDiff == 1) {
                    return "昨天" + " " + timeStamp2Date(when, 3);
                } else if (TodayDiff == 2) {
                    return "前天" + " " + timeStamp2Date(when, 3);
                } else {
                    return timeStamp2Date(when, 8);
                }
            } else {
                if (TodayDiff == 0) {
                    return timeStamp2Date(when, 3);
                } else if (TodayDiff == 1) {
                    return "昨天";
                } else if (TodayDiff == 2) {
                    return "前天";
                } else {
                    return timeStamp2Date(when, 9);
                }
            }
        }
    }

    /**
     * @return 0为 相等  1为 昨天  依次类推
     */
    public static int getTodayDiff(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        if ((thenYear == time.year) && (thenMonth == time.month)) {
            return time.monthDay - thenMonthDay;
        }
        return -1;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param type
     * @return
     */
    public static String timeStamp2Date(long seconds, int type) {
        String s = seconds + "";
        if (s.length() < 11)
            seconds = seconds * 1000;
        String format = null;
        switch (type) {
            case 1:
                format = "yyyy-MM-dd\nHH:mm:ss";
                break;
            case 2:
                format = "HH:mm:ss";
                break;
            case 3:
                format = "HH:mm";
                break;
            case 4:
                format = "yyyy/MM/dd";
                break;
            case 5:
                format = "MM-dd HH:mm";
                break;
            case 6:
                format = "mm:ss";
                break;
            case 7:
                format = "yyyy/MM/dd HH:mm:ss";
                break;
            case 8:
                format = "MM/dd HH:mm";
                break;
            case 9:
                format = "MM/dd";
                break;
            case 10:
                format = "MM-dd";
                break;
            case 11:
                format = "yyyyMMddHHmmssS";//毫秒为 3位
                break;
            case 12:
                format = "yyyyMMddHHmmsss";//毫秒为 1位
                break;
            default:
                format = "yyyy-MM-dd HH:mm:ss";
                break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(seconds));
    }
}
