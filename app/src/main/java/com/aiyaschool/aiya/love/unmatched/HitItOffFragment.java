package com.aiyaschool.aiya.love.unmatched;

import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/5/13.
 */

public class HitItOffFragment extends BaseFragment {

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

    public static HitItOffFragment newInstance() {
        return new HitItOffFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hit_it_off;
    }

    @Override
    protected void initView() {
        User user = getArguments().getParcelable("hit it off");
        if (user != null) {
            Glide.with(this).load(user.getAvatar().getThumb().getFace()).centerCrop()
                    .transform(new GlideRoundTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivAvatar);
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
        getFragmentManager().popBackStack();
        ((MainActivity) getActivity()).notifyAdapter();
    }

}
