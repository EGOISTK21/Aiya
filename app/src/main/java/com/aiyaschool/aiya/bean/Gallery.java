package com.aiyaschool.aiya.bean;

import java.util.ArrayList;

/**
 * Created by wewarriors on 2017/5/29.
 */

public class Gallery {
    private String imgid;
    Img img;
    private String createtime;

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }
}

