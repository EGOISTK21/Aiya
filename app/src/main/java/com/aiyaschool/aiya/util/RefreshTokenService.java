package com.aiyaschool.aiya.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/5/19.
 */

public class RefreshTokenService extends Service {

    private static final String TAG = "RefreshTokenService";
    private static final String REFRESH_FLAG = "refresh";
    private long triggerAtTime;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (triggerAtTime == 0) {
            triggerAtTime = System.currentTimeMillis();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    APIUtil.getInitApi()
                            .loadUser(SignUtil.getPhone(), SignUtil.getLoginToken(), REFRESH_FLAG)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .unsubscribeOn(Schedulers.io())
                            .subscribe(new Observer<HttpResult<User>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull HttpResult<User> userHttpResult) {
                                    Log.i(TAG, "onNext: onStartCommand " + userHttpResult);
                                    if ("2000".equals(userHttpResult.getState())) {
                                        MyApplication.setUser(userHttpResult.getData());
                                        SignUtil.addAccessToken();
                                    } else {
                                        ToastUtil.show("网络已断开，请重新登录");
                                        SignUtil.signOut(RefreshTokenService.this);
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    ToastUtil.show("网络不通畅");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
            }).start();
        }
        ((AlarmManager) getSystemService(ALARM_SERVICE))
                .setExact(AlarmManager.RTC_WAKEUP,
                        triggerAtTime += 1700000,
                        PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class),
                                0));
        return super.onStartCommand(intent, flags, startId);
    }
}
