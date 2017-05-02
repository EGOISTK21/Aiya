package com.aiyaschool.aiya.activity.sign;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.SignUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by EGOISTK21 on 2017/4/28.
 */

class SignPresenter implements SignContract.Presenter {

    private final static String TAG = "SignPresenter";
    private SignContract.Model mModel;
    private SignContract.View mView;

    SignPresenter(SignContract.View view) {
        attach(view);
    }

    @Override
    public void attach(SignContract.View view) {
        this.mModel = new SignModel();
        this.mView = view;
    }

    @Override
    public void detach() {
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void sign(final String phone, String verification) {
        mModel.sign(phone, verification, new Observer<HttpResult<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.i(TAG, "onSubscribe: sign");
                mView.showPD();
                SignUtil.setPhone(phone);
            }

            @Override
            public void onNext(@NonNull HttpResult<User> httpResult) {
                Log.i(TAG, "onNext: sign");
                MyApplication.setHttpState(httpResult.getState());
                MyApplication.setUser(httpResult.getData());
                SignUtil.setLoginToken(httpResult.getData().getLogintoken());
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.i(TAG, "onError: sign");
                mView.dismissPD();
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: sign");
                mView.dismissPD();
                switch (MyApplication.getHttpState()) {
                    case "5130":
                        mView.startFormView();
                        break;
                    case "2000":
                        mView.startMainView();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
