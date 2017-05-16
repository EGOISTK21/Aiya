package com.aiyaschool.aiya.me.mvpGuestRecord;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wewarriors on 2017/5/15.
 */

public class GuestDataModel implements GuestDataContract.Model {
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
