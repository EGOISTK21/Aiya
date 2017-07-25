package com.aiyaschool.aiya.activity.splash;

import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by EGOISTK21 on 2017/7/14.
 */

public class SplashFragment1 extends BaseFragment {

    @BindView(R.id.iv_splash)
    ImageView mImageView;

    public static SplashFragment1 newInstance() {
        SplashFragment1 splashFragment1 = new SplashFragment1();
        return splashFragment1;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initView() {
        mImageView.setImageResource(R.drawable.splash1);
    }
}
