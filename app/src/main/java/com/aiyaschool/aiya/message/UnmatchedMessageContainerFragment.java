package com.aiyaschool.aiya.message;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class UnmatchedMessageContainerFragment extends BaseFragment {

    public static UnmatchedMessageContainerFragment newInstance() {
        return new UnmatchedMessageContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_message, MsgListFragment.newInstance()).commit();
    }

}
