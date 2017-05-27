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
import com.aiyaschool.aiya.message.bean.HitNotification;
import com.aiyaschool.aiya.util.RefreshTokenService;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.NoScrollViewPager;
import com.tencent.TIMCallBack;
import com.tencent.TIMConnListener;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMLogListener;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMUser;
import com.tencent.TIMUserStatusListener;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    private Intent serviceIntent;
    @BindDrawable(R.drawable.love_icon)
    Drawable love;
    @BindDrawable(R.drawable.message_icon)
    Drawable message;
    @BindDrawable(R.drawable.me_icon)
    Drawable me;

    private boolean isMeChanged;

    @Override
    protected int getLayoutId() {
        startService(new Intent(this, RefreshTokenService.class));
        SignUtil.addAccessToken();
        initTIM();
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, RefreshTokenService.class));
        super.onDestroy();
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

    private void initTIM() {
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                for (TIMMessage message : list) {
                    for (int i = 0; i < message.getElementCount(); i++) {
                        Log.i(TAG, "onNewMessages: " + message.getElement(i));
                        TIMElem elem = message.getElement(i);
                        TIMElemType type = elem.getType();
                        if (type == TIMElemType.Custom) {
                            try {
                                JSONObject jsonObject = new JSONObject(new String(((TIMCustomElem) elem).getData()));
                                String requestid = jsonObject.getString("requestid");
                                String fromuserid = jsonObject.getString("fromuserid");
                                HitNotification hitNotification = new HitNotification(requestid, fromuserid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return true;
            }
        });
        if (MsfSdkUtils.isMainProcess(this)) {
            Log.d("MyApplication", "main process");
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    Log.e("MyApplication", "recv offline push");
                    notification.doNotify(getApplicationContext(), R.drawable.ic_launcher);
                }
            });
        }
        TIMManager.getInstance().setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {
                Log.i(TAG, "onConnected: initTIM");
            }

            @Override
            public void onDisconnected(int i, String s) {
                Log.i(TAG, "onDisconnected: initTIM " + i + " " + s);
            }

            @Override
            public void onWifiNeedAuth(String s) {
                Log.i(TAG, "onWifiNeedAuth: initTIM " + s);
            }
        });
        TIMManager.getInstance().setLogListener(new TIMLogListener() {
            @Override
            public void log(int i, String s, String s1) {
                Log.i(TAG, "log: " + i + " " + s + " " + s1);
            }
        });
        TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                ToastUtil.show("你的账号已在其他设备登录");
            }

            @Override
            public void onUserSigExpired() {
                ToastUtil.show("登录已过期，请重新登录");
            }
        });
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
