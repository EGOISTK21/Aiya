package com.aiyaschool.aiya.love.unmatched.matchResult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.OtherCardActivity;
import com.aiyaschool.aiya.base.LazyFragment;

/**
 * Created by EGOISTK21 on 2017/3/31.
 */

public class MatchResultFragment extends LazyFragment implements MatchResultContract.View {

    private View rootView;
    private MatchResultContract.Presenter presenter;
    private ImageView ivAvatar;
    private TextView tvNick, tvSchool;

    public static MatchResultFragment newInstance() {
        return new MatchResultFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MatchResultPresenter(getContext(), this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match_result, container, false);
        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        ivAvatar = (ImageView) rootView.findViewById(R.id.iv_avatar);
        tvNick = (TextView) rootView.findViewById(R.id.tv_username);
        tvSchool = (TextView) rootView.findViewById(R.id.tv_school);
        presenter.loadData();
    }

    private void initListener() {
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
        rootView.findViewById(R.id.ll_result_card).setOnClickListener(this);
    }

    @Override
    public void setData(Bitmap bm, String nick, String school) {
        ivAvatar.setImageBitmap(bm);
        tvNick.setText(nick);
        tvSchool.setText(school);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.ll_result_card:
                startActivity(new Intent(getContext(), OtherCardActivity.class));
                break;
        }
    }

}
