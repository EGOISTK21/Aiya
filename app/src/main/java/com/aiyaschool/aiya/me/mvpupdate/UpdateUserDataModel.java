package com.aiyaschool.aiya.me.mvpupdate;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wewarriors on 2017/5/20.
 */

public class UpdateUserDataModel implements UpdateUserDataContract.Model {

    @Override
    public void updateUserData(Map<String, String> map, Observer<HttpResult> observer) {
        APIUtil.getUpdateUserDataApi()
                .startUpdateUserData(map)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
