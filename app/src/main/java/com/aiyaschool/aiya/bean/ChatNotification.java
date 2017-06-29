package com.aiyaschool.aiya.bean;

import com.aiyaschool.aiya.util.NotificationUtil;
import com.aiyaschool.aiya.util.UserUtil;

/**
 * Created by EGOISTK21 on 2017/6/29.
 */

public class ChatNotification implements AiyaNotification {

    public ChatNotification(String content, int type) {
        UserUtil.addMsgTime(System.currentTimeMillis(), 0);
        UserUtil.addMsgPre(content, 0);
        NotificationUtil.show(ChatNotification.this, UserUtil.getTa().getUsername(), content, type);
    }
}
