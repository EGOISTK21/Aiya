package com.aiyaschool.aiya.love.unmatched.fateMatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.love.unmatched.HitItOffActivity;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/3/29.
 */

public class FateMatchFragment extends BaseFragment implements FateMatchContract.View {

    private FateMatchContract.Presenter mPresenter;
    private FragmentManager fm;
    @BindView(R.id.switch_fate)
    SwitchCompat swFate;

    public static FateMatchFragment newInstance() {
        return new FateMatchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fate_match;
    }

    @Override
    protected void initView() {
        fm = getFragmentManager();
        mPresenter = new FateMatchPresenter(this);
        mPresenter.initFateSwitch();
        Spannable spannable = new SpannableString("发起后将立即与一名异性随缘成为七天情侣");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 4, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) rootView.findViewById(R.id.tv_love_match_at_random_warn)).setText(spannable);
    }

    @OnCheckedChanged(value = R.id.switch_fate)
    void changeFate(CompoundButton compoundButton) {
        mPresenter.commitFateSwitch(compoundButton.isChecked());
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        fm.popBackStack();
    }

    @OnClick(value = R.id.btn_start_fate_match)
    void startFateMatch() {
        if ("1".equals(UserUtil.getUser().getSex()) && "1".equals(UserUtil.getUser().getGroup())) {
            ToastUtil.show("男性用户只有开通会员才能主动发起随缘匹配");
        } else {
            mPresenter.startFateMatch();
        }
    }

    @Override
    public void setFateSwitch(boolean isFated) {
        swFate.setChecked(isFated);
        UserUtil.setFateSwitch(String.valueOf(isFated));
    }

    @Override
    public void fate(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("hit it off", user);
        startActivity(new Intent(getContext(), HitItOffActivity.class).putExtras(bundle));
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ((MainActivity) getActivity()).notifyAdapter();
    }
}
