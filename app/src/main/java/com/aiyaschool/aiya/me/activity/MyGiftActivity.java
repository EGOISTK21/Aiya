package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyGiftActivity extends AppCompatActivity {

    @InjectView(R.id.tv_back)
    TextView mTvBack;
    @InjectView(R.id.tv_total)
    TextView mTvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_back, R.id.tv_total})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                Intent intent = new Intent(MyGiftActivity.this,MainActivity.class);
                intent.putExtra("Flag","Me");
                startActivity(intent);
                break;
            case R.id.tv_total:
                break;
        }
    }
}
