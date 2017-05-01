package com.aiyaschool.aiya.me.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aiyaschool.aiya.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGiftActivity extends AppCompatActivity {

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_total)
    TextView mTvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.tv_total})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_total:
                break;
        }
    }
}
