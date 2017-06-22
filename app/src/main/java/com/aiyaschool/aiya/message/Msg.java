package com.aiyaschool.aiya.message;

import com.aiyaschool.aiya.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XZY on 2017/3/8.
 * 消息单例
 */

public class Msg {
    private List<Msg> list ;

    private String mTitle = null;
    private String mTime = null;
    private String mPreview = null;
    private int mImageView;

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmPreview() {
        return mPreview;
    }

    public void setmPreview(String mPreview) {
        this.mPreview = mPreview;
    }

    public int getmImageView() {
        return mImageView;
    }

    public void setmImageView(int mImageView) {
        this.mImageView = mImageView;
    }

    public List<Msg> getMsgs2(){
        list = new ArrayList<>();
        Msg msg = new Msg();
        msg.setmTitle("情侣聊天");
        msg.setmImageView(R.drawable.message);
        list.add(msg);

        Msg msg1 = new Msg();
        msg1.setmTitle("消息通知");
        msg1.setmImageView(R.drawable.notice);
        list.add(msg1);

        Msg msg2 = new Msg();
        msg2.setmTitle("匹配请求");
        msg2.setmImageView(R.drawable.love_red);
        list.add(msg2);

        Msg msg3 = new Msg();
        msg3.setmTitle("攻略指南");
        msg3.setmImageView(R.drawable.bulb);
        list.add(msg3);

        return list;
    }
//
//    public Msg Msg() {
//        return new Msg();
//    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
