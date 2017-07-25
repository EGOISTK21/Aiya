package com.aiyaschool.aiya.activity.splash;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.util.UserUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/7/14.
 */

public class SplashFragment3 extends BaseFragment {

    @BindView(R.id.iv_splash)
    ImageView mImageView;
    @BindView(R.id.btn_splash)
    Button mButton;

    public static SplashFragment3 newInstance() {
        SplashFragment3 splashFragment3 = new SplashFragment3();
        return splashFragment3;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initView() {
        mImageView.setImageResource(R.drawable.splash3);
        mButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_splash)
    void start() {
        UserUtil.setFirst(false);
        ((SplashActivity) getActivity()).startSignView();
    }
}
