package com.aiyaschool.aiya.activity.splash;

import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by EGOISTK21 on 2017/7/14.
 */

public class SplashFragment2 extends BaseFragment {

    @BindView(R.id.iv_splash)
    ImageView mImageView;

    public static SplashFragment2 newInstance() {
        SplashFragment2 splashFragment2 = new SplashFragment2();
        return splashFragment2;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initView() {
        mImageView.setImageResource(R.drawable.splash2);
    }
}
