package com.aiyaschool.aiya.me.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by wewarrios on 2017/3/28.
 */

public class ImagePathItem extends DataSupport{

    private String date;

    private List<String> imagePath;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }
}
