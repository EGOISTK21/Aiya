package com.aiyaschool.aiya.message.hit;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by EGOISTK21 on 2017/6/7.
 */

class HitListPresenter implements HitListContract.Presenter {

    private static final String TAG = "HitListPresenter";
    private HitListContract.Model mModel;
    private HitListContract.View mView;

    HitListPresenter(HitListContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(HitListContract.View view) {
        this.mModel = new HitListModel();
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mModel = null;
        this.mView = null;
    }

    @Override
    public void getGuestRecord(String page, String lines) {
        mModel.getGuestRecord(page, lines, new Observer<HttpResult<ArrayList<OuInfo>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull HttpResult<ArrayList<OuInfo>> arrayListHttpResult) {
                Log.d(TAG, "onNext: " + arrayListHttpResult.getState());
                Log.d(TAG, "onNext: rows" + arrayListHttpResult.getRows());
                Log.d(TAG, "onNext: data" + arrayListHttpResult.getData().size());
                for (OuInfo o : arrayListHttpResult.getData()) {
                    System.out.println(o.getSchool());
                    System.out.println(o.getUsername());
                }

                int rows = Integer.parseInt(arrayListHttpResult.getRows());
                if (rows == 0) {
                    mView.setBackGroundIfNoData();
                } else {
                    mView.setGuestDataNum(rows);
                    mView.setGuestRecordData(arrayListHttpResult.getData());
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

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
                    //mView.finishToMain(RESULT_OK, null);
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
