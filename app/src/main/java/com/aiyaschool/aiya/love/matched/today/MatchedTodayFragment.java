package com.aiyaschool.aiya.love.matched.today;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.activity.PersonalDataActivity;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.util.ViewUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.aiyaschool.aiya.widget.DynamicListView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.aiyaschool.aiya.activity.main.MainActivity.DESTROY_LOVE;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedTodayFragment extends BaseFragment implements MatchedTodayContract.View {

    private static final String TAG = "MatchedTodayFragment";
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
    @BindView(R.id.tv_love_date)
    TextView tvLoveDate;
    @BindView(R.id.tv_love_day)
    TextView tvLoveDay;
    @BindView(R.id.lv_today)
    DynamicListView lvToday;

    public static MatchedTodayFragment newInstance() {
        return new MatchedTodayFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_matched_today;
    }

    @Override
    protected void initView() {
        mPresenter = new MatchedTodayPresenter(this);
        User me = UserUtil.getUser();
        Glide.with(this).load(me.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                .transform(new GlideCircleTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivLeftAvatar);
        tvLeftUsername.setText(me.getUsername());
        tvLeftSchool.setText(me.getSchool());
        User ta = UserUtil.getTa();
        Glide.with(this).load(ta.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                .transform(new GlideCircleTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivRightAvatar);
        tvRightUsername.setText(ta.getUsername());
        tvRightSchool.setText(ta.getSchool());
        mPresenter.getIntimacy(me.getLoveId());
        tvLoveDate.setText((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        tvLoveDay.setText("DAY " + (int) ((System.currentTimeMillis() / 1000 / 60 / 60 + 8) / 24 - (ta.getStartdate() / 60 / 60 + 8) / 24 + 1));
        mPresenter.getTodayTask("1");
    }


    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.iv_matched_left)
    void showMe() {
        startActivity(new Intent(getContext(), PersonalDataActivity.class));
    }

    @OnClick(R.id.iv_matched_right)
    void showTa() {
        Bundle bundle = new Bundle();
        bundle.putInt("card_flag", 3);
        startActivityForResult(new Intent(getContext(), OtherDetailActivity.class).putExtras(bundle), DESTROY_LOVE);
    }

    @OnClick(R.id.ll_intimacy)
    void showIntimacy() {
//        startActivity(new Intent(getContext(), IntimacyDetailActivity.class));
        startActivity(new Intent(getContext(), IntimacyRulesActivity.class));
    }

    @Override
    public void setIntimacy(String intimacy) {
        tvIntimacyNum.setText(intimacy);
    }

    @Override
    public void setTodayTask(List<String> todayTask) {
        Log.i(TAG, "setTodayTask: " + todayTask);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, todayTask);
        lvToday.setAdapter(adapter);
        ViewUtil.setListViewHeightBasedOnChildren(lvToday);
    }

}
