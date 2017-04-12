package com.aiyaschool.aiya.message.ui.activity;

import android.support.v4.app.Fragment;

import com.aiyaschool.aiya.message.MsgFragment;

import java.util.UUID;

/**
 * Created by XZY on 2017/3/8.
 * 消息界面的根activity，用于容纳消息fragment
 */

public class MsgActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.example.xzy.criminalintent.crime_id";

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        //调用fragment的构造及储存方法。
        return MsgFragment.newInstance(crimeId);
    }
}
