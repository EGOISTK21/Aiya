package com.aiyaschool.aiya.bean;

import java.util.ArrayList;

/**
 * Created by wewarriors on 2017/5/29.
 */

public class Gallery {
    Img img;
    private String createtime;
}

class Img {
    private ArrayList<String> normal;
    private ArrayList<String> thumb;

    public ArrayList<String> getNormal() {
        return normal;
    }

    public void setNormal(ArrayList<String> normal) {
        this.normal = normal;
    }

    public ArrayList<String> getThumb() {
        return thumb;
    }

    public void setThumb(ArrayList<String> thumb) {
        this.thumb = thumb;
    }
}
