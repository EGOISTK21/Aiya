package com.aiyaschool.aiya.message.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;

import com.tencent.TIMBaseApplication;

/**
 * Created by ShootHzj on 2016/10/30.
 */

public class Foreground implements TIMBaseApplication.ActivityLifecycleCallbacks{

    //单例
    private static Foreground instance = new Foreground();

    private static String TAG = Foreground.class.getSimpleName();
    private final int CHECK_DELAY = 500;

    //用于判断是否程序在前台
    private boolean foreground = false, paused = true;
    //handler用于处理切换activity时的短暂时期可能出现的判断错误
    private Handler handler = new Handler();
    private Runnable check;

    public static void init(Application app){
        app.registerActivityLifecycleCallbacks(instance);
    }

    public static Foreground get(){
        return instance;
    }

    private Foreground(){}

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
