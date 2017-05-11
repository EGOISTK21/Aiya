package com.aiyaschool.aiya.bean;

import java.util.List;

/**
 * Created by EGOISTK21 on 2017/5/9.
 */
public class ImgWall {
    /**
     * rows : 0
     * url : null
     */

    private int rows;
    private List<String> url;

    public ImgWall(int rows, List<String> url) {
        this.rows = rows;
        this.url = url;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImgWall{" +
                "rows=" + rows +
                ", url=" + url +
                '}';
    }
}
