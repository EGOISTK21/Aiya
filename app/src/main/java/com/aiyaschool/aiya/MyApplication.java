package com.aiyaschool.aiya;

import android.app.Application;
import android.content.SharedPreferences;

import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.tencent.TIMManager;

import org.litepal.LitePal;

import cn.smssdk.SMSSDK;

/**
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private User user;
    private boolean matched;
    private static MyApplication instance;
    private final String APPKEY = "1d3c277c6bde4";
    private final String APPSECRET = "3b295b8b0455bdf9c6aedf8ecc33f3cc";

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isMatched() {
        return matched;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        LitePal.initialize(this);
        TIMManager.getInstance().init(this);
        TLSService.getInstance().initTlsSdk(this);
        instance = this;
        setMatched(false);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void initUser(User user) {
        this.user = user;
    }

    private void getUserInfo() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userName = userInfo.getString("username", null);
        String userSig = userInfo.getString("usersig", null);
        String loginToken = userInfo.getString("logintoken", null);
    }
}
