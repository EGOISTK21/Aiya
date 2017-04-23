package com.aiyaschool.aiya.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.util.DBUtil;
import com.aiyaschool.aiya.util.OkHttpUtil;


/**
 * Created by EGOISTK21 on 2017/3/16.
 */

public class MeFragment extends LazyFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.view_me, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View mView) {


    }


}
