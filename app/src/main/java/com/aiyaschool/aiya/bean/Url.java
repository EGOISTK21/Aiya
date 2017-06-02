package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by EGOISTK21 on 2017/6/2.
 */

public class Url implements Parcelable {

    private List<String> normal;
    private List<String> thumb;

    public Url(List<String> normal, List<String> thumb) {
        this.normal = normal;
        this.thumb = thumb;
    }

    protected Url(Parcel in) {
        normal = in.createStringArrayList();
        thumb = in.createStringArrayList();
    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };

    public List<String> getNormal() {
        return normal;
    }

    public void setNormal(List<String> normal) {
        this.normal = normal;
    }

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(normal);
        dest.writeStringList(thumb);
    }

    @Override
    public String toString() {
        return "Url{" +
                "normal=" + normal +
                ", thumb=" + thumb +
                '}';
    }
}
