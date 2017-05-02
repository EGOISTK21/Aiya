package com.aiyaschool.aiya.activity.splash;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/5/1.
 */

class SplashModel implements SplashContract.Model {
    @Override
    public void init(String phone, String loginToken, Observer<HttpResult<User>> observer) {
        APIUtil.getInitApi()
                .loadUser(phone, loginToken)
                .debounce(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
