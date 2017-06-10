package com.aiyaschool.aiya.bean;

/**
 * Created by wewarriors on 2017/5/13.
 * 访客记录中访客的信息
 */

import com.aiyaschool.aiya.me.bean.MyAvatar;

/**
 *  "data": [
 {
 "ouinfo": { //对方信息
 "username": "janie",
 "avatar": {
 "normal": [
 "http://cdn.sinacloud.net/gxwy-user/avatar/face1.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=5DvWaYp8Uo&Expires=1493751318"
 ],
 "thumb": [
 "http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face1.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=s1BJZSV8QS&Expires=1493751318"
 ]
 },
 "school": "清华大学"
 }
 },
 */
public class OuInfo {
    private String username;
    private MyAvatar avatar;
    private String school;
    private String requestid;
    private String userid;
    private String createtime;
    private String status;

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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
