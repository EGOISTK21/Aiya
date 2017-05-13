package com.aiyaschool.aiya.me.postmessage;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wewarriors on 2017/5/13.
 */

public class PersonDataModel implements PersonDataContract.Model {
    @Override
    public void loadSchoolData(String hometown, Observer<HttpResult<List<String>>> observer) {
        APIUtil.getSearchSchoolApi()
                .loadSchoolData(null, hometown)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void updateUserData(String height, Observer<HttpResult> observer) {
        APIUtil.getUpdateUserApi()
                .startUpdateUser(height)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    @Override
    public void getGuestRecord(String page, String lines, Observer<HttpResult<ArrayList<OuInfo>>> observer) {
        APIUtil.getGuestRecordApi()
                .startGetGuestRecord(page, lines)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
