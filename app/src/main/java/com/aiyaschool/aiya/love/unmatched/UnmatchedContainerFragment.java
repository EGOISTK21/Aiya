package com.aiyaschool.aiya.love.unmatched;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.love.unmatched.view.UnmatchedFragment;

/**
 * Created by EGOISTK21 on 2017/3/29.
 */

public class UnmatchedContainerFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container_love_unmatched, UnmatchedFragment.newInstance()).commit();
        return inflater.inflate(R.layout.fragment_love_unmatched_container, container, false);
    }
}
