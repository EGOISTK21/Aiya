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

//    private UUID mId;
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

    //    匹配后
    public List<Msg> getMsgs(){
        list = new ArrayList<>();

        Msg msg = new Msg();
        msg.setmTitle("情侣聊天");
//        此处从后台取得头像
        msg.setmImageView(R.drawable.message);
        msg.setmPreview("1 分钟前");
        msg.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg);

        Msg msg1 = new Msg();
        msg1.setmTitle("消息通知");
        msg1.setmImageView(R.drawable.notice);
        msg1.setmPreview("1 分钟前");
        msg1.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg1);

        Msg msg2 = new Msg();
        msg2.setmTitle("任务消息");
        msg2.setmImageView(R.drawable.task);
        msg2.setmPreview("1 分钟前");
        msg2.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg2);

        Msg msg3 = new Msg();
        msg3.setmTitle("攻略指南");
        msg3.setmImageView(R.drawable.bulb);
        msg3.setmPreview("1 分钟前");
        msg3.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg3);

        return list;
    }
    //匹配前
    public List<Msg> getMsgs2(){
        list = new ArrayList<>();
        Msg msg = new Msg();
        msg.setmTitle("情侣聊天");
        msg.setmImageView(R.drawable.message);
        msg.setmPreview("1 分钟前");
        msg.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg);

        Msg msg1 = new Msg();
        msg1.setmTitle("消息通知");
        msg1.setmImageView(R.drawable.notice);
        msg1.setmPreview("1 分钟前");
        msg1.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg1);

        Msg msg2 = new Msg();
        msg2.setmTitle("任务消息");
        msg2.setmImageView(R.drawable.love_red);
        msg2.setmPreview("1 分钟前");
        msg2.setmPreview("哈哈哈哈哈哈哈");
        list.add(msg2);

        Msg msg3 = new Msg();
        msg3.setmTitle("攻略指南");
        msg3.setmImageView(R.drawable.bulb);
        msg3.setmPreview("1 分钟前");
        msg3.setmPreview("哈哈哈哈哈哈哈");
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
