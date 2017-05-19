package com.aiyaschool.aiya.love.unmatched.matchResult;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

interface MatchResultContract {

    interface Model {
        void loadOtherDetail(String id, Observer<HttpResult<User>> observer);
    }

    interface View {
        void startOtherDetailActivity(User user);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void getOtherDetail(String id);
    }

}
