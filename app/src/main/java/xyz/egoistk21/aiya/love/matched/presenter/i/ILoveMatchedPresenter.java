package xyz.egoistk21.aiya.love.matched.presenter.i;

import xyz.egoistk21.aiya.love.matched.view.ILoveMatchedView;


/**
 * Created by EGOISTK21 on 2017/3/23.
 */

public interface ILoveMatchedPresenter {
    void attachView(ILoveMatchedView loveMatchedView);

    void detachView();
}
