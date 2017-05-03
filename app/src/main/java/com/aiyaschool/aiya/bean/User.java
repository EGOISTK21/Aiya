package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/4/26.
 */

public class User {

    /**
     * temptoken : f29c1317158d76821e8d11ccd8a007a358fc9984c178e
     * username : xihuan
     * phone : 15000000000
     * AccessToken : 48f418d380c1cf2ae948732780f6a39815000000000590494641c6de
     * school : 南京大学
     * loveid : 1
     * group : 1
     * province : 陕西
     * avatar : {"normal":{"face":"http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756","background":"http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756"},"thumb":{"face":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756","background":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756"}}
     * profile : 亨利就坐在那里，满眼望去，都是自己二十岁的影子
     * points : 200
     * gifttickets : 0
     * imgwall : {"rows":0,"url":null}
     * usersig : eJxNjV1PgzAYRv9Lb2dcCy0LJl4AY4A6EzLddtdUeCF1ArUUlBj-uw3B6O05z8cXeno4XIui6IbWcDMpQDcIo6sZyxJaIysJ2sJXofW0CKGULLkw3NXlv3xfXvisLCMUY0wYxu4i4VNJDVxUZp4jjDHHRhY7gu5l11rhYOIRTAj*k0Y2ME-6tsF8x--9k7XF*-g5yvJom95Dr8ZT9eHs3U36GJ5fgnW5CldTUt-R0BspOQRBErP3PKthRzu-b1Rz2ubFdFmnO*qF3ZCwWMTDJi*OEZEZHt6gqm-R9w-MC1Zg
     * logintoken : 03b0761c6c915924907bb9ebabdebad3160b4f30-1f89-11e7-967b-ebe175c2a0263b946bfe-d0fb-4357-aa30-42cdaef6dc50
     */

    private String temptoken;
    private String username;
    private String phone;
    private String AccessToken;
    private String school;
    private String loveid;
    private String group;
    private String province;
    private AvatarBean avatar;
    private String profile;
    private String points;
    private String gifttickets;
    private ImgwallBean imgwall;
    private String usersig;
    private String logintoken;


    public String getTemptoken() {
        return temptoken;
    }

    public void setTemptoken(String temptoken) {
        this.temptoken = temptoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccessToken() {
        return AccessToken == null ? "false" : AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLoveid() {
        return loveid;
    }

    public boolean isMatched() {
        return !loveid.equals("0");
    }

    public void setLoveid(String loveid) {
        this.loveid = loveid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getGifttickets() {
        return gifttickets;
    }

    public void setGifttickets(String gifttickets) {
        this.gifttickets = gifttickets;
    }

    public ImgwallBean getImgwall() {
        return imgwall;
    }

    public void setImgwall(ImgwallBean imgwall) {
        this.imgwall = imgwall;
    }

    public String getUsersig() {
        return usersig;
    }

    public void setUsersig(String usersig) {
        this.usersig = usersig;
    }

    public String getLogintoken() {
        return logintoken;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }

    public static class AvatarBean {
        /**
         * normal : {"face":"http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756","background":"http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756"}
         * thumb : {"face":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756","background":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756"}
         */

        private NormalBean normal;
        private ThumbBean thumb;

        public NormalBean getNormal() {
            return normal;
        }

        public void setNormal(NormalBean normal) {
            this.normal = normal;
        }

        public ThumbBean getThumb() {
            return thumb;
        }

        public void setThumb(ThumbBean thumb) {
            this.thumb = thumb;
        }

        public static class NormalBean {
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

        public static class ThumbBean {
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

    public static class ImgwallBean {
        /**
         * rows : 0
         * url : null
         */

        private int rows;
        private Object url;

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "temptoken='" + temptoken + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", AccessToken='" + AccessToken + '\'' +
                ", school='" + school + '\'' +
                ", loveid='" + loveid + '\'' +
                ", group='" + group + '\'' +
                ", province='" + province + '\'' +
                ", avatar=" + avatar +
                ", profile='" + profile + '\'' +
                ", points='" + points + '\'' +
                ", gifttickets='" + gifttickets + '\'' +
                ", imgwall=" + imgwall +
                ", usersig='" + usersig + '\'' +
                ", logintoken='" + logintoken + '\'' +
                '}';
    }
}
