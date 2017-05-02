package com.aiyaschool.aiya.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.FormActivity;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.activity.sign.SignActivity;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by EGOISTK21 on 2017/5/1.
 */

public class SplashActivity extends RxAppCompatActivity implements SplashContract.View {

    private static final String TAG = "SplashActivity";
    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        mPresenter = new SplashPresenter(this);
        mPresenter.init(SignUtil.getPhone(), SignUtil.getLoginToken());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    @Override
    public void startSignView() {
        Log.i(TAG, "startSignView");
        startActivity(new Intent(this, SignActivity.class));
        finish();
    }

    @Override
    public void startFormView() {
        Log.i(TAG, "startFormView");
        startActivity(new Intent(this, FormActivity.class));
        finish();
    }

    @Override
    public void startMainView() {
        Log.i(TAG, "startMainView");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
