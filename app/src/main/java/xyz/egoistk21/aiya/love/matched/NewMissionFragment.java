package xyz.egoistk21.aiya.love.matched;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.base.LazyFragment;

/**
 * Created by EGOISTK on 2017/4/4.
 */

public class NewMissionFragment extends LazyFragment {

    private View rootView;
    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_mission, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        fm = getFragmentManager();
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                fm.popBackStack();
                break;
        }
    }
}
