package com.aiyaschool.aiya.me.postmessage;

import com.aiyaschool.aiya.bean.HttpResult;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wewarriors on 2017/5/13.
 */

public class PersonDataPresenter implements PersonDataContract.Presenter {

    private PersonDataContract.Model mModel;
    private PersonDataContract.View mView;

    public PersonDataPresenter(PersonDataContract.View view) {
        attach(view);
    }

    private void attach(PersonDataContract.View view) {
        mModel = new PersonDataModel();
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }

    @Override
    public void loadSchoolData(String hometown) {
        mModel.loadSchoolData(hometown, new Observer<HttpResult<List<String>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull HttpResult<List<String>> listHttpResult) {
                System.out.println("PersonDataPresenter" + listHttpResult.getState());
                mView.setSchoolData(listHttpResult.getData());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void updateUserData(final String height) {
        mModel.updateUserData(height, new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("PersonDataPresenter OnSubscribe");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                System.out.println("http" + httpResult.getState());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
