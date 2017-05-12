package com.aiyaschool.aiya.love.unmatched.randomMatch;

import android.os.Handler;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

class RandomMatchPresenter implements RandomMatchContract.Presenter {

    private RandomMatchContract.View view;
    private RandomMatchContract.Model model;
    private Handler handler;

    RandomMatchPresenter(RandomMatchContract.View view) {
        attachView(view);
        model = new RandomMatchModel();
        handler = new Handler();
    }

    @Override
    public void attachView(RandomMatchContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void initCanRandom() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.setCanRandom(model.getCanRandom());
            }
        });
    }

    @Override
    public void commitCanRandom(boolean canRandom) {
        model.commitCanRandom(canRandom);
    }
}
