package com.egoistk.aiya;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<View> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LayoutInflater inflater = LayoutInflater.from(this);

        mViews = new ArrayList<View>();
        mViews.add(inflater.inflate(R.layout.view_community, null));
        mViews.add(inflater.inflate(R.layout.view_message, null));
        mViews.add(inflater.inflate(R.layout.view_love, null));
        mViews.add(inflater.inflate(R.layout.view_me, null));

        mViewPagerAdapter = new ViewPagerAdapter(mViews, this);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_community:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_message:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_love:
                    mViewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_me:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

}
