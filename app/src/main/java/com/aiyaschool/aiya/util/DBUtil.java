package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.Avatar;
import com.aiyaschool.aiya.bean.ImgWall;
import com.aiyaschool.aiya.bean.UpLoad;
import com.aiyaschool.aiya.bean.User;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class DBUtil {

    private static SharedPreferences sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
    private static SharedPreferences.Editor sEditor = sSharedPreferences.edit();
    private static User sUser = new User();

    private DBUtil() {

    }

    static void setUser(User user) {
        setTempToken(user.getTempToken());
        setLoginToken(user.getLoginToken());
        setUserSig(user.getUsersig());
        setAccessToken(user.getAccessToken());
        setUpLoad(user.getUpload());
        setPhone(user.getPhone());
        setId(user.getId());
        setUsername(user.getUsername());
        setProfile(user.getProfile());
        setLoveId(user.getLoveId());
        setGroup(user.getGroup());
        setSchool(user.getSchool());
        setHomeTown(user.getProvince());
        setPoints(user.getPoints());
        setGiftTickets(user.getGiftTickets());
        setAvatar(user.getAvatar());
        setImageWall(user.getImgwall());
    }

    static User getUser() {
        return sUser;
    }

    static void setTempToken(String tempToken) {
        if (tempToken != null && !sUser.getTempToken().equals(tempToken)) {
            sUser.setTempToken(tempToken);
            sEditor.putString("temptoken", tempToken);
            sEditor.apply();
        }
    }

    static String getTempToken() {
        return sSharedPreferences.getString("temptoken", "");
    }

    static void clearTempToken() {
        sUser.setTempToken(null);
        sEditor.remove("temptoken");
        sEditor.apply();
    }

    static void setLoginToken(String loginToken) {
        if (loginToken != null && !sUser.getLoginToken().equals(loginToken)) {
            sUser.setLoginToken(loginToken);
            sEditor.putString("loginToken", loginToken);
            sEditor.apply();
        }
    }

    static String getLoginToken() {
        return sSharedPreferences.getString("logintoken", "");
    }

    static void clearLoginToken() {
        sUser.setLoginToken(null);
        sEditor.remove("logintoken");
        sEditor.apply();
    }

    static void setAccessToken(String accessToken) {
        if (accessToken != null && !sUser.getAccessToken().equals(accessToken)) {
            sUser.setAccessToken(accessToken);
            sEditor.putString("accesstoken", "");
            sEditor.apply();
        }
    }

    static String getAccessToken() {
        return sSharedPreferences.getString("accesstoken", "");
    }

    static void setUserSig(String userSig) {
        if (userSig != null && !sUser.getUsersig().equals(userSig)) {
            sUser.setUsersig(userSig);
            sEditor.putString("usersig", userSig);
            sEditor.apply();
        }
    }

    static String getUserSig() {
        return sSharedPreferences.getString("usersig", "");
    }

    static void setPhone(String phone) {
        if (phone != null && !sUser.getPhone().equals(phone)) {
            sUser.setPhone(phone);
            sEditor.putString("phone", phone);
            sEditor.apply();
        }
    }

    static String getPhone() {
        return sSharedPreferences.getString("phone", "");
    }

    static void setUpLoad(UpLoad upLoad) {
        if (upLoad != null) {
            sEditor.putString("upurl", upLoad.getUpurl());
            sEditor.putString("imgname", upLoad.getImgname());
            sEditor.apply();
        }
    }

    static UpLoad getUpLoad() {
        return new UpLoad(sSharedPreferences.getString("upurl", ""), sSharedPreferences.getString("upurl", ""));
    }

    static void clearUpLoad() {
        sEditor.remove("upurl");
        sEditor.remove("upurl");
        sEditor.apply();
    }

    static void setId(String id) {
        if (id != null && !sUser.getId().equals(id)) {
            sUser.setId(id);
            sEditor.putString("id", id);
            sEditor.apply();
        }
    }

    static String getId() {
        return sSharedPreferences.getString("id", "");
    }

    static void setUsername(String username) {
        if (username != null && !sUser.getUsername().equals(username)) {
            sUser.setUsername(username);
            sEditor.putString("username", username);
            sEditor.apply();
        }
    }

    static String getUsername() {
        return sSharedPreferences.getString("username", "");
    }

    static void setLoveId(String loveId) {
        if (loveId != null && !sUser.getLoveId().equals(loveId)) {
            sUser.setLoveId(loveId);
            sEditor.putString("loveid", loveId);
            sEditor.apply();
        }
    }

    static String getLoveId() {
        return sSharedPreferences.getString("loveid", "");
    }

    static void setGroup(String group) {
        if (group != null && !sUser.getGroup().equals(group)) {
            sUser.setGroup(group);
            sEditor.putString("group", group);
            sEditor.apply();
        }
    }

    static String getGroup() {
        return sSharedPreferences.getString("group", "");
    }

    static void setSchool(String school) {
        if (school != null && !sUser.getSchool().equals(school)) {
            sUser.setSchool(school);
            sEditor.putString("school", "");
            sEditor.apply();
        }
    }

    static String getSchool() {
        return sSharedPreferences.getString("school", "");
    }

    static void setHomeTown(String homeTown) {
        if (homeTown != null && !sUser.getProvince().equals(homeTown)) {
            sUser.setProvince(homeTown);
            sEditor.putString("hometown", homeTown);
            sEditor.apply();
        }
    }

    static String getHomeTown() {
        return sSharedPreferences.getString("hometown", "");
    }

    static void setAvatar(Avatar avatar) {
        if (avatar != null) {
//            sEditor.putString("normal", avatar.getNormal());
//            sEditor.putString("thumb", avatar.getThumb());
            sEditor.apply();
        }
    }

    static String getAvatar() {
        return sSharedPreferences.getString("avatar", "");
    }

    static void setProfile(String profile) {
        if (profile != null && !sUser.getProfile().equals(profile)) {
            sUser.setProfile(profile);
            sEditor.putString("profile", profile);
            sEditor.apply();
        }
    }

    static String getProfile() {
        return sSharedPreferences.getString("profile", "");
    }

    static void setPoints(String points) {
        if (points != null && !sUser.getPoints().equals(points)) {
            sUser.setPoints(points);
            sEditor.putString("points", points);
            sEditor.apply();
        }
    }

    static String getPoints() {
        return sSharedPreferences.getString("points", "");
    }

    static void setGiftTickets(String giftTickets) {
        if (giftTickets != null && !sUser.getGiftTickets().equals(giftTickets)) {
            sUser.setGiftTickets(giftTickets);
            sEditor.putString("gifttickets", giftTickets);
            sEditor.apply();
        }
    }

    static String getGiftTickets() {
        return sSharedPreferences.getString("gifttickets", "");
    }

    static void setImageWall(ImgWall imgWall) {
        if (imgWall != null) {
            //sEditor.putString("imagewall", imgWall.getUrl());
            sEditor.apply();
        }
    }

    static String getImageWall() {
        return sSharedPreferences.getString("imagewall", "");
    }

    public static void clearAll() {
        sUser = new User();
        sEditor.clear();
        sEditor.apply();
    }
}
