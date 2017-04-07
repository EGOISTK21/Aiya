package xyz.egoistk21.aiya.love.matched.presenter.impl;

import xyz.egoistk21.aiya.love.matched.presenter.i.ILoveMatchedPresenter;
import xyz.egoistk21.aiya.love.matched.view.ILoveMatchedView;


/**
 * Created by EGOISTK21 on 2017/3/23.
 */

public class LoveMatchedPresenter implements ILoveMatchedPresenter {

    private ILoveMatchedView iLoveMatchedView;


    public LoveMatchedPresenter(ILoveMatchedView loveMatchedView) {
        attachView(loveMatchedView);
    }

    @Override
    public void attachView(ILoveMatchedView loveMatchedView) {
        this.iLoveMatchedView = loveMatchedView;
    }

    @Override
    public void detachView() {
        this.iLoveMatchedView = null;
    }

}
