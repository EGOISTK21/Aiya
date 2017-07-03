package com.aiyaschool.aiya.activity;

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
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.love.matched.MatchedContainerFragment;
import com.aiyaschool.aiya.love.unmatched.UnmatchedContainerFragment;
import com.aiyaschool.aiya.me.MeFragment;
import com.aiyaschool.aiya.message.MatchedMessageContainerFragment;
import com.aiyaschool.aiya.message.UnmatchedMessageContainerFragment;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.NoScrollViewPager;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import java.util.ArrayList;

import butterknife.BindDrawable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * 主体为NoScrollViewPager+BottomNavigationView的主界面
 * Created by EGOISTK21 on 2017/3/13.
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final int DESTROY_LOVE = 100;
    private static final int PAGE_COUNT = 3;
    private static final int REQUEST_IMAGE = 202;
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
    public ArrayList<String> mSelectPath = new ArrayList<>();

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
                    UserUtil.getUser().isMatched()
                            ? UnmatchedMessageContainerFragment.newInstance()
                            : MatchedMessageContainerFragment.newInstance(),
                    new MeFragment()
            };

            @Override
            public int getItemPosition(Object object) {
                if (((object instanceof UnmatchedContainerFragment || object instanceof UnmatchedMessageContainerFragment)
                        && UserUtil.getUser().isMatched())
                        || ((object instanceof MatchedContainerFragment || object instanceof MatchedMessageContainerFragment)
                        && !UserUtil.getUser().isMatched())
                        || (object instanceof MatchedMessageContainerFragment && UserUtil.getMsgFlag())) {
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
                    } else if (fragment instanceof UnmatchedMessageContainerFragment) {
                        Log.i(TAG, "instantiateItem: " + fragmentTag);
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = MatchedMessageContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commitAllowingStateLoss();
                    } else if (fragment instanceof MatchedMessageContainerFragment) {
                        Log.i(TAG, "instantiateItem: " + fragmentTag);
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = MatchedMessageContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commitAllowingStateLoss();
                    }
                } else {
                    if (fragment instanceof MatchedContainerFragment) {
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = UnmatchedContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commitAllowingStateLoss();
                    } else if (fragment instanceof MatchedMessageContainerFragment) {
                        Log.i(TAG, "instantiateItem: " + fragmentTag);
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.remove(fragment);
                        fragment = UnmatchedMessageContainerFragment.newInstance();
                        ft.add(container.getId(), fragment, fragmentTag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.attach(fragment);
                        ft.commitAllowingStateLoss();
                    }
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
        initLover();
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
                                notifyAdapter();
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

    private void initLover() {
        if (UserUtil.getUser().isMatched()) {
            APIUtil.getLoverInfoApi()
                    .getLoverInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<HttpResult<User>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.i(TAG, "onSubscribe: initView");
                        }

                        @Override
                        public void onNext(@NonNull HttpResult<User> userHttpResult) {
                            Log.i(TAG, "onNext: initView " + userHttpResult);
                            if ("2000".equals(userHttpResult.getState())) {
                                UserUtil.setTa(userHttpResult.getData());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.i(TAG, "onError: initView " + e);
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "onComplete: initView");
                        }
                    });
        }
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
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                //上传背景,若成功就更新本地user的background
                isMeChanged = true;
                return;
            }
        } else {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra("flag");
                switch (s) {
                    case "me":
                        isMeChanged = true;
                        break;
                    case "destroyLove":
                        notifyAdapter();
                        break;
                }
            }
        }

    }
}
