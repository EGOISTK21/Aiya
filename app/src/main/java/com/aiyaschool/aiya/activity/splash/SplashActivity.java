package com.aiyaschool.aiya.activity.splash;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.activity.form.FormActivity;
import com.aiyaschool.aiya.activity.sign.SignActivity;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.UserUtil;

import butterknife.BindView;

/**
 * app启动动画View实现类，包括自动登录功能
 * Created by EGOISTK21 on 2017/5/1.
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    private static final String TAG = "SplashActivity";
    private SplashContract.Presenter mPresenter;
    @BindView(R.id.vp_splash)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mPresenter = new SplashPresenter(this);

        if (UserUtil.isFirst()) {
            mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                private Fragment[] mFragments = new Fragment[]{
                        SplashFragment1.newInstance(),
                        SplashFragment2.newInstance(),
                        SplashFragment3.newInstance()

                };

                @Override
                public Fragment getItem(int position) {
                    return mFragments[position];
                }

                @Override
                public int getCount() {
                    return mFragments.length;
                }
            });
        } else {
            mPresenter.init(SignUtil.getPhone(), SignUtil.getLoginToken());
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void startSignView() {
        Log.i(TAG, "startSignView");
        startActivity(new Intent(this, SignActivity.class));
        finish();
    }

    @Override
    public void startFormView() {
        Log.i(TAG, "startFormView");
        startActivity(new Intent(this, FormActivity.class));
        finish();
    }

    @Override
    public void startMainView() {
        Log.i(TAG, "startMainView");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
