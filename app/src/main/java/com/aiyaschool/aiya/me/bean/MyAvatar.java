package com.aiyaschool.aiya.me.bean;

/**
 * Created by wewarriors on 2017/5/16.
 */

public class MyAvatar {
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

    @Override
    public String toString() {
        return "MyAvatar{" +
                "normal='" + normal + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
