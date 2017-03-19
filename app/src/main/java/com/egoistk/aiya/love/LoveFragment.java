package com.egoistk.aiya.love;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.egoistk.aiya.LazyFragment;
import com.egoistk.aiya.MyApplication;
import com.egoistk.aiya.R;
import com.egoistk.aiya.love.presenter.ILovePresenter;
import com.egoistk.aiya.love.presenter.LovePresenter;
import com.egoistk.aiya.love.view.ILoveView;

/**
 * Created by EGOISTK on 2017/3/16.
 */

public class LoveFragment extends LazyFragment implements ILoveView, CompoundButton.OnCheckedChangeListener {

    private int sum;
    private int[] prices;
    private ILovePresenter iLovePresenter;
    private Switch[] switchsLove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final boolean isMatched = ((MyApplication)getActivity().getApplicationContext()).getIsMatched();
        View rootView = inflater.inflate(isMatched?
                R.layout.fragment_love_matched:R.layout.fragment_love_unmatched, container, false);
        initView(isMatched, rootView);
        iLovePresenter = new LovePresenter(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        iLovePresenter.setLoveView(null);
    }

    private void initView(boolean isMatched, View rootView) {
        if (!isMatched) {
            sum = 0;
            prices = new int[]{5, 4, 2, 2, 1, 0};
            switchsLove = new Switch[5];

            TextView tvLoveMatchAtRandom = (TextView) rootView.findViewById(R.id.tv_love_match_at_random);
            Button btnLoveHeightPicker = (Button) rootView.findViewById(R.id.btn_love_height_picker);
            Button btnLoveSchoolMajorPicker = (Button) rootView.findViewById(R.id.btn_love_school_major_picker);
            Button btnLoveStartMatch = (Button) rootView.findViewById(R.id.btn_love_start_match);
            switchsLove[0] = ((Switch) rootView.findViewById(R.id.switch_love_height));
            switchsLove[1] =((Switch) rootView.findViewById(R.id.switch_love_age));
            switchsLove[2] = ((Switch) rootView.findViewById(R.id.switch_love_school_major));
            switchsLove[3] = ((Switch) rootView.findViewById(R.id.switch_love_hometown));
            switchsLove[4] = ((Switch) rootView.findViewById(R.id.switch_love_constellation));

            tvLoveMatchAtRandom.setOnClickListener(this);
            btnLoveHeightPicker.setOnClickListener(this);
            btnLoveSchoolMajorPicker.setOnClickListener(this);
            btnLoveStartMatch.setOnClickListener(this);
            for (Switch s: switchsLove) s.setOnCheckedChangeListener(this);

            Spannable spannable = new SpannableString("你当前还是单身状态，快去和你的Ta相遇吧！");
            spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimaryDark)), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) rootView.findViewById(R.id.tv_love_warn_single)).setText(spannable);
            btnLoveSchoolMajorPicker.setText(btnLoveSchoolMajorPicker.getText().subSequence(0, 4) + "...");
        } else {}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_love_match_at_random:
                break;
            case R.id.btn_love_height_picker:
                break;
            case R.id.btn_love_school_major_picker:
                break;
            case R.id.btn_love_start_match:
                break;
        }
        super.onClick(v);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_love_height:
                iLovePresenter.changeSwitchsStatus(isChecked, 0);
                break;
            case R.id.switch_love_age:
                iLovePresenter.changeSwitchsStatus(isChecked, 1);
                break;
            case R.id.switch_love_school_major:
                iLovePresenter.changeSwitchsStatus(isChecked, 2);
                break;
            case R.id.switch_love_hometown:
                iLovePresenter.changeSwitchsStatus(isChecked, 3);
                break;
            case R.id.switch_love_constellation:
                iLovePresenter.changeSwitchsStatus(isChecked, 4);
                break;
        }
    }

    @Override
    public void onChangeSwitchsStatus(boolean isChecked, int index) {
        sum += isChecked? (sum + prices[index] > 7? 0:prices[index]):-prices[index];
        if (sum > 4) {
            switchsLove[0].setClickable(switchsLove[0].isChecked());
            switchsLove[1].setClickable(switchsLove[1].isChecked());
            switchsLove[2].setClickable(sum < 6 || switchsLove[2].isChecked());
            switchsLove[3].setClickable(sum < 6 || switchsLove[3].isChecked());
            switchsLove[4].setClickable(sum < 7 || switchsLove[4].isChecked());
        } else {
            switchsLove[0].setClickable(sum < 3);
            switchsLove[1].setClickable(sum < 4 || switchsLove[1].isChecked());
            switchsLove[2].setClickable(true);
            switchsLove[3].setClickable(true);
            switchsLove[4].setClickable(true);
        }
    }
}