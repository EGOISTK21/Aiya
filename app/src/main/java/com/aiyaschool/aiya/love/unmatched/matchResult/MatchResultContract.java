package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.graphics.Bitmap;

/**
 * Created by EGOISTK21 on 2017/4/19.
 */

interface MatchResultContract {
    interface Model {
        MatchResultModel getData(Presenter.OnServerReachableListener listener);
    }

    interface View {
        void setData(Bitmap bm, String nick, String school);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void loadData();

        interface OnServerReachableListener {
            void onFailure();

            void onSuccess();
        }
    }

}
