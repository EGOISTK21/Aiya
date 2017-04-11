package com.aiyaschool.aiya.love.matched.view;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.love.matched.presenter.i.ILoveMatchedPresenter;
import com.aiyaschool.aiya.love.matched.presenter.impl.LoveMatchedPresenter;

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

public class MatchedFragment extends LazyFragment implements ILoveMatchedView {

    private ILoveMatchedPresenter iLoveMatchedPresenter;
    private View rootView;
    private ViewPager vpLoveMatched;
    private TabLayout tabLayout;
    private FragmentTransaction ft;

    public static MatchedFragment newInstance() {
        MatchedFragment instance = new MatchedFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched, container, false);
        initTitleBar();
        initViewPager();
        initTabLayout();
        iLoveMatchedPresenter = new LoveMatchedPresenter(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        iLoveMatchedPresenter.detachView();
    }

    private void initTitleBar() {
        ft = getFragmentManager().beginTransaction();
        rootView.findViewById(R.id.ibn_new_mission).setOnClickListener(this);
    }

    private void initViewPager() {
        final Fragment[] fragments = new Fragment[]{new MatchedJournalFragment(), new MatchedTodayFragment(),
                new MatchedFutureFragment()};
        vpLoveMatched = (ViewPager) rootView.findViewById(R.id.viewpager_love_matched);
        vpLoveMatched.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs_love_matched);
        tabLayout.setupWithViewPager(vpLoveMatched);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.getTabAt(0).setText("纪念");
        tabLayout.getTabAt(1).setText("今天");
        tabLayout.getTabAt(2).setText("未来");
        tabLayout.getTabAt(1).select();
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            root.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPureWhite));
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(ContextCompat.getColor(getContext(), R.color.colorDivider));
            drawable.setSize(1, 1);
            ((LinearLayout) root).setDividerPadding(16);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
        tabLayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.colorContentSecondary), ContextCompat.getColor(getContext(), R.color.colorContentMain));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibn_new_mission:
                startActivity(new Intent(getContext(), NewMissionActivity.class));
                break;
        }
    }
}
