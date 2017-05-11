package com.aiyaschool.aiya.activity.splash;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * app启动动画Presenter实现类
 * Created by EGOISTK21 on 2017/5/1.
 */

class SplashPresenter implements SplashContract.Presenter {

    private static final String TAG = "SplashPresenter";
    private SplashContract.Model mModel;
    private SplashContract.View mView;

    SplashPresenter(SplashContract.View view) {
        attach(view);
    }

    @Override
    public void attach(SplashContract.View view) {
        mModel = new SplashModel();
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }

    @Override
    public void init(String phone, String loginToken) {
        Log.i(TAG, "init: " + "phone:" + phone + " logintoken:" + loginToken);
        if (!phone.equals("") && !loginToken.equals("")) {
            mModel.init(phone, loginToken, new Observer<HttpResult<User>>() {
                @Override
                public void onSubscribe(@NonNull Disposable disposable) {
                    Log.i(TAG, "onSubscribe: init");
                }

                @Override
                public void onNext(@NonNull HttpResult<User> httpResult) {
                    Log.i(TAG, "onNext: init");
                    Log.i(TAG, "onNext: " + httpResult);
                    MyApplication.setHttpState(httpResult.getState());
                    MyApplication.setUser(httpResult.getData());
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    Log.i(TAG, "onError: init");
                    mView.startSignView();
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete: init");
                    switch (MyApplication.getHttpState()) {
                        case "2000":
                            mView.startMainView();
                            break;
                        case "5133":
                            ToastUtil.show("你好像还没提交注册信息");
                            APIUtil.getTokenApi()
                                    .loadUser(SignUtil.getPhone(),
                                            MyApplication.getUser().getTempToken())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .unsubscribeOn(Schedulers.io())
                                    .subscribe(new Consumer<HttpResult<User>>() {
                                        @Override
                                        public void accept(@NonNull HttpResult<User> httpResult) throws Exception {
                                            Log.i(TAG, "accept: " + httpResult);
                                            MyApplication.setHttpState(httpResult.getState());
                                            MyApplication.setUser(httpResult.getData());
                                        }
                                    });
                            mView.startFormView();
                            break;
                        case "3002":
                        case "3050":
                            ToastUtil.show("登录已过期，请重新登录");
                            mView.startSignView();
                            break;
                    }
                }
            });
        } else {
            mView.startSignView();
        }
    }
}
