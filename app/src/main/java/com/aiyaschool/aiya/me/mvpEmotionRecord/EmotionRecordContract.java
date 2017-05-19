package com.aiyaschool.aiya.me.mvpEmotionRecord;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;

/**
 * Created by wewarriors on 2017/5/16.
 */

public interface EmotionRecordContract {
    interface Model {

        void getEmotionRecord(String sex, String page, String lines, Observer<HttpResult<ArrayList<EmotionRecordBean>>> observer);

    }

    interface View {
        void setEmotionRecordData(List<EmotionRecordBean> emotionRecordData);

        void setBackGroundIfNoData();
    }

    public interface Presenter {

        void getEmotionRecord(String sex, String page, String lines);

        void detach();
    }
}
