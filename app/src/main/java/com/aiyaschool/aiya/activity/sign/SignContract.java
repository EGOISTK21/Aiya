package com.aiyaschool.aiya.activity.sign;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * 登陆注册MVP契约
 * Created by EGOISTK21 on 2017/4/28.
 */

interface SignContract {
    interface Model {
        void sign(String phone, String verification, Observer<HttpResult<User>> observer);
    }

    interface View {
        void showPD();

        void dismissPD();

        void startFormView();

        void startMainView();
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void sign(String phone, String verification);
    }

}
