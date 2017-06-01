package com.aiyaschool.aiya.love.matched;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.love.matched.main.MatchedFragment;

/**
 * Created by EGOISTK21 on 2017/4/9.
 */

public class MatchedContainerFragment extends BaseFragment {

    private static final String TAG = "MatchedContainer";

    public static MatchedContainerFragment newInstance() {
        return new MatchedContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_container;
    }

    @Override
    protected void initView() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_love, MatchedFragment.newInstance()).commit();
    }
}
