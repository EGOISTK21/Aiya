package com.aiyaschool.aiya.love.unmatched.randomMatch;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

class RandomMatchModel implements RandomMatchContract.Model {

    @Override
    public boolean getCanRandom(RandomMatchContract.Presenter.OnServerReachableListener listener) {
        return true;
    }

    @Override
    public void commitCanRandom(boolean canRandom) {

    }

}
