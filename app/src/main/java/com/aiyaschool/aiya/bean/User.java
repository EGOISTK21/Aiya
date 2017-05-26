package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户实体类
 * Created by EGOISTK21 on 2017/4/26.
 */

public class User implements Parcelable {
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
    private String id;
    private String username;
    private String phone;
    private String school;
    private String loveid;
    private String group;
    private String sex;
    private String age;
    private String height;
    private String character;
    private String constellation;
    private String hobby;
    private Avatar avatar;
    private String profile;
    private String gifttickets;
    private String fateswitch;
    private ImgWall imgwall;
    private long startdate;

    public User() {

    }

    protected User(Parcel in) {
        temptoken = in.readString();
        logintoken = in.readString();
        usersig = in.readString();
        accesstoken = in.readString();
        upload = in.readParcelable(UpLoad.class.getClassLoader());
        id = in.readString();
        username = in.readString();
        phone = in.readString();
        school = in.readString();
        loveid = in.readString();
        group = in.readString();
        sex = in.readString();
        age = in.readString();
        height = in.readString();
        character = in.readString();
        constellation = in.readString();
        hobby = in.readString();
        avatar = in.readParcelable(Avatar.class.getClassLoader());
        profile = in.readString();
        gifttickets = in.readString();
        fateswitch = in.readString();
        imgwall = in.readParcelable(ImgWall.class.getClassLoader());
        startdate = in.readLong();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTempToken() {
        return temptoken;
    }

    public String getLoginToken() {
        return logintoken;
    }

    public void setLoginToken(String loginToken) {
        this.logintoken = loginToken;
    }

    public String getUserSig() {
        return usersig;
    }

    public void setUserSig(String userSig) {
        this.usersig = userSig;
    }

    public String getAccessToken() {
        return accesstoken == null ? "false" : accesstoken;
    }

    public void setAccessToken(String AccessToken) {
        this.accesstoken = AccessToken;
    }

    public UpLoad getUpLoad() {
        return upload;
    }

    public void setUpLoad(UpLoad upLoad) {
        this.upload = upLoad;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public boolean isMatched() {
        return !"0".equals(loveid);
    }

    public String getLoveId() {
        return loveid;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
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

    public String getGiftTickets() {
        return gifttickets;
    }

    public void setGiftTickets(String giftTickets) {
        this.gifttickets = giftTickets;
    }

    public String getFateSwitch() {
        return fateswitch;
    }

    public boolean isFated() {
        return !"0".equals(fateswitch);
    }

    public void setFateSwitch(String fateSwitch) {
        this.fateswitch = fateSwitch;
    }

    public ImgWall getImgWall() {
        return imgwall;
    }

    public void setImgWall(ImgWall imgWall) {
        this.imgwall = imgWall;
    }

    public long getStartdate() {
        return startdate;
    }

    public void setStartdate(long startdate) {
        this.startdate = startdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(temptoken);
        dest.writeString(logintoken);
        dest.writeString(usersig);
        dest.writeString(accesstoken);
        dest.writeParcelable(upload, flags);
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(phone);
        dest.writeString(school);
        dest.writeString(loveid);
        dest.writeString(group);
        dest.writeString(sex);
        dest.writeString(age);
        dest.writeString(height);
        dest.writeString(character);
        dest.writeString(constellation);
        dest.writeString(hobby);
        dest.writeParcelable(avatar, flags);
        dest.writeString(profile);
        dest.writeString(gifttickets);
        dest.writeString(fateswitch);
        dest.writeParcelable(imgwall, flags);
        dest.writeLong(startdate);
    }

    @Override
    public String toString() {
        return "User{" +
                "temptoken='" + temptoken + '\'' +
                ", logintoken='" + logintoken + '\'' +
                ", usersig='" + usersig + '\'' +
                ", accesstoken='" + accesstoken + '\'' +
                ", upload=" + upload +
                ", id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", school='" + school + '\'' +
                ", loveid='" + loveid + '\'' +
                ", group='" + group + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", character='" + character + '\'' +
                ", constellation='" + constellation + '\'' +
                ", hobby='" + hobby + '\'' +
                ", avatar=" + avatar +
                ", profile='" + profile + '\'' +
                ", gifttickets='" + gifttickets + '\'' +
                ", fateswitch='" + fateswitch + '\'' +
                ", imgwall=" + imgwall +
                ", startdate='" + startdate + '\'' +
                '}';
    }
}
