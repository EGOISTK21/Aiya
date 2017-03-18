package com.egoistk.aiya.love.presenter;

import com.egoistk.aiya.love.view.ILoveView;

/**
 * Created by EGOISTK on 2017/3/17.
 */

public interface ILovePresenter {
    void setLoveView(ILoveView loveView);
    void changeSwitchsStatus(boolean isChecked, int index);
}
