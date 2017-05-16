package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.User;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/5/13.
 */

public class HitItOffActivity extends BaseActivity {

    private User mUser;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_school)
    TextView tvSchool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hit_it_off;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mUser = bundle.getParcelable("hit it off");
        if (mUser != null) {
            tvUsername.setText(mUser.getUsername());
            tvSchool.setText(mUser.getSchool());
        }
    }

    @OnClick(R.id.go_matched)
    void goMatched() {
        finish();
    }

}
