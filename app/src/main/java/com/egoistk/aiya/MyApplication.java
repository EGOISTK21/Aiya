package com.egoistk.aiya;

import android.app.Application;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by EGOISTK on 2017/3/15.
 */

public class MyApplication extends Application {

    private boolean isMatched = false;
    public final URL _ROOT_ = new URL("http://lovefor7days.applinzi.com");

    public MyApplication() throws MalformedURLException {}


    public boolean getIsMatched() {
        return isMatched;
    }

    public void setIsMatched() {

    }

    @Override
    public void onCreate() {
        setIsMatched();
        super.onCreate();
    }
}
