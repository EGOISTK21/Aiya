package com.aiyaschool.aiya.bean;

import java.util.List;

/**
 * Created by EGOISTK21 on 2017/6/27.
 */
public class ImagePath {
    private String createTime;
    private List<Gallery> GalleryList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Gallery> getGalleryList() {
        return GalleryList;
    }

    public void setGalleryList(List<Gallery> galleryList) {
        GalleryList = galleryList;
    }
}
