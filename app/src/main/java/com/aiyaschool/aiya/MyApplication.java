package com.aiyaschool.aiya;

import android.app.Application;

import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.aiyaschool.aiya.util.SignUtil;
import com.tencent.TIMManager;

import org.litepal.LitePal;

import cn.smssdk.SMSSDK;

/**
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private final String APP_KEY = "1d3c277c6bde4";
    private final String APP_SECRET = "3b295b8b0455bdf9c6aedf8ecc33f3cc";
    private final String APP_ID = "1400029084";
    private static MyApplication instance;
    private static User sUser;
    private static String sHttpState;

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, APP_KEY, APP_SECRET);
        LitePal.initialize(this);
        TIMManager.getInstance().init(this);
        TLSService.getInstance().initTlsSdk(this);
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static void setUser(User user) {
        SignUtil.setLoginToken(user.getLogintoken());
        sUser = user;
    }

    public static User getUser() {
        return sUser;
    }

    public static String getHttpState() {
        return sHttpState;
    }

    public static void setHttpState(String httpState) {
        sHttpState = httpState;
    }
}
