package com.aiyaschool.aiya.love.matched.today;

import android.content.Intent;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.activity.PersonalDataActivity;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aiyaschool.aiya.activity.main.MainActivity.DESTROY_LOVE;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedTodayFragment extends BaseFragment implements MatchedTodayContract.View {

    //    private FragmentManager fm;
//    private FragmentTransaction ft;
    private MatchedTodayContract.Presenter mPresenter;
    @BindView(R.id.iv_matched_left)
    CircleImageView ivLeftAvatar;
    @BindView(R.id.tv_matched_username_left)
    TextView tvLeftUsername;
    @BindView(R.id.tv_matched_school_left)
    TextView tvLeftSchool;
    @BindView(R.id.iv_matched_right)
    CircleImageView ivRightAvatar;
    @BindView(R.id.tv_matched_username_right)
    TextView tvRightUsername;
    @BindView(R.id.tv_matched_school_right)
    TextView tvRightSchool;
    @BindView(R.id.tv_match_intimacy_num)
    TextView tvIntimacyNum;

    public static MatchedTodayFragment newInstance() {
        return new MatchedTodayFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_matched_today;
    }

    @Override
    protected void initView() {
//        fm = getParentFragment().getFragmentManager();
//        ft = fm.beginTransaction();
        mPresenter = new MatchedTodayPresenter(this);
        User ta = UserUtil.getTa();
        Glide.with(this).load(ta.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                .transform(new GlideCircleTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivLeftAvatar);
        tvLeftUsername.setText(ta.getUsername());
        tvLeftSchool.setText(ta.getSchool());
        User me = UserUtil.getUser();
        Glide.with(this).load(me.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                .transform(new GlideCircleTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivRightAvatar);
        tvRightUsername.setText(me.getUsername());
        tvRightSchool.setText(me.getSchool());
        mPresenter.getIntimacy();
    }


    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.iv_matched_left)
    void showTa() {
        startActivityForResult(new Intent(getContext(), OtherDetailActivity.class), DESTROY_LOVE);
    }

    @OnClick(R.id.iv_matched_right)
    void showMe() {
        startActivity(new Intent(getActivity(), PersonalDataActivity.class));
    }

    @OnClick(R.id.ll_intimacy)
    void showIntimacy() {
        startActivity(new Intent(getContext(), IntimacyDetailActivity.class));
    }

    @Override
    public void setIntimacy(String intimacy) {
        tvIntimacyNum.setText(intimacy);
    }
}
