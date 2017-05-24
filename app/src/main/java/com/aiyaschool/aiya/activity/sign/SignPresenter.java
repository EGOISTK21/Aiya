package com.aiyaschool.aiya.activity.sign;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 登陆注册Presenter实现类
 * Created by EGOISTK21 on 2017/4/28.
 */

class SignPresenter implements SignContract.Presenter {

    private static final String TAG = "SignPresenter";
    private SignContract.Model mModel;
    private SignContract.View mView;

    SignPresenter(SignContract.View view) {
        attach(view);
    }

    @Override
    public void attach(SignContract.View view) {
        mModel = new SignModel();
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }

    @Override
    public void sign(final String phone, String verification) {
        mModel.sign(phone, verification, new Observer<HttpResult<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.i(TAG, "onSubscribe: sign");
                mView.showPD();
            }

            @Override
            public void onNext(@NonNull HttpResult<User> httpResult) {
                Log.i(TAG, "onNext: sign " + httpResult);
                mView.dismissPD();
                switch (httpResult.getState()) {
                    case "5144":
                        ToastUtil.show("验证码错误");
                        break;
                    case "5130":
                        MyApplication.setUser(httpResult.getData());
                        APIUtil.getTokenApi()
                                .loadUser(phone, httpResult.getData().getTempToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .unsubscribeOn(Schedulers.io())
                                .subscribe(new Observer<HttpResult<User>>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        Log.i(TAG, "onSubscribe: sign up");
                                    }

                                    @Override
                                    public void onNext(@NonNull HttpResult<User> userHttpResult) {
                                        Log.i(TAG, "onNext: sign up " + userHttpResult);
                                        switch (userHttpResult.getState()) {
                                            case "2000":
                                                MyApplication.setUser(userHttpResult.getData());
                                                mView.startFormView();
                                                break;
                                            case "5044":
                                                ToastUtil.show("你的网络好像开小差了");
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.i(TAG, "onError: sign up");
                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.i(TAG, "onComplete: sign up");
                                    }
                                });
                        break;
                    case "2000":
                        MyApplication.setUser(httpResult.getData());
                        Log.d(TAG, "onNext: " + httpResult.getData().getAvatar().getNormal().getFace());
                        Log.d(TAG, "onNext: userUtil" + UserUtil.getUser().getAvatar().getNormal().getFace());

                        mView.startMainView();
                        break;
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.i(TAG, "onError: sign");
                mView.dismissPD();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: sign");
            }
        });
    }
}
