package com.aiyaschool.aiya.love.matched.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.love.matched.today.MatchedTodayFragment;

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

public class MatchedFragment extends BaseFragment implements MatchedContract.View {

    private static final int PAGE_COUNT = 1;
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
        ViewPager vpMatched = (ViewPager) rootView.findViewById(R.id.viewpager_love_matched);
        vpMatched.setOffscreenPageLimit(2);
        vpMatched.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            //            private String[] tabTitles = new String[]{"纪念", "今天", "未来"};
            private Fragment[] fragments = new Fragment[]{
//                    new MatchedJournalFragment(),
                    MatchedTodayFragment.newInstance(),
//                    new MatchedFutureFragment()
            };

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
                return null;
//                return tabTitles[position];
            }
        });
//        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs_love_matched);
//        tabLayout.setupWithViewPager(vpMatched);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        TabLayoutUtil.initIndicator(getContext(), tabLayout);
//        tabLayout.getTabAt(1).select();
    }

    @Override
    public void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

//    @OnClick(value = R.id.ib_new_mission)
//    void newMission() {
//        startActivity(new Intent(getContext(), NewMissionActivity.class));
//    }

}
