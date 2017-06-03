package com.aiyaschool.aiya.activity.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.love.matched.MatchedContainerFragment;
import com.aiyaschool.aiya.love.unmatched.UnmatchedContainerFragment;
import com.aiyaschool.aiya.me.MeFragment;
import com.aiyaschool.aiya.message.MessageFragment;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.NoScrollViewPager;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import butterknife.BindDrawable;

/**
 * 主体为NoScrollViewPager+BottomNavigationView的主界面
 * Created by EGOISTK21 on 2017/3/13.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final int DESTROY_LOVE = 100;
    private static final int PAGE_COUNT = 3;
    private NoScrollViewPager vpMain;
    private FragmentManager fm;
    private FragmentPagerAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    @BindDrawable(R.drawable.love_icon)
    Drawable love;
    @BindDrawable(R.drawable.message_icon)
    Drawable message;
    @BindDrawable(R.drawable.me_icon)
    Drawable me;

    private boolean isMeChanged;

    @Override
    protected int getLayoutId() {
        SignUtil.addAccessToken();
        loginTIM();
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        vpMain = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        fm = getSupportFragmentManager();
        adapter = new FragmentPagerAdapter(fm) {

            private Fragment[] fragments = new Fragment[]{
                /*new CommunityFragment(),*/
                    UserUtil.getUser().isMatched()
                            ? MatchedContainerFragment.newInstance()
                            : UnmatchedContainerFragment.newInstance(),
                    MessageFragment.newInstance(),
                    new MeFragment()
            };

            @Override
            public int getItemPosition(Object object) {
                if (((/*object instanceof || */object instanceof UnmatchedContainerFragment)
                        && UserUtil.getUser().isMatched())
                        || ((/*object instanceof || */object instanceof MatchedContainerFragment)
                        && !UserUtil.getUser().isMatched())) {
                    return POSITION_NONE;
                }
                if (isMeChanged && object instanceof MeFragment) {
                    isMeChanged = false;
                    return POSITION_NONE;
                }
                return POSITION_UNCHANGED;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                String fragmentTag = fragment.getTag();
                if (UserUtil.getUser().isMatched()) {
                    if (fragment instanceof UnmatchedContainerFragment) {
                        Log.i(TAG, "instantiateItem: " + fragmentTag);
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = MatchedContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commitAllowingStateLoss();
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
                        ft.commitAllowingStateLoss();
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
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.selector_color));
        //BottomNavigationViewUtil.disableShiftMode(bottomNavigationView);
        initListener();
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
                            case R.id.navigation_love:
                                break;
                            case R.id.navigation_message:
                                break;
                            case R.id.navigation_me:
                                break;
                        }
                    }
                });
    }

    private void loginTIM() {
        TIMUser timUser = new TIMUser();
        timUser.setIdentifier(SignUtil.getPhone());
        TIMManager.getInstance().login(MyApplication.APP_ID, timUser, UserUtil.getUser().getUserSig(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError: login " + i + " " + s);
            }

            @Override
            public void onSuccess() {
                Log.i(TAG, "onSuccess: login");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyAdapter();
    }

    /**
     * 在fragment里调用((MainActivity) getActivity).notifyAdapter();
     * 用来通知viewpager的adapter更新其中的fragment
     */

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void setLovePage() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_love);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.i(TAG, "onActivityResult: " + requestCode + " " + resultCode + " " + data.getStringExtra("flag"));
        }

        if (resultCode == RESULT_OK) {
            String s = data.getStringExtra("flag");
            switch (s) {
                case "me":
                    isMeChanged = true;
                case "destroyLove":
                    notifyAdapter();
                    break;
            }
        }
    }
}
