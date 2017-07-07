package com.aiyaschool.aiya.love.matched.today;

import android.util.Log;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.Task;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by EGOISTK21 on 2017/5/25.
 */

class MatchedTodayPresenter implements MatchedTodayContract.Presenter {

    private static final String TAG = "MatchedTodayPresenter";
    private MatchedTodayContract.Model mModel;
    private MatchedTodayContract.View mView;

    MatchedTodayPresenter(MatchedTodayContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(MatchedTodayContract.View view) {
        this.mModel = new MatchedTodayModel();
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        this.mModel = null;
    }

//    @Override
//    public void getIntimacy(String loveid) {
//        mModel.loadIntimacy(loveid, new Observer<Intimacy>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Log.i(TAG, "onSubscribe: getIntimacy");
//            }
//
//            @Override
//            public void onNext(@NonNull Intimacy intimacy) {
//                Log.i(TAG, "onNext: getIntimacy " + intimacy);
//                if ("2000".equals(intimacy.getState())) {
//                    mView.setIntimacy(intimacy.getValue());
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.i(TAG, "onError: getIntimacy");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete: getIntimacy");
//            }
//        });
//    }

    @Override
    public void getTodayTask(final int period) {
        if (UserUtil.getPeriod() != period && UserUtil.getTask().size() != 0) {
            mView.setTodayTask(period, UserUtil.getTask());
        } else {
            mModel.loadTodayTask(period, new Observer<HttpResult<Task>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.i(TAG, "onSubscribe: getTodayTask " + d);
                }

                @Override
                public void onNext(@NonNull HttpResult<Task> taskHttpResult) {
                    Log.i(TAG, "onNext: getTodayTask " + taskHttpResult);
                    if ("2000".equals(taskHttpResult.getState())) {
                        List<String> result = taskHttpResult.getData().getTask();
                        List<String> task = new ArrayList<>();
                        Random random = new Random();
                        for (int i = 0; i < 7; i++) {
                            task.add(result.remove(random.nextInt(result.size())));
                        }
                        UserUtil.setTask(period, task);
                        mView.setTodayTask(period, task);
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.i(TAG, "onError: getTodayTask " + e);
                }

                @Override
                public void onComplete() {
                    Log.i(TAG, "onComplete: getTodayTask");
                }
            });
        }
    }
}
