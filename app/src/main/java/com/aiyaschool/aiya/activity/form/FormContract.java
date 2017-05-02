package com.aiyaschool.aiya.activity.form;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * 表单MVP契约
 * Created by EGOISTK21 on 2017/5/2.
 */

interface FormContract {
    interface Model {
        void firstInit(String loginToken,
                       String phone,
                       String username,
                       String gender,
                       String school,
                       String age,
                       String height,
                       String constellation,
                       String hometown,
                       String hobby,
                       Observer<HttpResult<User>> observer);
    }

    interface View {
        void showPD();

        void dismissPD();

        void startMainView();
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void firstInit(String loginToken,
                       String phone,
                       String username,
                       String gender,
                       String school,
                       String age,
                       String height,
                       String constellation,
                       String hometown,
                       String hobby);
    }

}
