package com.aiyaschool.aiya.activity.OtherDetail;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

class OtherDetailModel implements OtherDetailContract.Model {
    @Override
    public void touch(String id, Observer<HttpResult> observer) {
        APIUtil.getTouchApi()
                .touch(id)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
