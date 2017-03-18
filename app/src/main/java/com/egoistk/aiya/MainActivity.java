package com.egoistk.aiya;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.egoistk.aiya.bottomNav.BottomNavigationViewHelper;
import com.egoistk.aiya.community.CommunityFragment;
import com.egoistk.aiya.love.LoveFragment;
import com.egoistk.aiya.me.MeFragment;
import com.egoistk.aiya.message.MessageFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager mainViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        initBottomNav();
    }

    private void initViewPager() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new CommunityFragment());
        fragments.add(new MessageFragment());
        fragments.add(new LoveFragment());
        fragments.add(new MeFragment());
        mainViewPager = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        mainViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
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
                                mainViewPager.setCurrentItem(0);
                                return true;
                            case R.id.navigation_message:
                                mainViewPager.setCurrentItem(1);
                                return true;
                            case R.id.navigation_love:
                                mainViewPager.setCurrentItem(2);
                                return true;
                            case R.id.navigation_me:
                                mainViewPager.setCurrentItem(3);
                                return true;
                        }
                        return false;
                    }
                });
    }
}
