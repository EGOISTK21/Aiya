package com.aiyaschool.aiya.love.matched.main;

import android.content.Context;

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

class MatchedPresenter implements MatchedContract.Presenter {

    private Context context;
    private MatchedContract.View view;

    MatchedPresenter(Context context, MatchedContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(MatchedContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

}
