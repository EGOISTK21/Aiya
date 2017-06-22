package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EGOISTK21 on 2017/5/9.
 */
public class ImgWall implements Parcelable {
    /**
     * rows : 0
     * url : null
     */

    private int rows;
    private Url url;

    public ImgWall(int rows, Url url) {
        this.rows = rows;
        this.url = url;
    }

    protected ImgWall(Parcel in) {
        rows = in.readInt();
        url = in.readParcelable(Url.class.getClassLoader());
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

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rows);
        dest.writeParcelable(url, flags);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ImgWall)) return false;
        ImgWall temp = (ImgWall) obj;
        return rows == temp.rows
                && ((url != null && url.equals(temp.url)) || (url == null && temp.url == null));
    }

    @Override
    public String toString() {
        return "ImgWall{" +
                "rows=" + rows +
                ", url=" + url +
                '}';
    }
}
