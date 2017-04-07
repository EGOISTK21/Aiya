package xyz.egoistk21.aiya.love.matched;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.base.LazyFragment;

/**
 * Created by EGOISTK21 on 2017/4/7.
 */

public class ClockInFragment extends LazyFragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_clock_in, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                getActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);
                getFragmentManager().popBackStack();
                break;
        }
    }
}
