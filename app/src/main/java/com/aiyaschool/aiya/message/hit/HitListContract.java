package com.aiyaschool.aiya.message.hit;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.bean.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

/**
 * Created by EGOISTK21 on 2017/6/7.
 */

interface HitListContract {
    interface Model {
        void getGuestRecord(String page, String lines, Observer<HttpResult<ArrayList<OuInfo>>> observer);

        void reply(String requestid, String fromuserid, String attitude, Observer<HttpResult<User>> observer);
    }

    interface View {
        void setGuestRecordData(List<OuInfo> guestItem);

        void setBackGroundIfNoData();

        void setGuestDataNum(int num);
    }

    interface Presenter {
        void attachView(View view);

        void detachView();

        void getGuestRecord(String page, String lines);

        void reply(String requestid, String fromid, String attitude);
    }
}
