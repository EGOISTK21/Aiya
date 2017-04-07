package xyz.egoistk21.aiya;

import android.app.Application;


/**
 * Created by EGOISTK21 on 2017/3/15.
 */

public class MyApplication extends Application {

    private boolean isMatched;

    public void setIsMatched(boolean isMached) {
        this.isMatched = isMached;
    }

    public boolean isMatched() {
        return isMatched;
    }

    @Override
    public void onCreate() {
        setIsMatched(false);
        super.onCreate();
    }
}
