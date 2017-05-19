package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/3/31.
 */

public class MatchResultFragment extends BaseFragment
        implements MatchResultContract.View {

    private List<User> mResult;
    private MatchResultContract.Presenter mPresenter;
    private int mCount;
    private User current;
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
    protected int getLayoutId() {
        return R.layout.fragment_match_result;
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
        mCount = 0;
        mResult = getArguments().getParcelableArrayList("result");
        initIvAvatar();
        if (mResult != null && mCount < mResult.size()) {
            current = mResult.get(mCount++);
            //ivAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, new BitmapFactory.Options()));
            //ivAvatar.setImageBitmap(Bitmap.createBitmap(current.getAvatar().getNormal()));
            tvUsername.setText(current.getUsername());
            tvSchool.setText(current.getSchool());
        }
        mPresenter = new MatchResultPresenter(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        getFragmentManager().popBackStack();
    }

    @OnClick(value = R.id.ll_result_card)
    void showOtherDetail() {
        if (current != null) {
            mPresenter.getOtherDetail(current.getId());
        }
    }

    @Override
    public void startOtherDetailActivity(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("other detail", user);
        startActivity(new Intent(getContext(), OtherDetailActivity.class).putExtras(bundle));
    }

    @OnClick(value = R.id.btn_have_a_change)
    void haveAChange() {
        if (mResult != null && mCount < mResult.size()) {
            ToastUtil.cancle();
            current = mResult.get(mCount++);
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
