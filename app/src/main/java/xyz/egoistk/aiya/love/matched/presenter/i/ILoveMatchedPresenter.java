package xyz.egoistk.aiya.love.matched.presenter.i;

import xyz.egoistk.aiya.love.matched.view.i.ILoveMatchedView;

/**
 * Created by EGOISTK on 2017/3/23.
 */

public interface ILoveMatchedPresenter {
    void attachView(ILoveMatchedView loveMatchedView);

    void detachView();
}
