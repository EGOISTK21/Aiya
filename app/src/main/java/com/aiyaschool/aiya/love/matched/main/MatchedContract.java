package com.aiyaschool.aiya.love.matched.main;


/**
 * Created by EGOISTK21 on 2017/3/23.
 */

interface MatchedContract {
    interface Model {

    }

    interface View {

    }

    interface Presenter {
        void attachView(View view);

        void detachView();
    }
}
