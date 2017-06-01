package com.aiyaschool.aiya.activity.otherDetail;

import android.content.Intent;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

interface OtherDetailContract {
    interface Model {
        void touch(String id, Observer<HttpResult> observer);

        void destroyLove(Observer<HttpResult> observer);

        void reply(String requestid, String fromuserid, String attitude, Observer<HttpResult<User>> observer);
    }

    interface View {
        void finishToMain(int result, Intent intent);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void touch(String id);

        void destroyLove();

        void reply(String requestid, String fromid, String attitude);
    }
}
