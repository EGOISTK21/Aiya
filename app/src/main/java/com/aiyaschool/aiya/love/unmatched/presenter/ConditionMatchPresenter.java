package com.aiyaschool.aiya.love.unmatched.presenter;

import android.content.Context;
import android.os.Handler;

import com.aiyaschool.aiya.love.unmatched.bean.UnmatchedModel;
import com.aiyaschool.aiya.love.unmatched.view.ConditionMatchContract;
import com.aiyaschool.aiya.util.OkHttpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/3/17.
 */

public class ConditionMatchPresenter implements ConditionMatchContract.Presenter {

    private Context context;
    private ConditionMatchContract.View view;
    private UnmatchedModel model;
    private Handler handler;

    public ConditionMatchPresenter(Context context, ConditionMatchContract.View view) {
        this.context = context;
        attachView(view);
        model = new UnmatchedModel();
        handler = new Handler();
    }

    @Override
    public void attachView(ConditionMatchContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadSchoolData() {
        if (OkHttpUtil.isNetworkReachable(context)) {
            model.getSchoolData(new OnDataListener() {
                @Override
                public void onFailure() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.toastNetworkError();
                        }
                    });
                }

                @Override
                public void onSuccess(final List<String> data) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (data.isEmpty()) {
                                data.add("没有什么大学");
                                view.toastNetworkError();
                            }
                            view.setSchoolData(data);
                        }
                    });
                }
            });
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    view.toastNetworkError();
                    view.setSchoolData(new ArrayList<>(Arrays.asList("没有什么大学")));
                }
            });
        }
    }
}
