package com.aiyaschool.aiya.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.NoScrollViewPager;
import com.aiyaschool.aiya.community.CommunityFragment;
import com.aiyaschool.aiya.love.LoveContainerFragment;
import com.aiyaschool.aiya.love.matched.MatchedContainerFragment;
import com.aiyaschool.aiya.me.MeFragment;
import com.aiyaschool.aiya.message.MessageFragment;
import com.aiyaschool.aiya.util.BottomNavigationViewHelper;
import com.aiyaschool.aiya.util.StatusBarUtil;

/**
 * Created by EGOISTK21 on 2017/3/13.
 */

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.init(this);
        initViewPager();
        initBottomNav();
    }

    private void initViewPager() {
        vpMain = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        final Fragment[] fragments = new Fragment[]{new CommunityFragment(), new MessageFragment(),
                ((MyApplication) getApplicationContext()).isMatched() ?
                        new MatchedContainerFragment() :
                        new LoveContainerFragment(),
                new MeFragment()};
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //每一页fragment实例化之后都不会被销毁
                //super.destroyItem(container, position, object);
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }

    private void initBottomNav() {
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(
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
        navigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
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
}
