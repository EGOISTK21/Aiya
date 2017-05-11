package com.aiyaschool.aiya.activity.form;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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
    public void submitAvatar(File file) {
        mModel.submitAvatar(RequestBody.create(MediaType.parse("image/jpg"), file),
                new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        Log.i(TAG, "onNext: " + responseBody);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        SignUtil.clearUpLoad();
                    }
                });
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
                if (listHttpResult.getState().equals("2000")) {
                    mView.setSchoolData(listHttpResult.getData());
                } else {
                    mView.setSchoolData(new ArrayList<>(Collections.singletonList("")));
                    ToastUtil.show("网络错误");
                }
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
                          String character,
                          String hobby,
                          String avatar) {
        mModel.firstInit(loginToken, phone, username, gender, school, age,
                height, constellation, character, hobby, avatar, new Observer<HttpResult<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: firstInit");
                        mView.showPD();
                    }

                    @Override
                    public void onNext(@NonNull HttpResult<User> httpResult) {
                        Log.i(TAG, "onNext: firstInit " + httpResult);
                        mView.dismissPD();
                        switch (httpResult.getState()) {
                            case "2000":
                                MyApplication.setUser(httpResult.getData());
                                mView.startMainView();
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
                    }
                });
    }
}
