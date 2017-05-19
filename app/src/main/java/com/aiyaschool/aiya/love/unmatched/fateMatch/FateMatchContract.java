package com.aiyaschool.aiya.love.unmatched.fateMatch;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

interface FateMatchContract {
    interface Model {
        void commitFateSwitch(String isFated, Observer<HttpResult> observer);

        void startFateMatch(Observer<HttpResult<User>> observer);
    }


    interface View {
        void setFateSwitch(boolean isFated);

        void fate(User user);
    }

    interface Presenter {
        void attachView(FateMatchContract.View view);

        void detachView();

        void initFateSwitch();

        void commitFateSwitch(boolean isFated);

        void startFateMatch();
    }
}
