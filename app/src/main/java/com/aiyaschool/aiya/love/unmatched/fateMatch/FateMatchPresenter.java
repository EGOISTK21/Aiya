package com.aiyaschool.aiya.love.unmatched.fateMatch;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

class FateMatchPresenter implements FateMatchContract.Presenter {

    private static final String TAG = "FateMatchPresenter";
    private FateMatchContract.View mView;
    private FateMatchContract.Model mModel;

    FateMatchPresenter(FateMatchContract.View view) {
        mModel = new FateMatchModel();
        attachView(view);
    }

    @Override
    public void attachView(FateMatchContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void initFateSwitch() {
        mView.setFateSwitch(UserUtil.getUser().isFated());
    }

    @Override
    public void commitFateSwitch(final boolean isFated) {
        mModel.commitFateSwitch(String.valueOf(isFated), new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: commitFateSwitch");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.i(TAG, "onNext: commitFateSwitch " + httpResult);
                if (!"2000".equals(httpResult.getState())) {
                    mView.setFateSwitch(!isFated);
                    ToastUtil.show("网络错误，请稍后重试");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: commitFateSwitch");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: commitFateSwitch");
            }
        });
    }

    @Override
    public void startFateMatch() {
        mModel.startFateMatch(new Observer<HttpResult<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: startFateMatch");
            }

            @Override
            public void onNext(@NonNull HttpResult<User> userHttpResult) {
                Log.i(TAG, "onNext: startFateMatch " + userHttpResult);
                if ("2000".equals(userHttpResult.getState())) {
                    User user = userHttpResult.getData();
                    UserUtil.setLoveId(user.getLoveId());
                    UserUtil.setTa(user);
                    mView.fate(user);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: startFateMatch");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: startFateMatch");
            }
        });
    }
}
