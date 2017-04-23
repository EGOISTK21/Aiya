package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/3/26.
 */

public class User {

    private int state;
    private String temptoken;
    private String username;
    private String phone;
    private String AccessToken;
    private String school;
    private String loveid;
    private String group;
    private String province;
    private Avatar avatar;
    private String profile;
    private String points;
    private String gifttickets;
    private Imgwall imgwall;
    private String usersig;
    private String logintoken;

    public User(String phone) {
        this.phone = phone;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setTemptoken(String temptoken) {
        this.temptoken = temptoken;
    }

    public String getTemptoken() {
        return temptoken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public String getAccessToken() {
        return this.AccessToken;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return this.school;
    }

    public void setLoveid(String loveid) {
        this.loveid = loveid;
    }

    public String getLoveid() {
        return this.loveid;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return this.province;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return this.avatar;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPoints() {
        return this.points;
    }

    public void setGifttickets(String gifttickets) {
        this.gifttickets = gifttickets;
    }

    public String getGifttickets() {
        return this.gifttickets;
    }

    public void setImgwall(Imgwall imgwall) {
        this.imgwall = imgwall;
    }

    public Imgwall getImgwall() {
        return this.imgwall;
    }

    public void setUsersig(String usersig) {
        this.usersig = usersig;
    }

    public String getUsersig() {
        return this.usersig;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }

    public String getLogintoken() {
        return this.logintoken;
    }

    @Override
    public String toString() {
        return "User{" +
                "state=" + state +
                ", temptoken='" + temptoken + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", AccessToken='" + AccessToken + '\'' +
                ", school='" + school + '\'' +
                ", loveid='" + loveid + '\'' +
                ", group='" + group + '\'' +
                ", province='" + province + '\'' +
                ", avatar=" + avatar +
                ", profile='" + profile + '\'' +
                ", points='" + points + '\'' +
                ", gifttickets='" + gifttickets + '\'' +
                ", imgwall=" + imgwall +
                ", usersig='" + usersig + '\'' +
                ", logintoken='" + logintoken + '\'' +
                '}';
    }

}

class Avatar {

    private Normal normal;
    private Thumb thumb;

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    public Normal getNormal() {
        return this.normal;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public Thumb getThumb() {
        return this.thumb;
    }

}

class Normal {

    private String face;
    private String background;

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return this.face;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackground() {
        return this.background;
    }

}

class Thumb {

    private String face;
    private String background;

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return this.face;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackground() {
        return this.background;
    }

}

class Imgwall {

    private int rows;
    private String url;

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return this.rows;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

}
