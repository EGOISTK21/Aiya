package xyz.egoistk.aiya.love.matched.view.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.egoistk.aiya.R;
import xyz.egoistk.aiya.base.LazyFragment;
import xyz.egoistk.aiya.love.matched.view.i.ILoveMatchedFutureView;

/**
 * Created by EGOISTK on 2017/3/21.
 */

public class LoveMatchedFutureFragment extends LazyFragment implements ILoveMatchedFutureView {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_future, container, false);
        return rootView;
    }
}
