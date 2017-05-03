package com.aiyaschool.aiya.bean;

/**
 * Created by EGOISTK21 on 2017/5/2.
 */

public class HttpResult<T> {

    private String state;
    private T data;

    public String getState() {
        return this.state;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "state='" + state + '\'' +
                ", data=" + data +
                '}';
    }
}
