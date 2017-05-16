package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.bean.Avatar;
import com.aiyaschool.aiya.bean.ImgWall;
import com.aiyaschool.aiya.bean.Normal;
import com.aiyaschool.aiya.bean.Thumb;
import com.aiyaschool.aiya.bean.UpLoad;
import com.aiyaschool.aiya.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class UserUtil {

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;
    private static User sUser;

    private UserUtil() {

    }

    public static void init(Context context) {
        sSharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
        sUser = new User();
        sUser.setLoginToken(UserUtil.getLoginToken());
        sUser.setUserSig(UserUtil.getUserSig());
        sUser.setAccessToken(UserUtil.getAccessToken());
        sUser.setUpLoad(UserUtil.getUpLoad());
        sUser.setId(UserUtil.getId());
        sUser.setUsername(UserUtil.getUsername());
        sUser.setPhone(UserUtil.getPhone());
        sUser.setSchool(UserUtil.getSchool());
        sUser.setLoveId(UserUtil.getLoveId());
        sUser.setGroup(UserUtil.getGroup());
        sUser.setCharacter(UserUtil.getCharacter());
        sUser.setAvatar(UserUtil.getAvatar());
        sUser.setProfile(UserUtil.getProfile());
        sUser.setPoints(UserUtil.getPoints());
        sUser.setGiftTickets(UserUtil.getGiftTickets());
        sUser.setFateSwitch(UserUtil.getFateSwitch());
        sUser.setImgWall(UserUtil.getImgWall());
    }

    static void setUser(User user) {
        setLoginToken(user.getLoginToken());
        setUserSig(user.getUserSig());
        setAccessToken(user.getAccessToken());
        setUpLoad(user.getUpLoad());
        setId(user.getId());
        setUsername(user.getUsername());
        setPhone(user.getPhone());
        setSchool(user.getSchool());
        setLoveId(user.getLoveId());
        setGroup(user.getGroup());
        setCharacter(user.getCharacter());
        setAvatar(user.getAvatar());
        setProfile(user.getProfile());
        setPoints(user.getPoints());
        setGiftTickets(user.getGiftTickets());
        setFateSwitch(user.getFateSwitch());
        setImgWall(user.getImgWall());
    }

    static User getUser() {
        return sUser;
    }

    public static void setContactShield(boolean contactShield) {
        sEditor.putBoolean("contactShield", contactShield);
        sEditor.apply();
    }

    public static boolean getContactShield() {
        return sSharedPreferences.getBoolean("contactShield", true);
    }

    static void setLoginToken(String loginToken) {
        if (loginToken != null && !loginToken.equals(sUser.getLoginToken())) {
            sUser.setLoginToken(loginToken);
            sEditor.putString("logintoken", loginToken);
            sEditor.apply();
        }
    }

    static String getLoginToken() {
        return sSharedPreferences.getString("logintoken", null);
    }

    static void clearLoginToken() {
        sUser.setLoginToken(null);
        sEditor.remove("logintoken");
        sEditor.apply();
    }

    static void setAccessToken(String accessToken) {
        if (accessToken != null && !accessToken.equals(sUser.getAccessToken())) {
            sUser.setAccessToken(accessToken);
            sEditor.putString("accesstoken", "");
            sEditor.apply();
        }
    }

    static String getAccessToken() {
        return sSharedPreferences.getString("accesstoken", null);
    }

    static void setUserSig(String userSig) {
        if (userSig != null && !userSig.equals(sUser.getUserSig())) {
            sUser.setUserSig(userSig);
            sEditor.putString("usersig", userSig);
            sEditor.apply();
        }
    }

    static String getUserSig() {
        return sSharedPreferences.getString("usersig", null);
    }

    static void setPhone(String phone) {
        if (phone != null && !phone.equals(sUser.getPhone())) {
            sUser.setPhone(phone);
            sEditor.putString("phone", phone);
            sEditor.apply();
        }
    }

    static String getPhone() {
        return sSharedPreferences.getString("phone", null);
    }

    static void setUpLoad(UpLoad upLoad) {
        if (upLoad != null && !upLoad.equals(sUser.getUpLoad())) {
            sUser.setUpLoad(upLoad);
            sEditor.putString("upurl", upLoad.getUpurl());
            sEditor.putString("imgname", upLoad.getImgname());
            sEditor.apply();
        }
    }

    static UpLoad getUpLoad() {
        return new UpLoad(sSharedPreferences.getString("upurl", null), sSharedPreferences.getString("upurl", null));
    }

    static void clearUpLoad() {
        sEditor.remove("upurl");
        sEditor.remove("imgname");
        sEditor.apply();
    }

    static void setId(String id) {
        if (id != null && !id.equals(sUser.getId())) {
            sUser.setId(id);
            sEditor.putString("id", id);
            sEditor.apply();
        }
    }

    static String getId() {
        return sSharedPreferences.getString("id", null);
    }

    static void setUsername(String username) {
        if (username != null && !username.equals(sUser.getUsername())) {
            sUser.setUsername(username);
            sEditor.putString("username", username);
            sEditor.apply();
        }
    }

    static String getUsername() {
        return sSharedPreferences.getString("username", null);
    }

    public static void setLoveId(String loveId) {
        if (loveId != null && !loveId.equals(sUser.getLoveId())) {
            sUser.setLoveId(loveId);
            sEditor.putString("loveid", loveId);
            sEditor.apply();
        }
    }

    static String getLoveId() {
        return sSharedPreferences.getString("loveid", null);
    }

    static void setGroup(String group) {
        if (group != null && !group.equals(sUser.getGroup())) {
            sUser.setGroup(group);
            sEditor.putString("group", group);
            sEditor.apply();
        }
    }

    static String getGroup() {
        return sSharedPreferences.getString("group", null);
    }

    static void setSchool(String school) {
        if (school != null && !school.equals(sUser.getSchool())) {
            sUser.setSchool(school);
            sEditor.putString("school", school);
            sEditor.apply();
        }
    }

    static String getSchool() {
        return sSharedPreferences.getString("school", null);
    }

    static void setCharacter(String character) {
        if (character != null && !character.equals(sUser.getCharacter())) {
            sUser.setCharacter(character);
            sEditor.putString("character", character);
            sEditor.apply();
        }
    }

    static String getCharacter() {
        return sSharedPreferences.getString("character", null);
    }

    static void setAvatar(Avatar avatar) {
        if (avatar != null && !avatar.equals(sUser.getAvatar())) {
            sEditor.putString("normal_face", avatar.getNormal().getFace());
            sEditor.putString("normal_background", avatar.getNormal().getBackground());
            sEditor.putString("thumb_face", avatar.getThumb().getFace());
            sEditor.putString("thumb_background", avatar.getThumb().getBackground());
            sEditor.apply();
        }
    }

    static Avatar getAvatar() {
        return new Avatar(new Normal(sSharedPreferences.getString("normal_face", null), sSharedPreferences.getString("normal_background", null)),
                new Thumb(sSharedPreferences.getString("thumb_face", null), sSharedPreferences.getString("thumb_background", null)));
    }

    static void setProfile(String profile) {
        if (profile != null && !profile.equals(sUser.getProfile())) {
            sUser.setProfile(profile);
            sEditor.putString("profile", profile);
            sEditor.apply();
        }
    }

    static String getProfile() {
        return sSharedPreferences.getString("profile", null);
    }

    static void setPoints(String points) {
        if (points != null && !points.equals(sUser.getPoints())) {
            sUser.setPoints(points);
            sEditor.putString("points", points);
            sEditor.apply();
        }
    }

    static String getPoints() {
        return sSharedPreferences.getString("points", null);
    }

    static void setGiftTickets(String giftTickets) {
        if (giftTickets != null && !giftTickets.equals(sUser.getGiftTickets())) {
            sUser.setGiftTickets(giftTickets);
            sEditor.putString("gifttickets", giftTickets);
            sEditor.apply();
        }
    }

    static String getGiftTickets() {
        return sSharedPreferences.getString("gifttickets", null);
    }

    static void setFateSwitch(String fateSwitch) {
        if (fateSwitch != null && !fateSwitch.equals(sUser.getFateSwitch())) {
            sUser.setFateSwitch(fateSwitch);
            sEditor.putString("fateswitch", fateSwitch);
            sEditor.apply();
        }
    }

    static String getFateSwitch() {
        return sSharedPreferences.getString("fateswitch", null);
    }

    static void setImgWall(ImgWall imgWall) {
        if (imgWall != null && !imgWall.equals(sUser.getImgWall())) {
            clearImgWall();
            sEditor.putInt("rows", imgWall.getRows());
            for (int i = 0; i < imgWall.getRows(); i++) {
                sEditor.putString("url_" + i, imgWall.getUrl().get(i));
            }
            sEditor.apply();
        }
    }

    static ImgWall getImgWall() {
        int rows = sSharedPreferences.getInt("rows", 0);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            urls.add(sSharedPreferences.getString("url_" + i, null));
        }
        return new ImgWall(rows, urls);
    }

    static void clearImgWall() {
        int rows = sSharedPreferences.getInt("rows", 0);
        if (rows != 0) {
            for (int i = 0; i < rows; i++) {
                sEditor.remove("url_" + i);
            }
            sEditor.apply();
        }
    }

    public static void clearAll() {
        //sUser = new User();
        sEditor.clear();
        sEditor.apply();
    }
}
