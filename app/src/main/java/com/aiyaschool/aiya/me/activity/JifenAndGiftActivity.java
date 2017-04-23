package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class JifenAndGiftActivity extends AppCompatActivity {

    @InjectView(R.id.tv_cancel)
    TextView mTvCancel;
    @InjectView(R.id.tv_gift_method)
    TextView mTvGiftMethod;
    @InjectView(R.id.tv_get_int)
    TextView mTvGetInt;
    @InjectView(R.id.iv_buy_int1)
    ImageView mIvBuyInt1;
    @InjectView(R.id.iv_buy_int2)
    ImageView mIvBuyInt2;
    @InjectView(R.id.iv_buy_int3)
    ImageView mIvBuyInt3;
    @InjectView(R.id.iv_buy_int4)
    ImageView mIvBuyInt4;
    @InjectView(R.id.iv_buy_g1)
    ImageView mIvBuyG1;
    @InjectView(R.id.iv_buy_g2)
    ImageView mIvBuyG2;
    @InjectView(R.id.iv_buy_g3)
    ImageView mIvBuyG3;
    @InjectView(R.id.iv_buy_g4)
    ImageView mIvBuyG4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifen_and_gift);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_gift_method, R.id.tv_get_int, R.id.iv_buy_int1, R.id.iv_buy_int2, R.id.iv_buy_int3, R.id.iv_buy_int4, R.id.iv_buy_g1, R.id.iv_buy_g2, R.id.iv_buy_g3, R.id.iv_buy_g4})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_cancel:
                intent = new Intent(JifenAndGiftActivity.this,MainActivity.class);
                intent.putExtra("Flag","Me");
                startActivity(intent);
                break;
            case R.id.tv_gift_method:
                intent = new Intent(JifenAndGiftActivity.this,GiftMethodActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_get_int:
                break;
            case R.id.iv_buy_int1:
                break;
            case R.id.iv_buy_int2:
                break;
            case R.id.iv_buy_int3:
                break;
            case R.id.iv_buy_int4:
                break;
            case R.id.iv_buy_g1:
                break;
            case R.id.iv_buy_g2:
                break;
            case R.id.iv_buy_g3:
                break;
            case R.id.iv_buy_g4:
                break;
        }
    }
}
