package com.aiyaschool.aiya.love.matched.today;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.Task;
import com.aiyaschool.aiya.bean.User;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/5/25.
 */

interface MatchedTodayContract {

    interface Model {
        void loadLover(Observer<HttpResult<User>> observer);

        void loadIntimacy(String loveid, Observer<Intimacy> observer);

        void loadTodayTask(String period, Observer<HttpResult<Task>> observer);
    }

    interface View {
        void setLover();

        void setIntimacy(String intimacy);

        void setTodayTask(List<String> todayTask);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void initLover();

        void getIntimacy(String loveid);

        void getTodayTask(String period);
    }
}
