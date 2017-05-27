package com.aiyaschool.aiya.me.mvpPersonData;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.bean.MyAvatar;
import com.aiyaschool.aiya.me.mvpGuestRecord.GuestDataContract;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wewarriors on 2017/5/13.
 */

public class PersonDataModel implements PersonDataContract.Model {

    @Override
    public void submitAvatar(String url, RequestBody img, Observer<ResponseBody> observer) {
        APIUtil.getIMGApi()
                .submitIMG(url, img)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

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
    public void updateUserHeight(String height, Observer<HttpResult> observer) {
        APIUtil.getUpdateUserApi()
                .startUpdateUser(height)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);

    }

    @Override
    public void getMeIndex(String demand, Observer<HttpResult<User>> observer) {
        APIUtil.getMeIndexApi()
                .startGetMeIndex(demand)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void getMeIndexAvatar(String demand, Observer<HttpResult<MyAvatar>> observer) {
        APIUtil.getMeIndexAvatarApi()
                .startGetMeIndexAvatar(demand)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void getAvatarUploadUrl(Observer<HttpResult<UploadUrl>> observer) {
        APIUtil.getAvatarUploadUrlApi()
                .startGetAvatarUploadUrl()
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }


}
