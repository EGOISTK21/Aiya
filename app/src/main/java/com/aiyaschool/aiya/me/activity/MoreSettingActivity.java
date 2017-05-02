package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.sign.SignActivity;
import com.aiyaschool.aiya.util.DBUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreSettingActivity extends RxAppCompatActivity {

    @BindView(R.id.tv)
    AppCompatTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
        ButterKnife.bind(this);
        StatusBarUtil.init(this);
    }

    @OnClick(value = R.id.btn_login_out)
    void loginOut() {
        DBUtil.clearLoginToken();
        startActivity(new Intent(this, SignActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @OnClick(value = R.id.btn_clear_all)
    void clearAll() {
        DBUtil.clearAll();
    }

    @OnClick(value = R.id.btn_print)
    void print() {
        tv.setText(MyApplication.getUser().toString());
    }
}
