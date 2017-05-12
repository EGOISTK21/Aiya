package com.aiyaschool.aiya.love.unmatched.matchResult;

/**
 * Created by EGOISTK21 on 2017/4/19.
 */

class MatchResultPresenter implements MatchResultContract.Presenter {

    private MatchResultContract.Model mModel;
    private MatchResultContract.View mView;

    MatchResultPresenter(MatchResultContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(MatchResultContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void loadData() {

    }
}
