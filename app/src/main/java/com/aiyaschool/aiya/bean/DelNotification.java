package com.aiyaschool.aiya.bean;

import android.util.Log;

import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.NotificationUtil;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/6/3.
 */

public class DelNotification implements AiyaNotification {

    private static final String TAG = "DelNotification";
    private String opuserid;

    public void initFromUser() {
        UserUtil.delLove();
        APIUtil.getPersonApi()
                .loadOtherDetail(opuserid)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<HttpResult<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: initFromUser " + d);
                    }

                    @Override
                    public void onNext(@NonNull HttpResult<User> userHttpResult) {
                        Log.i(TAG, "onNext: initFromUser " + userHttpResult);
                        if ("2000".equals(userHttpResult.getState())) {
                            NotificationUtil.show(DelNotification.this, "你被别人甩了", userHttpResult.getData().getUsername(), 444);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: initFromUser " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: initFromUser");
                    }
                });
    }

}
