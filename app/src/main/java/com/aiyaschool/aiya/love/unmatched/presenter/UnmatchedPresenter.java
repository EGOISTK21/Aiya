package com.aiyaschool.aiya.love.unmatched.presenter;

import android.os.Handler;

import com.aiyaschool.aiya.love.unmatched.bean.UnmatchedModel;
import com.aiyaschool.aiya.love.unmatched.view.UnmatchedContract;

import java.util.List;


/**
 * Created by EGOISTK21 on 2017/3/17.
 */

public class UnmatchedPresenter implements UnmatchedContract.Presenter {

    private UnmatchedContract.View view;
    private UnmatchedModel model;
    private Handler handler;

    public UnmatchedPresenter(UnmatchedContract.View loveView) {
        attachView(loveView);
        model = new UnmatchedModel();
        handler = new Handler();
    }

    @Override
    public void attachView(UnmatchedContract.View loveView) {
        this.view = loveView;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadSchoolData() {
        model.getSchoolData(new OnDataListener() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(final List<String> data) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setSchoolData(data);
                    }
                });
            }
        });
    }
}
