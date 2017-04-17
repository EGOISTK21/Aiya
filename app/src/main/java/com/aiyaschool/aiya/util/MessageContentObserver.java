package com.aiyaschool.aiya.util;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EGOISTK21 on 2017/4/16.
 */

public class MessageContentObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;
    private String code;

    public MessageContentObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    /**
     * 回调函数, 当监听的Uri发生改变时，会回调该方法
     * 需要注意的是当收到短信的时候会回调两次
     * 收到短信一般来说都是执行了两次onchange方法.第一次一般都是raw的这个.
     * 虽然收到了短信.但是短信并没有写入到收件箱里
     */
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");  // 按时间顺序排序短信数据库
        if (c != null) {
            if (c.moveToFirst()) {
                String body = c.getString(c.getColumnIndex("body")); // 短信内容
                Pattern pattern = Pattern.compile("\\d{4}");//正则表达式匹配验证码
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    code = matcher.group(0);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = code;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.obj = "";
                    mHandler.sendMessage(msg);
                }
            }
            c.close();
        }
    }
}
