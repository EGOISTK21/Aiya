package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by EGOISTK21 on 2017/5/9.
 */
public class ImgWall implements Parcelable {
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

    protected ImgWall(Parcel in) {
        rows = in.readInt();
        url = in.createStringArrayList();
    }

    public static final Creator<ImgWall> CREATOR = new Creator<ImgWall>() {
        @Override
        public ImgWall createFromParcel(Parcel in) {
            return new ImgWall(in);
        }

        @Override
        public ImgWall[] newArray(int size) {
            return new ImgWall[size];
        }
    };

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rows);
        dest.writeStringList(url);
    }

    @Override
    public String toString() {
        return "ImgWall{" +
                "rows=" + rows +
                ", url=" + url +
                '}';
    }
}
