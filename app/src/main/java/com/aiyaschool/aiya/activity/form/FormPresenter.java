package com.aiyaschool.aiya.activity.form;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void loadSchoolData() {
        mModel.loadSchoolData(new Observer<HttpResult<List<String>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: loadSchoolData");
                mView.showPD();
            }

            @Override
            public void onNext(@NonNull HttpResult<List<String>> listHttpResult) {
                Log.i(TAG, "onNext: loadSchoolData");
                mView.setSchoolData(listHttpResult.getData());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: loadSchoolData");
                mView.dismissPD();
                mView.setSchoolData(new ArrayList<>(Collections.singletonList("")));
                ToastUtil.show("网络错误");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: loadSchoolData");
                mView.dismissPD();
            }
        });
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
                        Log.i(TAG, "onSubscribe: firstInit");
                        mView.showPD();
                    }

                    @Override
                    public void onNext(@NonNull HttpResult<User> httpResult) {
                        Log.i(TAG, "onNext: firstInit");
                        switch (httpResult.getState()) {
                            case "2000":
                                MyApplication.setUser(httpResult.getData());
                                break;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: firstInit");
                        mView.dismissPD();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: firstInit");
                        mView.dismissPD();
                        mView.startMainView();
                    }
                });
    }
}
