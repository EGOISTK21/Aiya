package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.OtherDetailActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/3/31.
 */

public class MatchResultFragment extends BaseFragment {

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
        mCount = 0;
        mResult = getArguments().getParcelableArrayList("result");
    }

    private void initIvAvatar() {
        ivAvatar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                ivAvatar.getViewTreeObserver().removeOnPreDrawListener(this);
                ivAvatar.setMinimumHeight(ivAvatar.getMeasuredWidth());
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        initIvAvatar();
        if (mResult != null && mCount < mResult.size()) {
            User current = mResult.get(mCount++);
            //ivAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, new BitmapFactory.Options()));
            //ivAvatar.setImageBitmap(Bitmap.createBitmap(current.getAvatar().getNormal()));
            tvUsername.setText(current.getUsername());
            tvSchool.setText(current.getSchool());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_match_result;
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        getFragmentManager().popBackStack();
    }

    @OnClick(value = R.id.card_match_result)
    void showOtherDetail() {
        startActivity(new Intent(getContext(), OtherDetailActivity.class));
    }

    @OnClick(value = R.id.btn_have_a_change)
    void haveAChange() {
        if (mResult != null && mCount < mResult.size()) {
            ToastUtil.cancle();
            User current = mResult.get(mCount++);
            //ivAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, new BitmapFactory.Options()));
            //ivAvatar.setImageBitmap(Bitmap.createBitmap(current.getAvatar().getNormal()));
            tvUsername.setText(current.getUsername());
            tvSchool.setText(current.getSchool());
        } else {
            ToastUtil.show("再点一下，回到第一位");
            mCount = 0;
        }
    }
}
