package com.aiyaschool.aiya.activity;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;

import butterknife.OnClick;


public class CopyrightActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copyright;
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        finish();
    }
}
