package com.aiyaschool.aiya.me.mvpPersonData;

import android.util.Log;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.UserUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wewarriors on 2017/5/13.
 */

public class PersonDataPresenter implements PersonDataContract.Presenter {

    private static final String TAG = "PersonDataPresenter";

    private PersonDataContract.Model mModel;
    private PersonDataContract.View mView;

    public PersonDataPresenter(PersonDataContract.View view) {
        attach(view);
    }

    private void attach(PersonDataContract.View view) {
        mModel = new PersonDataModel();
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }

    @Override
    public void loadSchoolData(String hometown) {
        mModel.loadSchoolData(hometown, new Observer<HttpResult<List<String>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull HttpResult<List<String>> listHttpResult) {
                System.out.println("PersonDataPresenter" + listHttpResult.getState());
                mView.setSchoolData(listHttpResult.getData());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void updateUserHeight(final String height) {
        mModel.updateUserHeight(height, new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("PersonDataPresenter OnSubscribe");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                System.out.println("http" + httpResult.getState());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getMeIndex(String demand) {
        mModel.getMeIndex(demand, new Observer<HttpResult<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull HttpResult<User> userHttpResult) {
                if (userHttpResult.getState().equals("2000")) {
                    Log.d(TAG, "onNext: " + userHttpResult.getData().toString());
                    mView.showGetMeIndex(userHttpResult.getData());
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void submitAvatar(String url, File file) {
        mModel.submitAvatar(url, RequestBody.create(MediaType.parse("image/jpeg"), file), new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: submitAvatar");
            }

            @Override
            public void onNext(@NonNull ResponseBody responseBody) {
                Log.d(TAG, "onNext: submitAvatar");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: submitAvatar" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: submitAvatar");
                mView.showSubmitAvatar();
            }
        });
    }

    @Override
    public void getAvatarUploadUrl() {
        mModel.getAvatarUploadUrl(new Observer<HttpResult<UploadUrl>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull HttpResult<UploadUrl> uploadUrlHttpResult) {
                Log.d(TAG, "onNext: getAvatarUploadUrl()" + uploadUrlHttpResult.getData().toString());
                mView.setAvatarUploadUrl(uploadUrlHttpResult.getData());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
