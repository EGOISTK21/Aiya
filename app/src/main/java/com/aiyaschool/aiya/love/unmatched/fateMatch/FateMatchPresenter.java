package com.aiyaschool.aiya.love.unmatched.fateMatch;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

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
    public void commitCanRandom(boolean canRandom) {
        mModel.commitCanRandom(canRandom);
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
