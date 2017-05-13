package com.aiyaschool.aiya.me.postmessage;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by wewarriors on 2017/5/7.
 */

public interface PersonDataContract {

    interface Model {
        void loadSchoolData(String hometown, Observer<HttpResult<List<String>>> observer);

        void updateUserData(String height, Observer<HttpResult> observer);
    }

    interface View {

        void setSchoolData(List<String> schools);

    }

    public interface Presenter {

        void loadSchoolData(String hometown);

        void updateUserData(String height);
        void detach();


    }

}
