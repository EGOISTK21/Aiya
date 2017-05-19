package com.aiyaschool.aiya.love.unmatched;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/5/13.
 */

public class HitItOffActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_character)
    TextView tvCharacter;
    @BindView(R.id.tv_constellation)
    TextView tvConstellation;
    @BindView(R.id.tv_hobby)
    TextView tvHobby;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hit_it_off;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("hit it off");
        if (user != null) {
            Glide.with(this).load(user.getAvatar().getThumb().getFace()).centerCrop()
                    .transform(new GlideRoundTransform(this)).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivAvatar);
            tvUsername.setText(user.getUsername());
            tvSchool.setText(user.getSchool());
            tvAge.setText(user.getAge());
            tvHeight.setText(user.getHeight());
            tvCharacter.setText(user.getCharacter());
            tvConstellation.setText(user.getConstellation());
            tvHobby.setText(user.getHobby());
        }
    }

    @OnClick(R.id.go_matched)
    void goMatched() {
        finish();
    }

}
