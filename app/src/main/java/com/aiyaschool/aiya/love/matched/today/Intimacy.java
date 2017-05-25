package com.aiyaschool.aiya.love.matched.today;

/**
 * Created by EGOISTK21 on 2017/5/25.
 */

public class Intimacy {

    private String state;
    private String value;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Intimacy{" +
                "state='" + state + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
