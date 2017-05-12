package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EGOISTK21 on 2017/5/11.
 */
public class Normal implements Parcelable {
    /**
     * face : http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756
     * background : http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756
     */

    private String face;
    private String background;

    public Normal(String face, String background) {
        this.face = face;
        this.background = background;
    }

    protected Normal(Parcel in) {
        face = in.readString();
        background = in.readString();
    }

    public static final Creator<Normal> CREATOR = new Creator<Normal>() {
        @Override
        public Normal createFromParcel(Parcel in) {
            return new Normal(in);
        }

        @Override
        public Normal[] newArray(int size) {
            return new Normal[size];
        }
    };

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(face);
        dest.writeString(background);
    }

    @Override
    public String toString() {
        return "Normal{" +
                "face='" + face + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
