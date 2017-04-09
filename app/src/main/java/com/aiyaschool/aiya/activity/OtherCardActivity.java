package com.aiyaschool.aiya.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.util.StatusBarUtil;

/**
 * Created by EGOISTK21 on 2017/4/8.
 */

public class OtherCardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_card);
        StatusBarUtil.init(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
