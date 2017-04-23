package com.aiyaschool.aiya.love.unmatched.randomMatch;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

interface RandomMatchContract {

    interface Model {
        boolean getCanRandom(Presenter.OnServerReachableListener listener);

        void commitCanRandom(boolean canRandom);
    }


    interface View {
        void setCanRandom(boolean canRandom);
    }

    interface Presenter {
        void attachView(RandomMatchContract.View view);

        void detachView();

        void initCanRandom();

        void commitCanRandom(boolean canRandom);

        interface OnServerReachableListener {
            void onFailure();

            void onSuccess();
        }
    }
}
