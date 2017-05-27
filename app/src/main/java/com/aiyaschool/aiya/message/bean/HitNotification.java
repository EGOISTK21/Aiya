package com.aiyaschool.aiya.message.bean;

import com.aiyaschool.aiya.bean.Avatar;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class HitNotification {

    private Avatar mAvatar;
    private String mUsername;
    private String mSchool;
    private String requestid;
    private String fromuserid;

    public HitNotification(String requestid, String fromuserid) {
        this.requestid = requestid;
        this.fromuserid = fromuserid;
    }
}
