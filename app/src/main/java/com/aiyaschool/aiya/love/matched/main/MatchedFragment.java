package com.aiyaschool.aiya.love.matched.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.love.matched.future.MatchedFutureFragment;
import com.aiyaschool.aiya.love.matched.journal.MatchedJournalFragment;
import com.aiyaschool.aiya.love.matched.today.MatchedTodayFragment;
import com.aiyaschool.aiya.util.TabLayoutUtil;

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

public class MatchedFragment extends BaseFragment implements MatchedContract.View {

    private static final int PAGE_COUNT = 3;
    private MatchedContract.Presenter presenter;

    public static MatchedFragment newInstance() {
        return new MatchedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_matched;
    }

    @Override
    protected void initView() {
        presenter = new MatchedPresenter(getContext(), this);
        final Fragment[] fragments = new Fragment[]{
                new MatchedJournalFragment(),
                new MatchedTodayFragment(),
                new MatchedFutureFragment()
        };
        ViewPager vpMatched = (ViewPager) rootView.findViewById(R.id.viewpager_love_matched);
        vpMatched.setOffscreenPageLimit(2);
        vpMatched.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            private String[] tabTitles = new String[]{"纪念", "今天", "未来"};

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        });
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs_love_matched);
        tabLayout.setupWithViewPager(vpMatched);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        TabLayoutUtil.initIndicator(getContext(), tabLayout);
        tabLayout.getTabAt(1).select();
        initListener();
    }

    private void initListener() {
        rootView.findViewById(R.id.ib_new_mission).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_new_mission:
                startActivity(new Intent(getContext(), NewMissionActivity.class));
                break;
        }
    }
}
