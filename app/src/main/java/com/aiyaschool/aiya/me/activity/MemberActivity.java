package com.aiyaschool.aiya.me.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.open12)
    ImageView mIvOpen12;
    @BindView(R.id.open30)
    ImageView mIvOpen30;
    @BindView(R.id.open60)
    ImageView mIvOpen60;
    @BindView(R.id.open108)
    ImageView mIvOpen108;
    private TextView mTvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.open12, R.id.open30, R.id.open60, R.id.open108})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.open12:
                break;
            case R.id.open30:
                break;
            case R.id.open60:
                break;
            case R.id.open108:
                break;
        }
    }
}
