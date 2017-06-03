package com.aiyaschool.aiya.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EGOISTK21 on 2017/5/9.
 */
public class Avatar implements Parcelable {
    /**
     * normal : {"face":"http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756","background":"http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756"}
     * thumb : {"face":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756","background":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756"}
     */

    private Normal normal;
    private Thumb thumb;

    public Avatar(Normal normal, Thumb thumb) {
        this.normal = normal;
        this.thumb = thumb;
    }

    protected Avatar(Parcel in) {
        normal = in.readParcelable(Normal.class.getClassLoader());
        thumb = in.readParcelable(Thumb.class.getClassLoader());
    }

    public static final Creator<Avatar> CREATOR = new Creator<Avatar>() {
        @Override
        public Avatar createFromParcel(Parcel in) {
            return new Avatar(in);
        }

        @Override
        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public boolean isNull() {
        return getNormal().getFace() == null && getNormal().getBackground() == null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(normal, flags);
        dest.writeParcelable(thumb, flags);
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "normal=" + normal +
                ", thumb=" + thumb +
                '}';
    }
}
