package com.aiyaschool.aiya.activity.otherDetail;

import com.aiyaschool.aiya.bean.HttpResult;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

interface OtherDetailContract {
    interface Model {
        void touch(String id, Observer<HttpResult> observer);
    }

    interface View {

    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void touch(String id);
    }
}
