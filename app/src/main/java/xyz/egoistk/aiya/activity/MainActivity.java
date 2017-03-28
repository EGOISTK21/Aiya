package xyz.egoistk.aiya.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import xyz.egoistk.aiya.MyApplication;
import xyz.egoistk.aiya.R;
import xyz.egoistk.aiya.base.NoScrollViewPager;
import xyz.egoistk.aiya.util.BottomNavigationViewHelper;
import xyz.egoistk.aiya.community.CommunityFragment;
import xyz.egoistk.aiya.love.matched.view.impl.LoveMatchedFragment;
import xyz.egoistk.aiya.love.unmatched.UnmatchedFragment;
import xyz.egoistk.aiya.me.MeFragment;
import xyz.egoistk.aiya.message.MessageFragment;

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();
        initBottomNav();
    }

    private void initViewPager() {
        final android.support.v4.app.Fragment[] fragments = new android.support.v4.app.Fragment[]{
                new CommunityFragment(),
                new MessageFragment(),
                ((MyApplication) getApplicationContext()).isMatched() ?
                        new LoveMatchedFragment() :
                        new UnmatchedFragment(),
                new MeFragment()};
        vpMain = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        vpMain.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
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
    }
}
