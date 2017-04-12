package com.aiyaschool.aiya.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.NoScrollViewPager;
import com.aiyaschool.aiya.community.CommunityFragment;
import com.aiyaschool.aiya.love.matched.MatchedContainerFragment;
import com.aiyaschool.aiya.love.unmatched.UnmatchedContainerFragment;
import com.aiyaschool.aiya.me.MeFragment;
import com.aiyaschool.aiya.message.MsgListFragment;
import com.aiyaschool.aiya.util.BottomNavigationViewUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;

/**
 * Created by EGOISTK21 on 2017/3/13.
 */

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager vpMain;
    private Fragment[] fragments;
    private FragmentManager fm;
    private FragmentPagerAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.init(this);
        initView();
        initListener();
    }

    private void initView() {
        fragments = new Fragment[]{
                new CommunityFragment(),
                new MsgListFragment(),
                ((MyApplication) getApplication()).isMatched()
                        ? new MatchedContainerFragment()
                        : new UnmatchedContainerFragment(),
                new MeFragment()
        };
        vpMain = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        fm = getSupportFragmentManager();
        adapter = new FragmentPagerAdapter(fm) {
            @Override
            public int getItemPosition(Object object) {
                if ((object instanceof UnmatchedContainerFragment
                        && ((MyApplication) getApplication()).isMatched())
                        || (object instanceof MatchedContainerFragment
                        && !((MyApplication) getApplication()).isMatched())) {
                    return POSITION_NONE;
                }
                return POSITION_UNCHANGED;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                String fragmentTag = fragment.getTag();
                if (fragment instanceof UnmatchedContainerFragment
                        && ((MyApplication) getApplication()).isMatched()) {
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.remove(fragment);
                    fragment = new MatchedContainerFragment();
                    ft.add(container.getId(), fragment, fragmentTag)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.attach(fragment);
                    ft.commit();
                } else if (fragment instanceof MatchedContainerFragment
                        && !((MyApplication) getApplication()).isMatched()) {
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.remove(fragment);
                    fragment = new UnmatchedContainerFragment();
                    ft.add(container.getId(), fragment, fragmentTag)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.attach(fragment);
                    ft.commit();
                }
                return fragment;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
        vpMain.setOffscreenPageLimit(3);
        vpMain.setAdapter(adapter);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewUtil.disableShiftMode(bottomNavigationView);
    }

    private void initListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_community:
                                vpMain.setCurrentItem(0);
                                return true;
                            case R.id.navigation_message:
                                vpMain.setCurrentItem(1);
                                return true;
                            case R.id.navigation_love:
                                vpMain.setCurrentItem(2);
                                return true;
                            case R.id.navigation_me:
                                vpMain.setCurrentItem(3);
                                return true;
                        }
                        return false;
                    }
                });
        // TODO: 2017/4/4 二次点击刷新
        bottomNavigationView.setOnNavigationItemReselectedListener(
                new BottomNavigationView.OnNavigationItemReselectedListener() {
                    @Override
                    public void onNavigationItemReselected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_community:
                                break;
                            case R.id.navigation_message:
                                break;
                            case R.id.navigation_love:
                                break;
                            case R.id.navigation_me:
                                break;
                        }
                    }
                });
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

}
