package com.egoistk.aiya.love.presenter;

import com.egoistk.aiya.love.view.ILoveView;

/**
 * Created by EGOISTK on 2017/3/17.
 */

public class LovePresenter implements ILovePresenter {

    private ILoveView iLoveView;

    public LovePresenter(ILoveView loveView) {
        setLoveView(loveView);
    }

    @Override
    public void setLoveView(ILoveView iLoveView) {
        this.iLoveView = iLoveView;
    }

    @Override
    public void changeSwitchsStatus(boolean isChecked, int index) {
        iLoveView.onChangeSwitchsStatus(isChecked, index);
    }
}
