package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;

public class GiftMethodActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_method);
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id){
            case R.id.tv_back:
                finish();
                break;
            default:
                break;
        }
    }
}
