package com.aiyaschool.aiya.activity.form;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.io.File;
import java.util.List;

import io.reactivex.Observer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 表单MVP契约
 * Created by EGOISTK21 on 2017/5/2.
 */

interface FormContract {
    interface Model {
        void submitAvatar(RequestBody img, Observer<ResponseBody> observer);

        void loadSchoolData(Observer<HttpResult<List<String>>> observer);

        void firstInit(String loginToken,
                       String phone,
                       String username,
                       String gender,
                       String school,
                       String age,
                       String height,
                       String constellation,
                       String character,
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

        void submitAvatar(File file);

        void loadSchoolData();

        void firstInit(String loginToken,
                       String phone,
                       String username,
                       String gender,
                       String school,
                       String age,
                       String height,
                       String constellation,
                       String character,
                       String hobby,
                       String avatar);
    }

}
