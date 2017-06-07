package com.aiyaschool.aiya.bean;

import java.io.Serializable;

/**
 * Created by wewarriors on 2017/5/31.
 */

public class Img implements Serializable {
    private String normal;
    private String thumb;

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
