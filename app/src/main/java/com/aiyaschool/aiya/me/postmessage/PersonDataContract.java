package com.aiyaschool.aiya.me.postmessage;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by wewarriors on 2017/5/7.
 */

interface PersonDataContract {

    interface Model {
        void loadSchoolData(Observer<HttpResult<List<String>>> observer);

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
                       String avatar,
                       Observer<HttpResult<User>> observer);
    }

    interface View {
        void showPD();

        void dismissPD();

        void setSchoolData(List<String> schools);

        void startMainView();
    }

    interface Presenter {
        void attach(View view);

        void detach();

        void loadSchoolData();

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
                       String avatar);
    }

}
