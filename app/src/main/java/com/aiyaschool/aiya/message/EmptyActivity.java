package com.aiyaschool.aiya.message;

import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EmptyActivity extends BaseActivity {

    private String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        finish();
    }
}
