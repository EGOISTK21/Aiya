package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.graphics.Bitmap;

/**
 * Created by EGOISTK21 on 2017/4/19.
 */

class MatchResultModel implements MatchResultContract.Model {

    private String mobile;
    private Bitmap avatar, background;
    private String nick, school;

    public MatchResultModel(Bitmap avatar, String nick, String school) {
        this.avatar = avatar;
        this.nick = nick;
        this.school = school;
    }

    public String getMobile() {
        return mobile;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getNick() {
        return nick;
    }

    public String getSchool() {
        return school;
    }
}
