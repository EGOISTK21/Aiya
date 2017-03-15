package com.egoistk.aiya.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.egoistk.aiya.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView0, textView1, textView2, textView3;
    private NoScrollViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_community:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_message:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_love:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_me:
                        viewPager.setCurrentItem(3);
                        return true;
                }
                return false;
            }
        });
        initViewPager();
    }

    private void initViewPager() {
        LayoutInflater inflater = LayoutInflater.from(this);

        List<View> views = new ArrayList<>();
        views.add(inflater.inflate(R.layout.view_community, null));
        views.add(inflater.inflate(R.layout.view_message, null));
        views.add(inflater.inflate(R.layout.view_love, null));
        views.add(inflater.inflate(R.layout.view_me, null));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views, this);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);

        textView0 = (TextView) views.get(0).findViewById(R.id.message0);
        textView1 = (TextView) views.get(1).findViewById(R.id.message1);
        textView2 = (TextView) views.get(2).findViewById(R.id.message2);
        textView3 = (TextView) views.get(3).findViewById(R.id.message3);
        textView0.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message0:
                textView0.setText("社区状态已被更改");
                break;
            case R.id.message1:
                textView1.setText("消息状态已被更改");
                break;
            case R.id.message2:
                textView2.setText("恋爱记状态已被更改");
                break;
            case R.id.message3:
                textView3.setText("我状态已被更改");
                break;
        }
    }
}
