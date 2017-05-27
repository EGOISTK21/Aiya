package com.aiyaschool.aiya.bean;

/**
 * Created by wewarriors on 2017/5/24.
 */

public class UploadUrl {
    private String upurl;
    private String imgname;

    public String getUpurl() {
        return upurl;
    }

    public void setUpurl(String upurl) {
        this.upurl = upurl;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    @Override
    public String toString() {
        return "UploadUrl{" +
                "upurl='" + upurl + '\'' +
                ", imgname='" + imgname + '\'' +
                '}';
    }
}
