package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static com.aiyaschool.aiya.love.unmatched.matchResult.MatchResultContract.*;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

class MatchResultPresenter implements Presenter {

    private static final String TAG = "MatchResultPresenter";
    private Model mModel;
    private View mView;

    MatchResultPresenter(View view) {
        attachView(view);
    }

    @Override
    public void attachView(View view) {
        this.mModel = new MatchResultModel();
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void getOtherDetail(String id) {
        mModel.loadOtherDetail(id, new Observer<HttpResult<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: getOtherDetail");
            }

            @Override
            public void onNext(@NonNull HttpResult<User> userHttpResult) {
                Log.i(TAG, "onNext: getOtherDetail " + userHttpResult);
                if ("2000".equals(userHttpResult.getState())) {
                    mView.startOtherDetailActivity(userHttpResult.getData());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: getOtherDetail");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: getOtherDetail");
            }
        });
    }
}
