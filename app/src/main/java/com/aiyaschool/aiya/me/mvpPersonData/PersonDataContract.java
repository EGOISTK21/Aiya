package com.aiyaschool.aiya.me.mvpPersonData;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wewarriors on 2017/5/7.
 */

public interface PersonDataContract {

    interface Model {

        void submitAvatar(RequestBody img, Observer<ResponseBody> observer);

        void loadSchoolData(String hometown, Observer<HttpResult<List<String>>> observer);

        void updateUserHeight(String height, Observer<HttpResult> observer);

        void getMeIndex(String demand, Observer<HttpResult<User>> observer);

    }

    interface View {

        void setSchoolData(List<String> schools);

        void showGetMeIndex(User user);

        void showSubmitAvatar();
    }

    public interface Presenter {

        void loadSchoolData(String hometown);

        void updateUserHeight(String height);

        void getMeIndex(String demand);

        void submitAvatar(File file);

        void detach();


    }

}
