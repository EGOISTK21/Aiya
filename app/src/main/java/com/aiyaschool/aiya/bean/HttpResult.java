package com.aiyaschool.aiya.bean;

/**
 * http统一返回体，data根据需求转成相应实体类
 * Created by EGOISTK21 on 2017/5/2.
 */

public class HttpResult<T> {

    private String state;
    private String err_msg;
    private String rows;

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    private T data;

    public String getState() {
        return this.state;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "state='" + state + '\'' +
                ", err_msg='" + err_msg + '\'' +
                ", data=" + data +
                '}';
    }
}
