package com.aiyaschool.aiya.me.activity;

import android.content.Intent;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.CopyrightActivity;
import com.aiyaschool.aiya.activity.sign.SignActivity;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.util.SignUtil;

import butterknife.OnClick;

public class MoreSettingActivity extends BaseActivity {

    private static final String TAG = "MoreSettingActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_setting;
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        finish();
    }

    @OnClick(value = R.id.btn_login_out)
    void loginOut() {
        SignUtil.clearLoginToken();
        SignUtil.removeAccessToken();
        startActivity(new Intent(this, SignActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @OnClick(value = R.id.tv_copyright)
    void copyright() {
        startActivity(new Intent(MoreSettingActivity.this, CopyrightActivity.class));
    }

}
