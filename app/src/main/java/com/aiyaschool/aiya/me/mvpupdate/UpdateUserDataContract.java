package com.aiyaschool.aiya.me.mvpupdate;

import com.aiyaschool.aiya.bean.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by wewarriors on 2017/5/20.
 */

public interface UpdateUserDataContract {

    interface Model {
        void updateUserData(Map<String, String> map, Observer<HttpResult> observer);
    }

    interface View {
        void showUpdateSuccess();

        void getMeIndexAvatarUrl();
    }

    interface Presenter {
        void updateUserData(Map<String, String> map);

        void detach();
    }
}
