package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by EGOISTK21 on 2017/3/17.
 */

class ConditionMatchPresenter implements ConditionMatchContract.Presenter {

    private static final String TAG = "ConditionMatchPresenter";
    private ConditionMatchContract.View mView;
    private ConditionMatchContract.Model mModel;

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
    public void initContactShield() {
        mView.setContactShield(mModel.getContactShield());
    }

    @Override
    public void commitContactShield(boolean contactShield) {
        mModel.commitContactShield(contactShield);
    }

    @Override
    public void loadSchoolData(String province) {
        mView.setSchoolData(mModel.loadSchoolData(province));
    }

    @Override
    public void startConditionMatch(String height,
                                    String age,
                                    String school,
                                    String character,
                                    String constellation) {
        String minHeight, maxHeight;
        if (height == null) {
            minHeight = null;
            maxHeight = null;
        } else {
            switch (height.length()) {
                case 5:
                    minHeight = null;
                    maxHeight = "149";
                    break;
                case 6:
                    minHeight = "190";
                    maxHeight = null;
                    break;
                default:
                    minHeight = height.substring(0, 3);
                    maxHeight = height.substring(4, 7);
                    break;
            }
        }
        mModel.startConditionMatch(minHeight, maxHeight, age, school, character, constellation,
                new Observer<HttpResult<ArrayList<User>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: startConditionMatch");
                        mView.showPD();
                    }

                    @Override
                    public void onNext(@NonNull HttpResult<ArrayList<User>> listHttpResult) {
                        Log.i(TAG, "onNext: startConditionMatch " + listHttpResult);
                        mView.dismissPD();
                        if ("2000".equals(listHttpResult.getState())) {
                            mView.showMatchResult(listHttpResult.getData());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: startConditionMatch");
                        mView.dismissPD();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: startConditionMatch");
                    }
                });
    }
}
