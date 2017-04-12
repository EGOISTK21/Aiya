package com.aiyaschool.aiya.message.ui.activity;

import android.support.v4.app.Fragment;

import com.aiyaschool.aiya.message.MsgListFragment;

/**
 * Created by XZY on 2017/2/26.
 */

public class MsgListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MsgListFragment();
    }
}
