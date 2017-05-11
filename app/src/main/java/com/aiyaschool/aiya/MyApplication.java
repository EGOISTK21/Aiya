package com.aiyaschool.aiya;

import android.app.Application;

import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.aiyaschool.aiya.util.DBUtil;
import com.aiyaschool.aiya.util.SignUtil;
import com.tencent.TIMManager;

import org.litepal.LitePal;

import cn.smssdk.SMSSDK;

/**
 * app实体类暴露User对象，onCreate初始化各种SDK
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private static final String APP_KEY = "1d3c277c6bde4";
    private static final String APP_SECRET = "3b295b8b0455bdf9c6aedf8ecc33f3cc";
    private static final String APP_ID = "1400029084";
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, APP_KEY, APP_SECRET);
        LitePal.initialize(this);
        TIMManager.getInstance().init(this);
        TLSService.getInstance().initTlsSdk(this);
        DBUtil.init(this);
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static void setUser(User user) {
        SignUtil.setUser(user);
    }

    public static User getUser() {
        return SignUtil.getUser();
    }

}
