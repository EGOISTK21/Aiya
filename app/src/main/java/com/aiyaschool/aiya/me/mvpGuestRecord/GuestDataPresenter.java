package com.aiyaschool.aiya.me.mvpGuestRecord;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wewarriors on 2017/5/15.
 */

public class GuestDataPresenter implements GuestDataContract.Presenter {

    private static final String TAG = "GuestDataPresenter";

    private GuestDataContract.Model mModel;
    private GuestDataContract.View mView;

    public GuestDataPresenter(GuestDataContract.View view) {
        attach(view);
    }

    private void attach(GuestDataContract.View view) {
        mModel = new GuestDataModel();
        mView = view;
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
    public void detach() {
        mView = null;
        mModel = null;
    }
}
