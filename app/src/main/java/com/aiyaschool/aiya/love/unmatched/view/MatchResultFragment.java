package com.aiyaschool.aiya.love.unmatched.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.OtherCardActivity;
import com.aiyaschool.aiya.base.LazyFragment;

/**
 * Created by EGOISTK21 on 2017/3/31.
 */

public class MatchResultFragment extends LazyFragment {

    private View rootView;

    public static MatchResultFragment newInstance() {
        MatchResultFragment instance = new MatchResultFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match_result, container, false);
        initView();
        initListener();
        return rootView;
    }

    private void initView() {

    }

    private void initListener() {
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
        rootView.findViewById(R.id.ll_result_card).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.ll_result_card:
                startActivity(new Intent(getContext(), OtherCardActivity.class));
                break;
        }
    }

}
