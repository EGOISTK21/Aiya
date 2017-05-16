package com.aiyaschool.aiya.me.mvpGuestRecord;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

/**
 * Created by wewarriors on 2017/5/15.
 */

public interface GuestDataContract {

    interface Model {
        void getGuestRecord(String page, String lines, Observer<HttpResult<ArrayList<OuInfo>>> observer);
    }

    interface View {
        void setGuestRecordData(List<OuInfo> guestItem);

        void setBackGroundIfNoData();
    }

    public interface Presenter {
        void getGuestRecord(String page, String lines);

        void detach();
    }


}
