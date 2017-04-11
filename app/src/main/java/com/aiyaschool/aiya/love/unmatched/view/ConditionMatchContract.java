package com.aiyaschool.aiya.love.unmatched.view;

import java.util.List;


/**
 * Created by EGOISTK21 on 2017/3/27.
 */

public abstract class ConditionMatchContract {

    public interface View {
        void setSchoolData(List<String> data);
        void toastNetworkError();
    }

    public interface Presenter {

        void attachView(View view);

        void detachView();

        void loadSchoolData();

        interface OnDataListener {
            void onFailure();
            void onSuccess(List<String> data);
        }
    }

}
