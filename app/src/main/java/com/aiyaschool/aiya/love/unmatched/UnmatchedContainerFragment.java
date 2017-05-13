package com.aiyaschool.aiya.love.unmatched;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.love.unmatched.conditionMatch.ConditionMatchFragment;

/**
 * Created by EGOISTK21 on 2017/3/29.
 */

public class UnmatchedContainerFragment extends BaseFragment {

    public static UnmatchedContainerFragment newInstance() {
        return new UnmatchedContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_container;
    }

    @Override
    protected void initView() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_love, ConditionMatchFragment.newInstance()).commit();
    }
}
