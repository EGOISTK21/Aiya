package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EGOISTK21 on 2017/5/11.
 */
public class Thumb implements Parcelable {
    /**
     * face : http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756
     * background : http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756
     */

    private String face;
    private String background;

    public Thumb(String face, String background) {
        this.face = face;
        this.background = background;
    }

    protected Thumb(Parcel in) {
        face = in.readString();
        background = in.readString();
    }

    public static final Creator<Thumb> CREATOR = new Creator<Thumb>() {
        @Override
        public Thumb createFromParcel(Parcel in) {
            return new Thumb(in);
        }

        @Override
        public Thumb[] newArray(int size) {
            return new Thumb[size];
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
    public boolean equals(Object obj) {
        if (!(obj instanceof Thumb)) return false;
        Thumb temp = (Thumb) obj;
        return ((face != null && face.equals(temp.face)) || (face == null && temp.face == null))
                && ((background != null && background.equals(temp.background)) || (background == null && temp.background == null));
    }

    @Override
    public String toString() {
        return "Thumb{" +
                "face='" + face + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
