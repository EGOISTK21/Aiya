package com.aiyaschool.aiya.message;

import android.support.v7.widget.RecyclerView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class HitListFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static HitListFragment newInstance() {
        return new HitListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_hit;
    }

    @Override
    protected void initView() {

    }
}
