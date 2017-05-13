package com.aiyaschool.aiya.love.unmatched.fateMatch;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

interface FateMatchContract {
    interface Model {
        boolean getCanRandom();

        void commitCanRandom(boolean canRandom);

        void startFateMatch(Observer<HttpResult<User>> observer);
    }


    interface View {
        void setCanRandom(boolean canRandom);

        void fate();
    }

    interface Presenter {
        void attachView(FateMatchContract.View view);

        void detachView();

        void initCanRandom();

        void commitCanRandom(boolean canRandom);

        void startFateMatch();
    }
}
