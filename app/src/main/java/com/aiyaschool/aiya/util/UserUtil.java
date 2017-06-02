package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.bean.Avatar;
import com.aiyaschool.aiya.bean.ImgWall;
import com.aiyaschool.aiya.bean.Normal;
import com.aiyaschool.aiya.bean.Thumb;
import com.aiyaschool.aiya.bean.UpLoad;
import com.aiyaschool.aiya.bean.Url;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.bean.HitNotification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class UserUtil {

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;
    private static User sUser, sTa;
    private static List<HitNotification> sHitNotifications;

    private UserUtil() {

    }

    public static void init(Context context) {
        sSharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
        initUser();
        initHitNotification();
    }

    private static void initUser() {
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
        sUser.setSex(UserUtil.getSex());
        sUser.setAge(UserUtil.getAge());
        sUser.setHeight(UserUtil.getHeight());
        sUser.setCharacter(UserUtil.getCharacter());
        sUser.setConstellation(UserUtil.getConstellation());
        sUser.setHobby(UserUtil.getHobby());
        sUser.setAvatar(UserUtil.getAvatar());
        sUser.setProfile(UserUtil.getProfile());
        sUser.setGiftTickets(UserUtil.getGiftTickets());
        sUser.setFateSwitch(UserUtil.getFateSwitch());
        sUser.setImgWall(UserUtil.getImgWall());
    }

    public static void setUser(User user) {
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
        setSex(user.getSex());
        setAge(user.getAge());
        setHeight(user.getHeight());
        setCharacter(user.getCharacter());
        setConstellation(user.getConstellation());
        setHobby(user.getHobby());
        setAvatar(user.getAvatar());
        setProfile(user.getProfile());
        setGiftTickets(user.getGiftTickets());
        setFateSwitch(user.getFateSwitch());
        setImgWall(user.getImgWall());
    }

    public static User getUser() {
        return sUser;
    }

    public static void setTa(User user) {
        if (user != null) {
            sTa = user;
        }
    }

    public static User getTa() {
        return sTa;
    }

    private static void initHitNotification() {
        sHitNotifications = new ArrayList<>();
        int size = sSharedPreferences.getInt("hitnotificationNum", 0);
        for (int i = 0; i < size; i++) {
//            sHitNotifications.add(new HitNotification(
//                            new Avatar(null, new Thumb(sSharedPreferences.getString("hitnotification_avatar_" + i, null), null)),
//                            sSharedPreferences.getString("hitnotification_username_" + i, null),
//                            sSharedPreferences.getString("hitnotification_school_" + i, null),
//                            sSharedPreferences.getString("hitnotification_requestid_" + i, null),
//                            sSharedPreferences.getString("hitnotification_fromuserid_" + i, null),
//                            sSharedPreferences.getBoolean("hitnotification_state_" + i, Boolean.parseBoolean(null))));
        }
    }

    public static void addHitNotification(HitNotification hitNotification) {
//        sHitNotifications.add(hitNotification);
//        int size = sHitNotifications.size();
//        sEditor.putInt("hitnotificationNum", size);
//        sEditor.putString("hitnotification_avatar_" + size, hitNotification.getAvatar().getThumb().getFace());
//        sEditor.putString("hitnotification_username_" + size, hitNotification.getUsername());
//        sEditor.putString("hitnotification_school_" + size, hitNotification.getSchool());
//        sEditor.putString("hitnotification_requestid_" + size, hitNotification.getRequestid());
//        sEditor.putString("hitnotification_fromuserid_" + size, hitNotification.getFromuserid());
//        sEditor.putBoolean("hitnotification_state_" + size, hitNotification.getState());
//        sEditor.apply();
    }

    public static void updateHitNotification(int index, boolean state) {
        sHitNotifications.get(index).setState(state);
    }

    private static List<HitNotification> getHitNotifications() {
        return sHitNotifications;
    }

    public static void setContactShield(boolean contactShield) {
        sEditor.putBoolean("contactShield", contactShield);
        sEditor.apply();
    }

    public static boolean getContactShield() {
        return sSharedPreferences.getBoolean("contactShield", true);
    }

    private static void setLoginToken(String loginToken) {
        if (loginToken != null && !loginToken.equals(sUser.getLoginToken())) {
            sUser.setLoginToken(loginToken);
            sEditor.putString("logintoken", loginToken);
            sEditor.apply();
        }
    }

    private static String getLoginToken() {
        return sSharedPreferences.getString("logintoken", null);
    }

    static void clearLoginToken() {
        sUser.setLoginToken(null);
        sEditor.remove("logintoken");
        sEditor.apply();
    }

    private static void setAccessToken(String accessToken) {
        if (accessToken != null && !accessToken.equals(sUser.getAccessToken())) {
            sUser.setAccessToken(accessToken);
            sEditor.putString("accesstoken", "");
            sEditor.apply();
        }
    }

    private static String getAccessToken() {
        return sSharedPreferences.getString("accesstoken", null);
    }

    private static void setUserSig(String userSig) {
        if (userSig != null && !userSig.equals(sUser.getUserSig())) {
            sUser.setUserSig(userSig);
            sEditor.putString("usersig", userSig);
            sEditor.apply();
        }
    }

    private static String getUserSig() {
        return sSharedPreferences.getString("usersig", null);
    }

    static void setPhone(String phone) {
        if (phone != null && !phone.equals(sUser.getPhone())) {
            sUser.setPhone(phone);
            sEditor.putString("phone", phone);
            sEditor.apply();
        }
    }

    private static String getPhone() {
        return sSharedPreferences.getString("phone", null);
    }

    private static void setUpLoad(UpLoad upLoad) {
        if (upLoad != null && !upLoad.equals(sUser.getUpLoad())) {
            sUser.setUpLoad(upLoad);
            sEditor.putString("upurl", upLoad.getUpurl());
            sEditor.putString("imgname", upLoad.getImgname());
            sEditor.apply();
        }
    }

    private static UpLoad getUpLoad() {
        return new UpLoad(sSharedPreferences.getString("upurl", null), sSharedPreferences.getString("imgname", null));
    }

    private static void clearUpLoad() {
        sUser.setUpLoad(null);
        sEditor.remove("upurl");
        sEditor.remove("imgname");
        sEditor.apply();
    }

    private static void setId(String id) {
        if (id != null && !id.equals(sUser.getId())) {
            sUser.setId(id);
            sEditor.putString("id", id);
            sEditor.apply();
        }
    }

    private static String getId() {
        return sSharedPreferences.getString("id", null);
    }

    private static void setUsername(String username) {
        if (username != null && !username.equals(sUser.getUsername())) {
            sUser.setUsername(username);
            sEditor.putString("username", username);
            sEditor.apply();
        }
    }

    private static String getUsername() {
        return sSharedPreferences.getString("username", null);
    }

    public static void setLoveId(String loveId) {
        if (loveId != null && !loveId.equals(sUser.getLoveId())) {
            sUser.setLoveId(loveId);
            sEditor.putString("loveid", loveId);
            sEditor.apply();
        }
    }

    private static String getLoveId() {
        return sSharedPreferences.getString("loveid", null);
    }

    private static void setGroup(String group) {
        if (group != null && !group.equals(sUser.getGroup())) {
            sUser.setGroup(group);
            sEditor.putString("group", group);
            sEditor.apply();
        }
    }

    private static String getGroup() {
        return sSharedPreferences.getString("group", null);
    }

    private static void setSex(String sex) {
        if (sex != null && !sex.equals(sUser.getSex())) {
            sUser.setSex(sex);
            sEditor.putString("sex", sex);
            sEditor.apply();
        }
    }

    private static String getSex() {
        return sSharedPreferences.getString("sex", null);
    }

    private static void setSchool(String school) {
        if (school != null && !school.equals(sUser.getSchool())) {
            sUser.setSchool(school);
            sEditor.putString("school", school);
            sEditor.apply();
        }
    }

    private static String getSchool() {
        return sSharedPreferences.getString("school", null);
    }

    private static void setAge(String age) {
        if (age != null && !age.equals(sUser.getAge())) {
            sUser.setHobby(age);
            sEditor.putString("age", age);
            sEditor.apply();
        }
    }

    private static String getAge() {
        return sSharedPreferences.getString("age", null);
    }

    private static void setHeight(String height) {
        if (height != null && !height.equals(sUser.getHeight())) {
            sUser.setHeight(height);
            sEditor.putString("height", height);
            sEditor.apply();
        }
    }

    private static String getHeight() {
        return sSharedPreferences.getString("height", null);
    }

    private static void setCharacter(String character) {
        if (character != null && !character.equals(sUser.getCharacter())) {
            sUser.setCharacter(character);
            sEditor.putString("character", character);
            sEditor.apply();
        }
    }

    private static String getCharacter() {
        return sSharedPreferences.getString("character", null);
    }

    private static void setConstellation(String constellation) {
        if (constellation != null && !constellation.equals(sUser.getConstellation())) {
            sUser.setConstellation(constellation);
            sEditor.putString("constellation", constellation);
            sEditor.apply();
        }
    }

    private static String getConstellation() {
        return sSharedPreferences.getString("constellation", null);
    }

    private static void setHobby(String hobby) {
        if (hobby != null && !hobby.equals(sUser.getHobby())) {
            sUser.setHobby(hobby);
            sEditor.putString("hobby", hobby);
            sEditor.apply();
        }
    }

    private static String getHobby() {
        return sSharedPreferences.getString("hobby", null);
    }

    private static void setAvatar(Avatar avatar) {
        if (avatar != null && !avatar.equals(sUser.getAvatar())) {
            sUser.setAvatar(avatar);
            sEditor.putString("normal_face", avatar.getNormal().getFace());
            sEditor.putString("normal_background", avatar.getNormal().getBackground());
            sEditor.putString("thumb_face", avatar.getThumb().getFace());
            sEditor.putString("thumb_background", avatar.getThumb().getBackground());
            sEditor.apply();
        }
    }

    private static Avatar getAvatar() {
        return new Avatar(new Normal(sSharedPreferences.getString("normal_face", null), sSharedPreferences.getString("normal_background", null)),
                new Thumb(sSharedPreferences.getString("thumb_face", null), sSharedPreferences.getString("thumb_background", null)));
    }

    private static void setProfile(String profile) {
        if (profile != null && !profile.equals(sUser.getProfile())) {
            sUser.setProfile(profile);
            sEditor.putString("profile", profile);
            sEditor.apply();
        }
    }

    private static String getProfile() {
        return sSharedPreferences.getString("profile", null);
    }

    private static void setGiftTickets(String giftTickets) {
        if (giftTickets != null && !giftTickets.equals(sUser.getGiftTickets())) {
            sUser.setGiftTickets(giftTickets);
            sEditor.putString("gifttickets", giftTickets);
            sEditor.apply();
        }
    }

    private static String getGiftTickets() {
        return sSharedPreferences.getString("gifttickets", null);
    }

    public static void setFateSwitch(String fateSwitch) {
        if (fateSwitch != null && !fateSwitch.equals(sUser.getFateSwitch())) {
            sUser.setFateSwitch(fateSwitch);
            sEditor.putString("fateswitch", fateSwitch);
            sEditor.apply();
        }
    }

    private static String getFateSwitch() {
        return sSharedPreferences.getString("fateswitch", null);
    }

    private static void setImgWall(ImgWall imgWall) {
        if (imgWall != null && !imgWall.equals(sUser.getImgWall())) {
            sUser.setImgWall(imgWall);
            clearImgWall();
            sEditor.putInt("rows", imgWall.getRows());
            for (int i = 0; i < imgWall.getRows(); i++) {
                sEditor.putString("url_normal" + i, imgWall.getUrl().getNormal().get(i));
                sEditor.putString("url_thumb" + i, imgWall.getUrl().getThumb().get(i));
            }
            sEditor.apply();
        }
    }

    private static ImgWall getImgWall() {
        int rows = sSharedPreferences.getInt("rows", 0);
        List<String> normal = new ArrayList<>();
        List<String> thumb = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            normal.add(sSharedPreferences.getString("url_normal" + i, null));
            thumb.add(sSharedPreferences.getString("url_thumb" + i, null));
        }
        return new ImgWall(rows, new Url(normal, thumb));
    }

    private static void clearImgWall() {
        int rows = sSharedPreferences.getInt("rows", 0);
        if (rows != 0) {
            for (int i = 0; i < rows; i++) {
                sEditor.remove("url_" + i);
            }
            sEditor.apply();
        }
    }

    public static void setTask(List<String> task) {
        if (task != null) {
            clearTask();
            sEditor.putInt("taskNum", task.size());
            for (int i = 0; i < task.size(); i++) {
                sEditor.putString("task_" + i, task.get(i));
            }
            sEditor.apply();
        }
    }

    public static List<String> getTask() {
        int taskNum = sSharedPreferences.getInt("taskNum", 0);
        List<String> task = new ArrayList<>();
        for (int i = 0; i < taskNum; i++) {
            task.add(sSharedPreferences.getString("task_" + i, null));
        }
        return task;
    }

    private static void clearTask() {
        int taskNum = sSharedPreferences.getInt("taskNum", 0);
        for (int i = 0; i < taskNum; i++) {
            sEditor.remove("task_" + i);
        }
    }

    public static void clearAll() {
//        String phone = sSharedPreferences.getString("phone", null);
//        String logintoken = sSharedPreferences.getString("logintoken", null);
        sEditor.clear();
//        sEditor.putString("phone", phone);
//        sEditor.putString("logintoken", logintoken);
        sEditor.apply();
    }
}
