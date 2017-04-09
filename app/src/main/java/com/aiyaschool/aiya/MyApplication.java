package com.aiyaschool.aiya;

import android.app.Application;

/**
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private boolean matched;

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isMatched() {
        return matched;
    }

    @Override
    public void onCreate() {
        setMatched(false);
        super.onCreate();
    }
}
