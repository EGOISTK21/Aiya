package com.aiyaschool.aiya.activity.splash;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/5/1.
 */

interface SplashContract {
    interface Model {
        void init(String phone, String loginToken, Observer<HttpResult<User>> observer);
    }

    interface View {
        void startSignView();

        void startFormView();

        void startMainView();
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void init(String phone, String loginToken);

    }
}
