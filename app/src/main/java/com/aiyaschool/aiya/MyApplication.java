package com.aiyaschool.aiya;

import android.app.Application;

import com.aiyaschool.aiya.me.util.DBCopyUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.tencent.TIMManager;

import org.litepal.LitePal;

import cn.smssdk.SMSSDK;

/**
 * app实体类暴露User对象，onCreate初始化各种SDK
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    private static final String APP_KEY = "1d3c277c6bde4";
    private static final String APP_SECRET = "3b295b8b0455bdf9c6aedf8ecc33f3cc";
    public static final int APP_ID = 1400029084;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SMSSDK.initSDK(this, APP_KEY, APP_SECRET);
        LitePal.initialize(this);
        UserUtil.init(this);
        DBCopyUtil.copyDataBaseFromAssets(this, "edu.db");
        TIMManager.getInstance().init(this);
    }

}
