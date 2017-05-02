package com.aiyaschool.aiya.activity.form;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 表单Presenter实现类
 * Created by EGOISTK21 on 2017/5/2.
 */

class FormPresenter implements FormContract.Presenter {

    private static final String TAG = "FormPresenter";
    private FormContract.Model mModel;
    private FormContract.View mView;

    FormPresenter(FormContract.View view) {
        attach(view);
    }


    @Override
    public void attach(FormContract.View view) {
        mModel = new FormModel();
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }

    @Override
    public void firstInit(String loginToken,
                          String phone,
                          String username,
                          String gender,
                          String school,
                          String age,
                          String height,
                          String constellation,
                          String hometown,
                          String hobby) {
        mModel.firstInit(loginToken, phone, username, gender, school, age,
                height, constellation, hometown, hobby, new Observer<HttpResult<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HttpResult<User> httpResult) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: firstInit");
                        mView.startMainView();
                    }
                });
    }
}
