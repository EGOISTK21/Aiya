package com.aiyaschool.aiya.activity.otherDetail;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
}
