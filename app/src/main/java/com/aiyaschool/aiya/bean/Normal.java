package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/5/11.
 */
public class Normal {
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
}
