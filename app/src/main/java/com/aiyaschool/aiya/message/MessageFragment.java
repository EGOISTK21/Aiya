package com.aiyaschool.aiya.message;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class MessageFragment extends BaseFragment {

    public static MessageFragment newInstance() {
        return new MessageFragment();
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
