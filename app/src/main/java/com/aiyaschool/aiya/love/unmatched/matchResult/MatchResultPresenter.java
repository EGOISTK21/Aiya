package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.content.Context;
import android.os.Handler;

/**
 * Created by EGOISTK21 on 2017/4/19.
 */

class MatchResultPresenter implements MatchResultContract.Presenter {

    private Context context;
    private MatchResultContract.Model model;
    private MatchResultContract.View view;
    private Handler handler;

    MatchResultPresenter(Context context, MatchResultContract.View view) {
        this.context = context;

        attachView(view);
        handler = new Handler();
    }

    @Override
    public void attachView(MatchResultContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadData() {
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
