package com.aiyaschool.aiya.message.bean;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.NotificationUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class HitNotification {

    private static final String TAG = "HitNotification";
    private User mUser;
    private String requestid;
    private String fromuserid;
    private boolean state;

    public HitNotification(String requestid, String fromuserid, boolean state) {
        this.requestid = requestid;
        this.fromuserid = fromuserid;
        this.state = state;
    }

    public void initHitFromUser() {
        APIUtil.getPersonApi()
                .loadOtherDetail(fromuserid)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<HttpResult<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: initHitFromUser " + d);
                    }

                    @Override
                    public void onNext(@NonNull HttpResult<User> userHttpResult) {
                        Log.i(TAG, "onNext: initHitFromUser " + userHttpResult);
                        if ("2000".equals(userHttpResult.getState())) {
                            mUser = userHttpResult.getData();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: initHitFromUser " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: initHitFromUser");
                        NotificationUtil.show(HitNotification.this, "你被别人撩啦", getUser().getUsername(), 111);
                    }
                });
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }

    public void responseHit(boolean response) {

    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
