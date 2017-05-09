package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import android.content.Context;
import android.os.Handler;
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
    private Context context;
    private ConditionMatchContract.View mView;
    private ConditionMatchModel mModel;
    private Handler handler;

    ConditionMatchPresenter(Context context, ConditionMatchContract.View view) {
        this.context = context;
        attachView(view);
        mModel = new ConditionMatchModel();
        handler = new Handler();
    }

    @Override
    public void attachView(ConditionMatchContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void initIsContactShield() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mView != null) {
                    mView.setIsContactShield(mModel.getIsContactShield());
                }
            }
        });
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
