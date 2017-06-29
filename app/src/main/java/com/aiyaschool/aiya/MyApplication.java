package com.aiyaschool.aiya;

import android.app.ActivityManager;
import android.content.Intent;
import android.util.Log;

import com.aiyaschool.aiya.bean.ChatNotification;
import com.aiyaschool.aiya.bean.DelNotification;
import com.aiyaschool.aiya.bean.HitNotification;
import com.aiyaschool.aiya.bean.ReplyNotification;
import com.aiyaschool.aiya.me.util.DBCopyUtil;
import com.aiyaschool.aiya.util.RefreshTokenService;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.google.gson.Gson;
import com.mob.MobApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
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
import com.tencent.TIMTextElem;
import com.tencent.TIMUserStatusListener;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import org.litepal.LitePal;

import java.io.File;
import java.util.List;

/**
 * app实体类，onCreate初始化各种SDK
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends MobApplication {

    private static final String TAG = "MyApplication";
    public static final int APP_ID = 1400029084;
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        instance = this;
        LitePal.initialize(this);
        UserUtil.init();
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
                            if ("aiyaliao".equals(((TIMCustomElem) elem).getDesc())) {
                                hitNotification = gson.fromJson(new String(((TIMCustomElem) elem).getData()), HitNotification.class);
                                hitNotification.initHitFromUser();
                            } else if ("aiyaliaoreply".equals(((TIMCustomElem) elem).getDesc())) {
                                replyNotification = gson.fromJson(new String(((TIMCustomElem) elem).getData()), ReplyNotification.class);
                                replyNotification.initReplyUser();
                            } else if ("aiyadellove".equals(((TIMCustomElem) elem).getDesc())) {
                                new DelNotification();
                            }
                        } else if (!isTopActivity("com.aiyaschool.aiya.message.ui.activity.ChatQQActivity")) {
                            if (type == TIMElemType.Text) {
                                new ChatNotification(((TIMTextElem) elem).getText(), 555);
                            } else if (type == TIMElemType.Image) {
                                new ChatNotification("图片", 555);
                            } else if (type == TIMElemType.Sound) {
                                new ChatNotification("语音", 555);
                            }
                            return false;
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
                    notification.doNotify(instance, R.drawable.ic_notification);
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
                SignUtil.signOut(instance);
            }

            @Override
            public void onUserSigExpired() {
                ToastUtil.show("登录已过期，请重新登录");
                SignUtil.signOut(instance);
            }
        });
        TIMManager.getInstance().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(new Intent(this, RefreshTokenService.class));
    }

    private void initImageLoader() {


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.mis_default_error) // resource or
                // drawable
                .showImageForEmptyUri(R.drawable.mis_default_error) // resource or
                // drawable
                .showImageOnFail(R.drawable.mis_default_error).build();

        File imageCacheDir = StorageUtils.getCacheDirectory(this, true);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(imageCacheDir))
                // 自定义缓存路径
                .defaultDisplayImageOptions(options)
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // 超时时间
                .build();// 开始构建
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    private boolean isTopActivity(String activityName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if (runningTaskInfos != null) {
            cmpNameTemp = runningTaskInfos.get(0).topActivity.toString();
        }
        if (cmpNameTemp == null) {
            return false;
        }
        return cmpNameTemp.equals(activityName);
    }

}
