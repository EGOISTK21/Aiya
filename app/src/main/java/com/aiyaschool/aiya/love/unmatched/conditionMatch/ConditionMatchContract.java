package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import com.aiyaschool.aiya.bean.HttpResult;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/3/27.
 */

interface ConditionMatchContract {

    interface Model {
        boolean getIsContactShield();

        void commitIsContactShield(boolean isContactShield);

        void loadSchoolData(Observer<HttpResult<List<String>>> observer);
    }

    interface View {
        void showPD();

        void dismissPD();

        void setIsContactShield(boolean isContactShield);

        void setSchoolData(List<String> data);

        void toastNetworkError();
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void initIsContactShield();

        void commitIsContactShield(boolean isContactShield);

        void loadSchoolData();

        interface OnServerReachableListener {
            void onFailure();

            void onSuccess(List<String> data);
        }
    }

}
