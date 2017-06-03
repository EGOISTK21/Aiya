package com.aiyaschool.aiya.bean;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.NotificationUtil;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/6/2.
 */

public class ReplyNotification implements AiyaNotification {

    private static final String TAG = "ReplyNotification";
    private User mUser;
    private String requestid;
    private String succuserid;

    public void initReplyUser() {
        APIUtil.getLoverInfoApi()
                .getLoverInfo()
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
                            UserUtil.setTa(mUser);
                            UserUtil.setLoveId(mUser.getLoveId());
                            NotificationUtil.show(ReplyNotification.this, "别人同意你的请求了", getUser().getUsername(), 222);
                        } else if ("5013".equals(userHttpResult.getState())) {
                            SignUtil.signOut(MyApplication.getInstance());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: initHitFromUser " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: initHitFromUser");
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

    public String getSuccuserid() {
        return succuserid;
    }

    public void setSuccuserid(String succuserid) {
        this.succuserid = succuserid;
    }
}
