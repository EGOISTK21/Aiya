package com.aiyaschool.aiya.message;

import android.app.Application;
import android.content.Context;

import com.aiyaschool.aiya.message.utils.Foreground;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.tencent.TIMManager;

/**
 * Created by ShootHzj on 2016/10/30.
 */

public class MyApplication extends Application{
    //全局的context对象
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Foreground.init(this);
        context = getApplicationContext();
        TIMManager.getInstance().init(this);

        TLSService.getInstance().initTlsSdk(context);
        //todo may be off line notification
    }


}
