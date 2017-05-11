package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by EGOISTK21 on 2017/3/17.
 */

class ConditionMatchPresenter implements ConditionMatchContract.Presenter {

    private static final String TAG = "ConditionMatchPresenter";
    private ConditionMatchContract.View mView;
    private ConditionMatchModel mModel;

    ConditionMatchPresenter(ConditionMatchContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(ConditionMatchContract.View view) {
        this.mModel = new ConditionMatchModel();
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mModel = null;
    }

    @Override
    public void initIsContactShield() {
        // TODO: 2017/5/11 本地存储通讯录匹配开关
        mView.setIsContactShield(true);
    }

    @Override
    public void commitIsContactShield(boolean isContactShield) {
        mModel.commitIsContactShield(isContactShield);
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
}
