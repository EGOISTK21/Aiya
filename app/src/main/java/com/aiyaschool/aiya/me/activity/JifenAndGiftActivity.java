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

public class JifenAndGiftActivity extends AppCompatActivity {

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.buy10)
    ImageView mIvBuy10;
    @BindView(R.id.buy20)
    ImageView mIvBuy20;
    @BindView(R.id.buy50)
    ImageView mIvBuy50;
    @BindView(R.id.buy100)
    ImageView mIvBuy100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifen_and_gift);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_cancel, R.id.buy10, R.id.buy20, R.id.buy50, R.id.buy100})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.buy10:
                break;
            case R.id.buy20:
                break;
            case R.id.buy50:
                break;
            case R.id.buy100:
                break;
        }
    }
}
