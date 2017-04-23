package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import java.util.List;

/**
 * Created by EGOISTK21 on 2017/3/27.
 */

interface ConditionMatchContract {

    interface Model {
        boolean getIsContactShield();

        void commitIsContactShield(boolean isContactShield);

        void getSchoolData(Presenter.OnServerReachableListener listener);
    }

    interface View {
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
