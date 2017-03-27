package xyz.egoistk.aiya.love.unmatched.view;

import java.util.List;

/**
 * Created by EGOISTK on 2017/3/27.
 */

public abstract class UnmatchedContract {

    public interface View {
        void setSchoolMajorData(List<String> data);
    }

    public interface Presenter {
        void attachLoveUnmatchedView(View loveView);

        void detachLoveUnmatchedView();

        void getSchoolMajorData();

        interface OnDataListener {
            void onFailure();

            void onSuccess(List<String> data);
        }
    }

}
