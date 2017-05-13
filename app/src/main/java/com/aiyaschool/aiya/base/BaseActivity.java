package com.aiyaschool.aiya.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.aiyaschool.aiya.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * 基类Activity
 * Created by EGOISTK21 on 2017/5/5.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("BaseActivity", getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        StatusBarUtil.init(this);
        ButterKnife.bind(this);
        initView();
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}
