package com.aiyaschool.aiya.me.mvpEmotionRecord;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wewarriors on 2017/5/16.
 */

public class EmotionRecordModel implements EmotionRecordContract.Model {
    @Override
    public void getEmotionRecord(String sex, String page, String lines, Observer<HttpResult<ArrayList<EmotionRecordBean>>> observer) {
        APIUtil.getEmotionRecordApi()
                .startGetEmotionRecord(sex, page, lines)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
