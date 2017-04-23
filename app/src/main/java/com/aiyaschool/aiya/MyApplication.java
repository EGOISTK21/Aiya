package com.aiyaschool.aiya;

import android.app.Application;

import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.tencent.TIMManager;

import cn.smssdk.SMSSDK;

/**
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    volatile private User user;
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
        TIMManager.getInstance().init(this);
        TLSService.getInstance().initTlsSdk(this);
        instance = this;
        setMatched(false);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
