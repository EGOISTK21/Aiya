package com.aiyaschool.aiya.love.unmatched.randomMatch;

import android.content.Context;
import android.os.Handler;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

public class RandomMatchPresenter implements RandomMatchContract.Presenter {

    private Context context;
    private RandomMatchContract.View view;
    private RandomMatchContract.Model model;
    private Handler handler;

    RandomMatchPresenter(Context context, RandomMatchContract.View view) {
        this.context = context;
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
                view.setCanRandom(model.getCanRandom(new OnServerReachableListener() {
                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onSuccess() {

                    }
                }));
            }
        });
    }

    @Override
    public void commitCanRandom(boolean canRandom) {
        model.commitCanRandom(canRandom);
    }
}
