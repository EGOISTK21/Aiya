package xyz.egoistk21.aiya.love.unmatched.presenter;

import android.os.Handler;

import java.util.List;

import xyz.egoistk21.aiya.love.unmatched.bean.UnmatchedModel;
import xyz.egoistk21.aiya.love.unmatched.view.UnmatchedContract;


/**
 * Created by EGOISTK21 on 2017/3/17.
 */

public class UnmatchedPresenter implements UnmatchedContract.Presenter {

    private UnmatchedContract.View view;
    private UnmatchedModel model;
    private Handler handler;

    public UnmatchedPresenter(UnmatchedContract.View loveView) {
        attachLoveUnmatchedView(loveView);
        model = new UnmatchedModel();
        handler = new Handler();
    }

    @Override
    public void attachLoveUnmatchedView(UnmatchedContract.View loveView) {
        this.view = loveView;
    }

    @Override
    public void detachLoveUnmatchedView() {
        this.view = null;
    }

    @Override
    public void loadSchoolMajorData() {
        model.getSchoolMajorData(new OnDataListener() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(final List<String> data) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setSchoolMajorData(data);
                    }
                });
            }
        });
    }
}
