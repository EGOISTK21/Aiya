package com.aiyaschool.aiya.message.hit;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/6/7.
 */

class HitListModel implements HitListContract.Model {
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

    @Override
    public void reply(String requestid, String fromuserid, String attitude, Observer<HttpResult<User>> observer) {
        APIUtil.getReplyApi()
                .response(requestid, fromuserid, attitude)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
