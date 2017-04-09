package com.aiyaschool.aiya.love.matched;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.IntimacyRulesActivity;
import com.aiyaschool.aiya.base.LazyFragment;

/**
 * Created by EGOISTK21 on 2017/4/6.
 */

public class IntimacyDetailFragment extends LazyFragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_intimacy_detail, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
        rootView.findViewById(R.id.tv_intimacy_rules).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_intimacy_rules:
                startActivity(new Intent(getContext(), IntimacyRulesActivity.class));
                break;
        }
    }
}
