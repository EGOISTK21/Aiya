package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.OtherDetailActivity;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/3/31.
 */

public class MatchResultFragment extends LazyFragment implements MatchResultContract.View {

    private View rootView;
    private MatchResultContract.Presenter mPresenter;
    private List<User> mResult;
    private int mCount;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_school)
    TextView tvSchool;

    public static MatchResultFragment newInstance() {
        return new MatchResultFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MatchResultPresenter(this);
        mCount = 0;
        mResult = getArguments().getParcelableArrayList("result");
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match_result, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        getFragmentManager().popBackStack();
    }

    @OnClick(value = R.id.ll_result_card)
    void showOtherDetail() {
        startActivity(new Intent(getContext(), OtherDetailActivity.class));
    }

    @OnClick(value = R.id.btn_have_a_change)
    void haveAChange() {
        initView();
    }

    private void initView() {
        if (mResult != null && mCount < mResult.size()) {
            User current = mResult.get(mCount++);
            //ivAvatar.setImageBitmap(Bitmap.createBitmap(current.getAvatar().getNormal()));
            tvUsername.setText(current.getUsername());
            tvSchool.setText(current.getSchool());
        } else {
            ToastUtil.show("再点一下，回到第一位");
            mCount = 0;
        }
    }
}
