package com.aiyaschool.aiya.love.unmatched.view;

import java.util.List;


/**
 * Created by EGOISTK21 on 2017/3/27.
 */

public abstract class UnmatchedContract {

    public interface View {
        void setSchoolData(List<String> data);
    }

    public interface Presenter {
        void attachView(View loveView);

        void detachView();

        void loadSchoolData();

        interface OnDataListener {
            void onFailure();

            void onSuccess(final List<String> data);
        }
    }

}
