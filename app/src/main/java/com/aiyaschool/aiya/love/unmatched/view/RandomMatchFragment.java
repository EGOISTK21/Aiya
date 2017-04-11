package com.aiyaschool.aiya.love.unmatched.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.base.LazyFragment;

/**
 * Created by EGOISTK21 on 2017/3/29.
 */

public class RandomMatchFragment extends LazyFragment {

    private boolean canRandom;
    private View rootView;
    private SwitchCompat swRandom;
    private FragmentManager fm;

    public static RandomMatchFragment newInstance() {
        RandomMatchFragment instance = new RandomMatchFragment();
        instance.setCanRandom(false);
        return instance;
    }

    private void setCanRandom(boolean canRandom) {
        this.canRandom = canRandom;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_random_match, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        fm = getFragmentManager();
        swRandom = (SwitchCompat) rootView.findViewById(R.id.switch_can_random);
        swRandom.setChecked(canRandom);
        swRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                canRandom = isChecked;
            }
        });

        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
        rootView.findViewById(R.id.btn_invite).setOnClickListener(this);

        Spannable spannable = new SpannableString("发起后将立即与一名异性随缘成为七天情侣");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) rootView.findViewById(R.id.tv_love_match_at_random_warn)).setText(spannable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                fm.popBackStack();
                break;
            case R.id.btn_invite:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((MyApplication) getActivity().getApplication()).setMatched(true);
                ((MainActivity) getActivity()).notifyAdapter();
                break;
        }
    }

}
