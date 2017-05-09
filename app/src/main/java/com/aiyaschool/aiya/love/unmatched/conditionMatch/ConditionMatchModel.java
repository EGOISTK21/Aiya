package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

class ConditionMatchModel implements ConditionMatchContract.Model {

    @Override
    public boolean getIsContactShield() {
        return true;
    }

    @Override
    public void commitIsContactShield(boolean isContactShield) {

    }

    @Override
    public void loadSchoolData(Observer<HttpResult<List<String>>> observer) {
        APIUtil.getSearchSchoolApi()
                .loadSchoolData(null, "陕西")
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

}
