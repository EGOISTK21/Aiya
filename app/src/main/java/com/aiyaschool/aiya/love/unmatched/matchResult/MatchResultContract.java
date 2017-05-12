package com.aiyaschool.aiya.love.unmatched.matchResult;

/**
 * Created by EGOISTK21 on 2017/4/19.
 */

interface MatchResultContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void loadData();
    }

}
