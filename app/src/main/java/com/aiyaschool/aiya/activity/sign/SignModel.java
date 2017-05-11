package com.aiyaschool.aiya.activity.sign;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登陆注册Model实现类
 * Created by EGOISTK21 on 2017/4/28.
 */

class SignModel implements SignContract.Model {
    @Override
    public void sign(String phone, String verification, Observer<HttpResult<User>> observer) {
        APIUtil.getVerificationInitApi()
                .loadUser(phone, verification)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
