package com.aiyaschool.aiya.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.aiyaschool.aiya.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 基类Activity
 * Created by EGOISTK21 on 2017/5/5.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    protected static final int RC_EXTERNAL_STORAGE = 0;
    protected static final String[] _EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    @Override
    public void onPermissionsGranted(int i, List<String> list) {

    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {

    }
}
