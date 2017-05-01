package com.aiyaschool.aiya.activity;

import android.content.Intent;
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
import com.aiyaschool.aiya.love.matched.MatchedContainerFragment;
import com.aiyaschool.aiya.love.unmatched.UnmatchedContainerFragment;
import com.aiyaschool.aiya.me.MeFragment;
import com.aiyaschool.aiya.message.MsgListFragment;
import com.aiyaschool.aiya.util.StatusBarUtil;

/**
 * 主体为NoScrollViewPager+BottomNavigationView的主界面
 * Created by EGOISTK21 on 2017/3/13.
 */

public class MainActivity extends AppCompatActivity {

    private static final int PAGE_COUNT = 3;
    private NoScrollViewPager vpMain;
    private FragmentManager fm;
    private FragmentPagerAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private boolean isMeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.init(this);
        initView();
        initListener();
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        System.out.println("onNewIntent();");
//    }
//
//    @Override
//    protected void onRestart() {
//        Intent intent = getIntent();
//        if(!TextUtils.isEmpty(intent.getStringExtra("Flag"))){
//            String str = intent.getStringExtra("Flag");
//            System.out.println(str);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            switch (str){
//                case "Me":
//                    ft.replace(R.id.container_main,new MeFragment());
//                    ft.commit();
//                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
//                    break;
//
//            }
//        }
//        super.onRestart();
//
//    }

    private void initView() {
        final Fragment[] fragments = new Fragment[]{
                /*new CommunityFragment(),*/
                MyApplication.getInstance().isMatched()
                        ? MatchedContainerFragment.newInstance()
                        : UnmatchedContainerFragment.newInstance(),
                new MsgListFragment(),
                new MeFragment()
        };
        vpMain = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        fm = getSupportFragmentManager();
        adapter = new FragmentPagerAdapter(fm) {
            @Override
            public int getItemPosition(Object object) {
                if (((/*object instanceof || */object instanceof UnmatchedContainerFragment)
                        && MyApplication.getInstance().isMatched())
                        || ((/*object instanceof || */object instanceof MatchedContainerFragment)
                        && !MyApplication.getInstance().isMatched())) {
                    return POSITION_NONE;
                }
                if(isMeChanged&&object instanceof MeFragment){
                    return POSITION_NONE;
                }
                return POSITION_UNCHANGED;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                String fragmentTag = fragment.getTag();
                if (MyApplication.getInstance().isMatched()) {
                    if (fragment instanceof UnmatchedContainerFragment) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = MatchedContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commit();
                    }/*else if () {

                    }*/
                } else {
                    if (fragment instanceof MatchedContainerFragment) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = UnmatchedContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commit();
                    }/*else if () {

                    }*/
                }
                return fragment;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return PAGE_COUNT;
            }
        };
        vpMain.setOffscreenPageLimit(PAGE_COUNT - 1);
        vpMain.setAdapter(adapter);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        //BottomNavigationViewUtil.disableShiftMode(bottomNavigationView);
    }

    private void initListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
//                            case R.id.navigation_community:
//                                vpMain.setCurrentItem(0);
//                                return true;
                            case R.id.navigation_love:
                                vpMain.setCurrentItem(0);
                                return true;
                            case R.id.navigation_message:
                                vpMain.setCurrentItem(1);
                                return true;
                            case R.id.navigation_me:
                                vpMain.setCurrentItem(2);
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
//                            case R.id.navigation_community:
//                                break;
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

    /**
     * 在fragment里调用((MainActivity) getActivity).notifyAdapter();
     * 用来通知viewpager的adapter更新其中的fragment
     */

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode);
        System.out.println(resultCode);
        System.out.println(RESULT_OK);
        if(resultCode == RESULT_OK){
            String s = data.getStringExtra("Flag");
            isMeChanged = s.equals("Me");
            System.out.println(isMeChanged);
            notifyAdapter();
        }
    }
}
