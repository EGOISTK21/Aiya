package com.aiyaschool.aiya.love.matched.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.LazyFragment;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedFutureFragment extends LazyFragment implements ILoveMatchedFutureView {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_future, container, false);
        return rootView;
    }
}
