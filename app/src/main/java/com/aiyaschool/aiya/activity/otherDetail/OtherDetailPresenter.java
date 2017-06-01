package com.aiyaschool.aiya.activity.otherDetail;

import android.content.Intent;
import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

class OtherDetailPresenter implements OtherDetailContract.Presenter {

    private static final String TAG = "OtherDetailPresenter";
    private OtherDetailContract.Model mModel;
    private OtherDetailContract.View mView;


    OtherDetailPresenter(OtherDetailContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(OtherDetailContract.View view) {
        this.mModel = new OtherDetailModel();
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void touch(String id) {
        mModel.touch(id, new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: touch");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.i(TAG, "onNext: touch " + httpResult);
                if ("2000".equals(httpResult.getState())) {
                    ToastUtil.show("七天恋爱请求成功");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: touch");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: touch");
            }
        });
    }

    @Override
    public void destroyLove() {
        mModel.destroyLove(new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: destroyLove");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.i(TAG, "onNext: destroyLove " + httpResult);
                if ("2000".equals(httpResult.getState())) {
                    UserUtil.setLoveId("0");
                    mView.finishToMain(RESULT_OK, new Intent().putExtra("flag", "destroyLove"));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: destroyLove");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: destroyLove");
            }
        });
    }

    @Override
    public void reply(String requestid, String fromuserid, String attitude) {
        mModel.reply(requestid, fromuserid, attitude, new Observer<HttpResult<User>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe: reply " + d);
            }

            @Override
            public void onNext(@NonNull HttpResult<User> userHttpResult) {
                Log.i(TAG, "onNext: reply " + userHttpResult);
                if ("2000".equals(userHttpResult.getState())) {
                    User ta = userHttpResult.getData();
                    if (ta != null) {
                        UserUtil.setTa(ta);
                        UserUtil.setLoveId(ta.getLoveId());
                    }
                    mView.finishToMain(RESULT_OK, null);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError: reply" + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: reply");
            }
        });
    }

}
