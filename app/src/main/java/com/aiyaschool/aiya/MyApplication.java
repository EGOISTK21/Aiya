package com.aiyaschool.aiya;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.aiyaschool.aiya.bean.ReplyNotification;
import com.aiyaschool.aiya.me.util.DBCopyUtil;
import com.aiyaschool.aiya.bean.HitNotification;
import com.aiyaschool.aiya.util.RefreshTokenService;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.google.gson.Gson;
import com.tencent.TIMConnListener;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMLogListener;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMUserStatusListener;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import org.litepal.LitePal;

import java.util.List;

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
        initTIM();
        startService(new Intent(this, RefreshTokenService.class));
    }

    private void initTIM() {
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {

            private Gson gson = new Gson();
            private HitNotification hitNotification;
            private ReplyNotification replyNotification;

            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                for (TIMMessage message : list) {
                    for (int i = 0; i < message.getElementCount(); i++) {
                        Log.i(TAG, "onNewMessages: " + message.getElement(i));
                        TIMElem elem = message.getElement(i);
                        TIMElemType type = elem.getType();
                        if (type == TIMElemType.Custom) {
                            System.out.println("+++++++++++" + new String(((TIMCustomElem) elem).getData()));
                            if ("aiyaliao".equals(((TIMCustomElem) elem).getDesc())) {
                                hitNotification = gson.fromJson(new String(((TIMCustomElem) elem).getData()), HitNotification.class);
                                hitNotification.initHitFromUser();
                            } else if ("aiyaliaoreply".equals(((TIMCustomElem) elem).getDesc())) {
                                replyNotification = gson.fromJson(new String(((TIMCustomElem) elem).getData()), ReplyNotification.class);
                                replyNotification.initReplyUser();
                            }
                        }
                    }
                }
                return true;
            }
        });
        if (MsfSdkUtils.isMainProcess(this)) {
            Log.d("MyApplication", "main process");
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    Log.e("MyApplication", "recv offline push");
                    notification.doNotify(getApplicationContext(), R.drawable.ic_launcher);
                }
            });
        }
        TIMManager.getInstance().setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {
                Log.i(TAG, "onConnected: initTIM");
            }

            @Override
            public void onDisconnected(int i, String s) {
                Log.i(TAG, "onDisconnected: initTIM " + i + " " + s);
            }

            @Override
            public void onWifiNeedAuth(String s) {
                Log.i(TAG, "onWifiNeedAuth: initTIM " + s);
            }
        });
        TIMManager.getInstance().setLogListener(new TIMLogListener() {
            @Override
            public void log(int i, String s, String s1) {
                Log.i(TAG, "log: " + i + " " + s + " " + s1);
            }
        });
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                ToastUtil.show("你的账号已在其他设备登录");
            }

            @Override
            public void onUserSigExpired() {
                ToastUtil.show("登录已过期，请重新登录");
            }
        });
        TIMManager.getInstance().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(new Intent(this, RefreshTokenService.class));
    }
}
