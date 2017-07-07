package com.aiyaschool.aiya.activity;

import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;

public class AvatarActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_avatar;
    }

    @Override
    protected void initView() {
        Glide.with(this).load(getIntent().getStringExtra("url")).error(R.color.black).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(ivAvatar);
    }
}
