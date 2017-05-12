package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.DBUtil;

import java.util.ArrayList;
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
    public boolean getContactShield() {
        return DBUtil.getContactShield();
    }

    @Override
    public void commitContactShield(boolean contactShield) {
        DBUtil.setContactShield(contactShield);
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

    @Override
    public void startConditionMatch(String minHeight,
                                    String maxHeight,
                                    String minAge,
                                    String maxAge,
                                    String school,
                                    String character,
                                    String constellation,
                                    Observer<HttpResult<ArrayList<User>>> observer) {
        APIUtil.getMatchingApi()
                .startConditionMatch(minHeight, maxHeight, minAge, maxAge, school, character, constellation, null, null)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

}
