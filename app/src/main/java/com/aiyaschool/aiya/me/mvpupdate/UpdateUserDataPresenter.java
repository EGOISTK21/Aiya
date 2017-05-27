package com.aiyaschool.aiya.me.mvpupdate;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataContract;

import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wewarriors on 2017/5/20.
 */

public class UpdateUserDataPresenter implements UpdateUserDataContract.Presenter {
    private static final String TAG = "UpdateUserDataPresenter";

    private UpdateUserDataContract.Model mModel;
    private UpdateUserDataContract.View mView;

    public UpdateUserDataPresenter(UpdateUserDataContract.View view) {
        attach(view);
    }

    private void attach(UpdateUserDataContract.View view) {
        mModel = new UpdateUserDataModel();
        mView = view;
    }

    @Override
    public void updateUserData(Map<String, String> map) {
        final Set<String> get = map.keySet();
        for (String test : get) {
            Log.d(TAG, "updateUserData: " + test + "," + map.get(test));
        }
        mModel.updateUserData(map, new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.d(TAG, "onNext: updateUserData" + httpResult.getState());
                mView.showUpdateSuccess();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                if (get.size() == 1) {
                    for (String test : get) {
                        if (test.equals("avatar")) {
                            mView.getMeIndexAvatarUrl();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void detach() {
        mModel = null;
        mView = null;
    }
}
