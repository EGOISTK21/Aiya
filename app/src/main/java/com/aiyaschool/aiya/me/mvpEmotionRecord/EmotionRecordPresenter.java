package com.aiyaschool.aiya.me.mvpEmotionRecord;

import android.util.Log;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wewarriors on 2017/5/16.
 */

public class EmotionRecordPresenter implements EmotionRecordContract.Presenter {

    private static final String TAG = "EmotionRecordPresenter";

    private EmotionRecordContract.Model mModel;
    private EmotionRecordContract.View mView;

    public EmotionRecordPresenter(EmotionRecordContract.View view) {
        attach(view);
    }

    private void attach(EmotionRecordContract.View view) {
        mModel = new EmotionRecordModel();
        mView = view;
    }


    @Override
    public void getEmotionRecord(String sex, String page, String lines) {
        mModel.getEmotionRecord(sex, page, lines, new Observer<HttpResult<ArrayList<EmotionRecordBean>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: getEmotionRecord");
            }

            @Override
            public void onNext(@NonNull HttpResult<ArrayList<EmotionRecordBean>> arrayListHttpResult) {
                Log.d(TAG, "onNext: getEmotionRecord" + arrayListHttpResult.getState());
                Log.d(TAG, "onNext: getEmotionRecord" + arrayListHttpResult.getData().size());
                for (EmotionRecordBean e : arrayListHttpResult.getData()) {
                    System.out.println(e);
                }
                int rows = Integer.parseInt(arrayListHttpResult.getRows());
                Log.d(TAG, "onNext: rows" + rows);
                if (rows == 0) {
                    mView.setBackGroundIfNoData();
                } else {
                    mView.setEmotionRecordNum(rows);
                    mView.setEmotionRecordData(arrayListHttpResult.getData());

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.setBackGroundIfNoData();
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
