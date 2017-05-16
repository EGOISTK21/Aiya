package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/3/27.
 */

interface ConditionMatchContract {

    interface Model {
        boolean getContactShield();

        void commitContactShield(boolean contactShield);

        List<String> loadSchoolData(String province);

        void startConditionMatch(String minHeight,
                                 String maxHeight,
                                 String age,
                                 String school,
                                 String character,
                                 String constellation,
                                 Observer<HttpResult<ArrayList<User>>> observer);
    }

    interface View {
        void showPD();

        void dismissPD();

        void setContactShield(boolean contactShield);

        void setSchoolData(List<String> data);

        void showMatchResult(ArrayList<User> users);

    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void initContactShield();

        void commitContactShield(boolean contactShield);

        void loadSchoolData(String province);

        void startConditionMatch(String height,
                                 String age,
                                 String school,
                                 String character,
                                 String constellation);
    }

}
