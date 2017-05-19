package com.aiyaschool.aiya.activity.OtherDetail;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/4/8.
 */

public class OtherDetailActivity extends BaseActivity implements OtherDetailContract.View {

    private static final String TAG = "OtherDetailActivity";
    private User mUser;
    private OtherDetailContract.Presenter mPresenter;
    @BindView(R.id.iv_other_avatar)
    CircleImageView ivOtherAvatar;
    @BindView(R.id.tv_other_username)
    TextView tvOtherUsername;
    @BindView(R.id.tv_other_profile)
    TextView tvOtherProfile;
    @BindView(R.id.tv_other_school)
    TextView tvOtherSchool;
    @BindView(R.id.tv_other_age)
    TextView tvOtherAge;
    @BindView(R.id.tv_other_height)
    TextView tvOtherHeight;
    @BindView(R.id.tv_other_character)
    TextView tvOtherCharacter;
    @BindView(R.id.tv_other_hobby)
    TextView tvOtherHobby;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_card;
    }

    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        mUser = bundle.getParcelable("other detail");
        Log.i(TAG, "initView: " + mUser);
        if (mUser != null) {
            tvOtherUsername.setText(mUser.getUsername());
            tvOtherProfile.setText(mUser.getProfile());
            tvOtherSchool.setText(mUser.getSchool());
            tvOtherAge.setText(mUser.getAge());
            tvOtherHeight.setText(mUser.getHeight());
            tvOtherCharacter.setText(mUser.getCharacter());
            tvOtherHobby.setText(mUser.getHobby());
        }
        mPresenter = new OtherDetailPresenter(this);
    }

    @OnClick(R.id.tv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.btn_hit)
    void hit() {
        mPresenter.touch(mUser.getId());
    }
}
