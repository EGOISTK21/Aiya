package com.aiyaschool.aiya.me.bean;

/**
 * Created by wewarrios on 2017/3/18.
 */

public class GuestItem {
    //存放Guest的照片的id
    private int id;
    //姓名
    private String name;
    //学校
    private String school;
    //时间
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
