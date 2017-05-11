package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/5/9.
 */
public class Avatar {
    /**
     * normal : {"face":"http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756","background":"http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756"}
     * thumb : {"face":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756","background":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756"}
     */

    private Normal normal;
    private Thumb thumb;

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

    private class Normal {
        /**
         * face : http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756
         * background : http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756
         */

        private String face;
        private String background;

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

    private class Thumb {
        /**
         * face : http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756
         * background : http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756
         */

        private String face;
        private String background;

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
}
