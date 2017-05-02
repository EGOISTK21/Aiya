package com.aiyaschool.aiya.activity.splash;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
        if (phone != null && loginToken != null) {
            mModel.init(phone, loginToken, new Observer<HttpResult<User>>() {
                @Override
                public void onSubscribe(@NonNull Disposable disposable) {
                    Log.i(TAG, "onSubscribe: init");
                }

                @Override
                public void onNext(@NonNull HttpResult<User> httpResult) {
                    Log.i(TAG, "onNext: init");
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

                            mView.startFormView();
                            break;
                        case "3002":
                        case "3050":
                        default:
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
