package com.aiyaschool.aiya.activity.otherDetail;

import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/5/17.
 */

class OtherDetailModel implements OtherDetailContract.Model {
    @Override
    public void getImgWall(String userid, Observer<HttpResult<List<Gallery>>> observer) {
        APIUtil.getPhotoApi()
                .getPhoto(null, null, userid)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

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

    @Override
    public void destroyLove(Observer<HttpResult> observer) {
        APIUtil.getDestroyLoveApi()
                .destroyLove(UserUtil.getUser().getLoveId())
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
