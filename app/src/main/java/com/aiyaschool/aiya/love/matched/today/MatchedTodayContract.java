package com.aiyaschool.aiya.love.matched.today;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/5/25.
 */

interface MatchedTodayContract {

    interface Model {
        void loadIntimacy(Observer<Intimacy> observer);
    }

    interface View {
        void setIntimacy(String intimacy);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void getIntimacy();
    }
}
