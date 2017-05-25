package com.aiyaschool.aiya.love.matched.today;

import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/5/25.
 */

class MatchedTodayModel implements MatchedTodayContract.Model {
    @Override
    public void loadIntimacy(Observer<Intimacy> observer) {
        APIUtil.getIntimacyApi()
                .getIntimacy(UserUtil.getUser().getLoveId())
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
