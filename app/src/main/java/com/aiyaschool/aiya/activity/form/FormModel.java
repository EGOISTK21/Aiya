package com.aiyaschool.aiya.activity.form;

import android.database.Cursor;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.SchoolDBHelper;
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
 * 表单Model实现类
 * Created by EGOISTK21 on 2017/5/2.
 */

class FormModel implements FormContract.Model {

    List<String> schoolList = new ArrayList<>();

    @Override
    public void submitAvatar(RequestBody img, Observer<ResponseBody> observer) {
        APIUtil.getIMGApi()
                .submitIMG(UserUtil.getUser().getUpLoad().getUpurl(), img)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public List<String> loadSchoolData(String province) {
        schoolList.clear();
        String sql = "SELECT school FROM edu WHERE province = ?";
        Cursor cursor = SchoolDBHelper.getDBInstance().rawQuery(sql, new String[]{province});
        while (cursor.moveToNext()) {
            schoolList.add(cursor.getString(0));
        }
        return schoolList;
    }

    @Override
    public void firstInit(String loginToken,
                          String phone,
                          String username,
                          String gender,
                          String school,
                          String age,
                          String height,
                          String constellation,
                          String hometown,
                          String hobby,
                          String avatar,
                          Observer<HttpResult<User>> observer) {
        APIUtil.getFirstInitApi()
                .submitUser(loginToken, phone, username, gender, school,
                        age, height, constellation, hometown, hobby, avatar)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
