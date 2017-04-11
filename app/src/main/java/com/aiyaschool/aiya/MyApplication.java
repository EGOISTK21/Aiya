package com.aiyaschool.aiya;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private boolean matched;

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isMatched() {
        return matched;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setMatched(false);
    }

    private void getUserInfo() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userName = userInfo.getString("username", null);
        String userSig = userInfo.getString("usersig", null);
        String loginToken = userInfo.getString("logintoken", null);
    }
}
