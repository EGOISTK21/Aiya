package com.aiyaschool.aiya.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.Avatar;
import com.aiyaschool.aiya.bean.ImgWall;
import com.aiyaschool.aiya.bean.Normal;
import com.aiyaschool.aiya.bean.Thumb;
import com.aiyaschool.aiya.bean.UpLoad;
import com.aiyaschool.aiya.bean.Url;
import com.aiyaschool.aiya.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

public class UserUtil {

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;
    private static User sUser, sTa;
    private static Boolean msgFlag;

    private UserUtil() {

    }

    public static void init() {
        sSharedPreferences = MyApplication.getInstance().getSharedPreferences("test", Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
        msgFlag = true;
        initUser();
        initTa();
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

    private static void initTa() {
        sTa = new User();
        if (sUser.isMatched()) {
            sTa.setId(UserUtil.getTaId());
            sTa.setUsername(UserUtil.getTaUsername());
            sTa.setPhone(UserUtil.getTaPhone());
            sTa.setSchool(UserUtil.getTaSchool());
            sTa.setAge(UserUtil.getTaAge());
            sTa.setHeight(UserUtil.getTaHeight());
            sTa.setCharacter(UserUtil.getTaCharacter());
            sTa.setConstellation(UserUtil.getTaConstellation());
            sTa.setHobby(UserUtil.getTaHobby());
            sTa.setAvatar(UserUtil.getTaAvatar());
            sTa.setProfile(UserUtil.getTaProfile());
            sTa.setImgWall(UserUtil.getTaImgWall());
            sTa.setStartdate(UserUtil.getStartDate());
        }
    }

    public static void setTa(User user) {
        if (user != null) {
            setTaId(user.getId());
            setTaUsername(user.getUsername());
            setTaPhone(user.getPhone());
            setTaSchool(user.getSchool());
            setTaAge(user.getAge());
            setTaHeight(user.getHeight());
            setTaCharacter(user.getCharacter());
            setTaConstellation(user.getConstellation());
            setTaHobby(user.getHobby());
            setTaAvatar(user.getAvatar());
            setTaProfile(user.getProfile());
            setImgWall(user.getImgWall());
            setStartDate(user.getStartdate());
//            MyApplication.getInstance().startActivity(new Intent(MyApplication.getInstance(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public static User getTa() {
        return sTa;
    }

    public static void delLove() {
        setLoveId("0");
        sTa = null;
    }

    public static void setFirst(boolean isFirst) {
        sEditor.putBoolean("first", isFirst);
        sEditor.apply();
    }

    public static boolean isFirst() {
        return sSharedPreferences.getBoolean("first", true);
    }

    public static void setContactShield(boolean contactShield) {
        sEditor.putBoolean("contactShield", contactShield);
        sEditor.apply();
    }

    public static boolean getContactShield() {
        return sSharedPreferences.getBoolean("contactShield", true);
    }

    public static Boolean getMsgFlag() {
        return msgFlag;
    }

    public static void setMsgFlag(Boolean msgFlag) {
        UserUtil.msgFlag = msgFlag;
    }

    public static void addMsgTime(long msgTime, int index) {
        if (msgTime > getMsgTime(index)) {
            setMsgFlag(true);
            sEditor.putLong("msgTime_" + index, msgTime);
        }
        sEditor.apply();
    }

    public static Long getMsgTime(int index) {
        setMsgFlag(false);
        return sSharedPreferences.getLong("msgTime_" + index, 0);
    }

    public static void addMsgPre(String msgPre, int index) {
        if (msgPre != null && !(msgPre.equals(getMsgPre(index)))) {
            sEditor.putString("msgPre_" + index, msgPre);
        }
        sEditor.apply();
    }

    public static String getMsgPre(int index) {
        return sSharedPreferences.getString("msgPre_" + index, null);
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

    private static void setTaPhone(String phone) {
        if (phone != null && !phone.equals(sTa.getPhone())) {
            sTa.setPhone(phone);
            sEditor.putString("ta_phone", phone);
            sEditor.apply();
        }
    }

    private static String getTaPhone() {
        return sSharedPreferences.getString("ta_phone", null);
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

    private static void setTaId(String id) {
        if (id != null && !id.equals(sTa.getId())) {
            sTa.setId(id);
            sEditor.putString("ta_id", id);
            sEditor.apply();
        }
    }

    private static String getTaId() {
        return sSharedPreferences.getString("ta_id", null);
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

    private static void setTaUsername(String username) {
        if (username != null && !username.equals(sTa.getUsername())) {
            sTa.setUsername(username);
            sEditor.putString("ta_username", username);
            sEditor.apply();
        }
    }

    private static String getTaUsername() {
        return sSharedPreferences.getString("ta_username", null);
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

    private static void setTaSchool(String school) {
        if (school != null && !school.equals(sTa.getSchool())) {
            sTa.setSchool(school);
            sEditor.putString("ta_school", school);
            sEditor.apply();
        }
    }

    private static String getTaSchool() {
        return sSharedPreferences.getString("ta_school", null);
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

    private static void setTaAge(String age) {
        if (age != null && !age.equals(sTa.getAge())) {
            sTa.setAge(age);
            sEditor.putString("ta_age", age);
            sEditor.apply();
        }
    }

    private static String getTaAge() {
        return sSharedPreferences.getString("ta_age", null);
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

    private static void setTaHeight(String height) {
        if (height != null && !height.equals(sTa.getHeight())) {
            sTa.setHeight(height);
            sEditor.putString("ta_height", height);
            sEditor.apply();
        }
    }

    private static String getTaHeight() {
        return sSharedPreferences.getString("ta_height", null);
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

    private static void setTaCharacter(String character) {
        if (character != null && !character.equals(sTa.getCharacter())) {
            sTa.setCharacter(character);
            sEditor.putString("ta_character", character);
            sEditor.apply();
        }
    }

    private static String getTaCharacter() {
        return sSharedPreferences.getString("ta_character", null);
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

    private static void setTaConstellation(String constellation) {
        if (constellation != null && !constellation.equals(sTa.getConstellation())) {
            sTa.setConstellation(constellation);
            sEditor.putString("ta_constellation", constellation);
            sEditor.apply();
        }
    }

    private static String getTaConstellation() {
        return sSharedPreferences.getString("ta_constellation", null);
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

    private static void setTaHobby(String hobby) {
        if (hobby != null && !hobby.equals(sTa.getHobby())) {
            sTa.setHobby(hobby);
            sEditor.putString("ta_hobby", hobby);
            sEditor.apply();
        }
    }

    private static String getTaHobby() {
        return sSharedPreferences.getString("ta_hobby", null);
    }

    private static void setAvatar(Avatar avatar) {
        if (avatar != null && !avatar.equals(sUser.getAvatar()) && !avatar.isNull()) {
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

    private static void setTaAvatar(Avatar avatar) {
        if (avatar != null && !avatar.equals(sTa.getAvatar()) && !avatar.isNull()) {
            sTa.setAvatar(avatar);
            sEditor.putString("ta_normal_face", avatar.getNormal().getFace());
            sEditor.putString("ta_normal_background", avatar.getNormal().getBackground());
            sEditor.putString("ta_thumb_face", avatar.getThumb().getFace());
            sEditor.putString("ta_thumb_background", avatar.getThumb().getBackground());
            sEditor.apply();
        }
    }

    private static Avatar getTaAvatar() {
        return new Avatar(new Normal(sSharedPreferences.getString("ta_normal_face", null), sSharedPreferences.getString("ta_normal_background", null)),
                new Thumb(sSharedPreferences.getString("ta_thumb_face", null), sSharedPreferences.getString("ta_thumb_background", null)));
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

    private static void setTaProfile(String profile) {
        if (profile != null && !profile.equals(sTa.getProfile())) {
            sTa.setProfile(profile);
            sEditor.putString("ta_profile", profile);
            sEditor.apply();
        }
    }

    private static String getTaProfile() {
        return sSharedPreferences.getString("ta_profile", null);
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
                sEditor.remove("url_normal" + i);
                sEditor.remove("url_thumb" + i);
            }
            sEditor.apply();
        }
    }

    private static void setTaImgWall(ImgWall imgWall) {
        if (imgWall != null && !imgWall.equals(sTa.getImgWall())) {
            sTa.setImgWall(imgWall);
            clearTaImgWall();
            sEditor.putInt("ta_rows", imgWall.getRows());
            for (int i = 0; i < imgWall.getRows(); i++) {
                sEditor.putString("ta_url_normal" + i, imgWall.getUrl().getNormal().get(i));
                sEditor.putString("ta_url_thumb" + i, imgWall.getUrl().getThumb().get(i));
            }
            sEditor.apply();
        }
    }

    private static ImgWall getTaImgWall() {
        int rows = sSharedPreferences.getInt("ta_rows", 0);
        List<String> normal = new ArrayList<>();
        List<String> thumb = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            normal.add(sSharedPreferences.getString("ta_url_normal" + i, null));
            thumb.add(sSharedPreferences.getString("ta_url_thumb" + i, null));
        }
        return new ImgWall(rows, new Url(normal, thumb));
    }

    private static void clearTaImgWall() {
        int rows = sSharedPreferences.getInt("ta_rows", 0);
        if (rows != 0) {
            for (int i = 0; i < rows; i++) {
                sEditor.remove("ta_url_normal" + i);
                sEditor.remove("ta_url_thumb" + i);
            }
            sEditor.apply();
        }
    }

    private static void setStartDate(long startDate) {
        if (startDate != 0) {
            sTa.setStartdate(startDate);
            sEditor.putLong("startdate", startDate);
            sEditor.apply();
        }
    }

    private static long getStartDate() {
        return sSharedPreferences.getLong("satrtdate", 0);
    }

    public static void setTask(int period, List<String> task) {
        if (task != null) {
            clearTask();
            sEditor.putInt("period", period);
            for (int i = 0; i < 7; i++) {
                sEditor.putString("task_" + i, task.get(i));
            }
            sEditor.apply();
        }
    }

    public static int getPeriod() {
        return sSharedPreferences.getInt("period", -1);
    }

    public static List<String> getTask() {
        List<String> task = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
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
