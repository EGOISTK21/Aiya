package com.aiyaschool.aiya.bean;

import com.aiyaschool.aiya.me.bean.MyAvatar;

/**
 * Created by wewarriors on 2017/5/13.
 */

public class EmotionRecordBean {
    private String username;
    private MyAvatar avatar;
    private String school;
    private String userid;
    private String starttime;
    private String endtime;
    private String intimacy;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MyAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(MyAvatar avatar) {
        this.avatar = avatar;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(String intimacy) {
        this.intimacy = intimacy;
    }
}
