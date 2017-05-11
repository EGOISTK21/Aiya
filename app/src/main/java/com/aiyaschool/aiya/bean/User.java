package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/4/26.
 */

public class User {

    /**
     * temptoken : f29c1317158d76821e8d11ccd8a007a358fc9984c178e
     * username : xihuan
     * phone : 15000000000
     * accesstoken : 48f418d380c1cf2ae948732780f6a39815000000000590494641c6de
     * school : 南京大学
     * loveid : 1
     * group : 1
     * province : 陕西
     * avatar : {"normal":{"face":"http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756","background":"http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756"},"thumb":{"face":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756","background":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756"}}
     * profile : 亨利就坐在那里，满眼望去，都是自己二十岁的影子
     * points : 200
     * gifttickets : 0
     * imgwall : {"rows":0,"url":null}
     * usersig : eJxNjV1PgzAYRv9Lb2dcCy0LJl4AY4A6EzLddtdUeCF1ArUUlBj-uw3B6O05z8cXeno4XIui6IbWcDMpQDcIo6sZyxJaIysJ2sJXofW0CKGULLkw3NXlv3xfXvisLCMUY0wYxu4i4VNJDVxUZp4jjDHHRhY7gu5l11rhYOIRTAj*k0Y2ME-6tsF8x--9k7XF*-g5yvJom95Dr8ZT9eHs3U36GJ5fgnW5CldTUt-R0BspOQRBErP3PKthRzu-b1Rz2ubFdFmnO*qF3ZCwWMTDJi*OEZEZHt6gqm-R9w-MC1Zg
     * logintoken : 03b0761c6c915924907bb9ebabdebad3160b4f30-1f89-11e7-967b-ebe175c2a0263b946bfe-d0fb-4357-aa30-42cdaef6dc50
     */

    private String temptoken;
    private String logintoken;
    private String usersig;
    private String accesstoken;
    private UpLoad upload;
    private String phone;
    private String id;
    private String username;
    private String profile;
    private String loveid;
    private String group;
    private String school;
    private String province;
    private String points;
    private String gifttickets;
    private Avatar avatar;
    private ImgWall imgwall;


    public String getTempToken() {
        return temptoken;
    }

    public void setTempToken(String tempToken) {
        this.temptoken = tempToken;
    }

    public UpLoad getUpload() {
        return upload;
    }

    public void setUpload(UpLoad upload) {
        this.upload = upload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessToken() {
        return accesstoken == null ? "false" : accesstoken;
    }

    public void setAccessToken(String AccessToken) {
        this.accesstoken = AccessToken;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLoveId() {
        return loveid;
    }

    public boolean isMatched() {
        return !loveid.equals("0");
    }

    public void setLoveId(String loveId) {
        this.loveid = loveId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getGiftTickets() {
        return gifttickets;
    }

    public void setGiftTickets(String giftTickets) {
        this.gifttickets = giftTickets;
    }

    public ImgWall getImgwall() {
        return imgwall;
    }

    public void setImgwall(ImgWall imgwall) {
        this.imgwall = imgwall;
    }

    public String getUsersig() {
        return usersig;
    }

    public void setUsersig(String userSig) {
        this.usersig = userSig;
    }

    public String getLoginToken() {
        return logintoken;
    }

    public void setLoginToken(String loginToken) {
        this.logintoken = loginToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "temptoken='" + temptoken + '\'' +
                ", upload=" + upload +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", accesstoken='" + accesstoken + '\'' +
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
