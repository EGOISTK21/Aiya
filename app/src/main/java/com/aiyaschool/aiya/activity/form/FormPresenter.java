package com.aiyaschool.aiya.activity.form;

import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.io.File;

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
        mModel.submitAvatar(RequestBody.create(MediaType.parse("image/jpeg"), file),
                new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: submitAvatar");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        Log.i(TAG, "onNext: submitAvatar");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: submitAvatar" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                        mView.signUp(MyApplication.getUser().getUpLoad().getImgname());
                    }
                });
    }

    @Override
    public void loadSchoolData(String province) {
        mView.setSchoolData(mModel.loadSchoolData(province));
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
