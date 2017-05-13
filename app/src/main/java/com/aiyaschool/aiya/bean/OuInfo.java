package com.aiyaschool.aiya.bean;

/**
 * Created by wewarriors on 2017/5/13.
 * 访客记录中访客的信息
 */

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
    private String avatar;
    private String normal;
    private String thumb;
    private String school;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }


    @Override
    public String toString() {
        return "OuInfo{" +
                "username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", normal='" + normal + '\'' +
                ", thumb='" + thumb + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
