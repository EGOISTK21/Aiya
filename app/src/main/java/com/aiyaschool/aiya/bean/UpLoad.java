package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/5/7.
 */
public class UpLoad {
    /**
     * "upurl": "http://up.sinacloud.net/gxwy-dynamic/photo/15000000009/2017-05-03/7476462ccbf328b3aed1791d88808e5959099404149f9.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=e%2FrgNcZzKn&Expires=1493803540", //头像上传地址
     * "imgname": "15000000009/2017-05-03/7476462ccbf328b3aed1791d88808e5959099404149f9.jpg" //URI资源表示符号
     */
    private String upurl;
    private String imgname;

    public UpLoad(String upurl, String imgname) {
        this.upurl = upurl;
        this.imgname = imgname;
    }

    public String getUpurl() {
        return upurl;
    }

    public void setUpurl(String upurl) {
        this.upurl = upurl;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    @Override
    public String toString() {
        return "UpLoad{" +
                "upurl='" + upurl + '\'' +
                ", imgname='" + imgname + '\'' +
                '}';
    }
}
