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
    public void initCanRandom() {

    }

    @Override
    public void commitCanRandom(final boolean canRandom) {
        mModel.commitCanRandom(String.valueOf(canRandom), new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: commitCanRandom");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.i(TAG, "onNext: commitCanRandom");
                if (!"2000".equals(httpResult.getState())) {
                    mView.setCanRandom(!canRandom);
                    ToastUtil.show("网络错误，请稍后重试");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: commitCanRandom");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: commitCanRandom");
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
