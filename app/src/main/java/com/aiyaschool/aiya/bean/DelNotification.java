package com.aiyaschool.aiya.bean;

import com.aiyaschool.aiya.util.NotificationUtil;
import com.aiyaschool.aiya.util.UserUtil;

/**
 * Created by EGOISTK21 on 2017/6/3.
 */

public class DelNotification implements AiyaNotification {

    public DelNotification() {
        initFromUser();
    }

    private void initFromUser() {
        NotificationUtil.show(DelNotification.this, "你被别人甩了", UserUtil.getTa().getUsername(), 444);
        UserUtil.addMsgTime(System.currentTimeMillis(), 0);
        UserUtil.addMsgPre(UserUtil.getTa().getUsername(), 0);
        UserUtil.delLove();
    }
}
