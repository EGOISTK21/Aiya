package com.aiyaschool.aiya.love.unmatched.randomMatch;

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

public class RandomMatchFragment extends LazyFragment implements RandomMatchContract.View {

    private View rootView;
    private RandomMatchContract.Presenter presenter;
    private SwitchCompat swRandom;
    private FragmentManager fm;

    public static RandomMatchFragment newInstance() {
        return new RandomMatchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RandomMatchPresenter(getContext(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_random_match, container, false);
        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        fm = getFragmentManager();
        swRandom = (SwitchCompat) rootView.findViewById(R.id.switch_can_random);
        presenter.initCanRandom();
        Spannable spannable = new SpannableString("发起后将立即与一名异性随缘成为七天情侣");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) rootView.findViewById(R.id.tv_love_match_at_random_warn)).setText(spannable);
    }

    private void initListener() {
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
        rootView.findViewById(R.id.btn_invite).setOnClickListener(this);
        swRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.commitCanRandom(isChecked);
            }
        });
    }

    @Override
    public void setCanRandom(boolean canRandom) {
        swRandom.setChecked(canRandom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                fm.popBackStack();
                break;
            case R.id.btn_invite:
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                MyApplication.getInstance().setMatched(true);
                ((MainActivity) getActivity()).notifyAdapter();
                break;
        }
    }

}
