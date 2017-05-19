package com.aiyaschool.aiya.love.unmatched.fateMatch;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/4/18.
 */

class FateMatchModel implements FateMatchContract.Model {

    @Override
    public void commitFateSwitch(String canRandom, Observer<HttpResult> observer) {
        APIUtil.getFateSwitchApi()
                .setFateSwitch(canRandom)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void startFateMatch(Observer<HttpResult<User>> observer) {
        APIUtil.getFateMatchingApi()
                .startFateMatch()
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

}
