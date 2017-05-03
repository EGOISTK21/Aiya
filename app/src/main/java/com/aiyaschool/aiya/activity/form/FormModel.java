package com.aiyaschool.aiya.activity.form;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 表单Model实现类
 * Created by EGOISTK21 on 2017/5/2.
 */

class FormModel implements FormContract.Model {
    @Override
    public void loadSchoolData(Observer<HttpResult<List<String>>> observer) {
        APIUtil.getSearchSchoolApi()
                .loadSchoolData(null, null)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void firstInit(String loginToken,
                          String phone,
                          String username,
                          String gender,
                          String school,
                          String age,
                          String height,
                          String constellation,
                          String hometown,
                          String hobby,
                          Observer<HttpResult<User>> observer) {
        APIUtil.getFirstInitApi()
                .submitUser(loginToken, phone, username, gender, school,
                        age, height, constellation, hometown, hobby)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
