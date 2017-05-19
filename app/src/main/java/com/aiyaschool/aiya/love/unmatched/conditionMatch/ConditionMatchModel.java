package com.aiyaschool.aiya.love.unmatched.conditionMatch;

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

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

class ConditionMatchModel implements ConditionMatchContract.Model {

    List<String> schoolList = new ArrayList<>();

    @Override
    public boolean getContactShield() {
        return UserUtil.getContactShield();
    }

    @Override
    public void commitContactShield(boolean contactShield) {
        UserUtil.setContactShield(contactShield);
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
    public void startConditionMatch(String minHeight,
                                    String maxHeight,
                                    String age,
                                    String school,
                                    String character,
                                    String constellation,
                                    Observer<HttpResult<ArrayList<User>>> observer) {
        APIUtil.getMatchingApi()
                .startConditionMatch(minHeight, maxHeight, age, school, character, constellation, null, null)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

}
