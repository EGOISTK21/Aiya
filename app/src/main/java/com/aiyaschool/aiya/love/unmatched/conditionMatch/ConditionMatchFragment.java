package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.love.unmatched.matchResult.MatchResultFragment;
import com.aiyaschool.aiya.love.unmatched.randomMatch.RandomMatchFragment;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.widget.FilletDialog;
import com.aiyaschool.aiya.widget.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/3/16.
 */

public class ConditionMatchFragment extends LazyFragment
        implements ConditionMatchContract.View, CompoundButton.OnCheckedChangeListener {

    private int sum;
    private int[] prices = new int[]{5, 4, 2, 2, 1, 0};
    private String tmpSchool;
    private FragmentTransaction ft;
    private View rootView;
    private ConditionMatchContract.Presenter mPresenter;
    @BindView(R.id.tv_random_match)
    TextView tvRandomMatch;
    @BindView(R.id.tv_height_picker)
    TextView tvHeightPicker;
    @BindView(R.id.tv_age_picker)
    TextView tvAgePicker;
    @BindView(R.id.tv_school_picker)
    TextView tvSchoolPicker;
    @BindView(R.id.tv_character_picker)
    TextView tvCharacterPicker;
    @BindView(R.id.tv_constellation_picker)
    TextView tvConstellationPicker;
    @BindView(R.id.btn_start_condition_match)
    Button btnStartConditionMatch;
    @BindViews({R.id.sw_height, R.id.sw_age, R.id.sw_school, R.id.sw_character, R.id.sw_constellation, R.id.sw_shield_contact})
    SwitchCompat[] switches;
    private FilletDialog dialogHeightPicker, dialogAgePicker,
            dialogSchoolPicker, dialogCharacterPicker, dialogConstellationPicker;
    private StringScrollPicker sspHeight, sspAge, sspSchool, sspCharacter, sspConstellation;
    private ProgressDialog mPD;

    public static ConditionMatchFragment newInstance() {
        return new ConditionMatchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ConditionMatchPresenter(this);
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
        rootView = inflater.inflate(R.layout.fragment_love_condition_match, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        sum = 0;
        mPresenter.initContactShield();
        ft = getFragmentManager().beginTransaction();
        Spannable spannable = new SpannableString("你当前还是单身状态，快去和你的Ta相遇吧！");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_single_warn)).setText(spannable);
    }

    private void initListener() {
        for (SwitchCompat s : switches) {
            s.setOnCheckedChangeListener(this);
        }
    }

    @OnClick(R.id.tv_random_match)
    void randomMatch() {
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_love, RandomMatchFragment.newInstance()).commit();
    }

    @OnClick(value = R.id.tv_height_picker)
    void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("身高")
                    .setSubTitle("(单位:cm)")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHeight.setSelectedItem((String) tvHeightPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvHeightPicker.setText(sspHeight.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHeightPicker.show();
        setHeightData();
    }

    @OnClick(value = R.id.tv_age_picker)
    void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("年龄")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspAge.setSelectedItem((String) tvAgePicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvAgePicker.setText(sspAge.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogAgePicker.show();
        setAgeData();
    }

    @OnClick(value = R.id.tv_school_picker)
    void showDialogSchoolPicker() {
        if (dialogSchoolPicker == null) {
            dialogSchoolPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("学校")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSchool.setSelectedItem(tmpSchool);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tmpSchool = sspSchool.getSelectedItem();
                            if (tmpSchool.length() > 4) {
                                tvSchoolPicker.setText(tmpSchool.subSequence(0, 4) + "…");
                            } else {
                                tvSchoolPicker.setText(tmpSchool);
                            }
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogSchoolPicker.show();
        mPresenter.loadSchoolData();
    }

    @OnClick(R.id.tv_character_picker)
    void showDialogCharacterPicker() {
        if (dialogCharacterPicker == null) {
            dialogCharacterPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("性格")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspCharacter.setSelectedItem((String) tvCharacterPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvCharacterPicker.setText(sspCharacter.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogCharacterPicker.show();
        setCharacterData();
    }

    @OnClick(R.id.tv_constellation_picker)
    void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspConstellation.setSelectedItem((String) tvConstellationPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvConstellationPicker.setText(sspConstellation.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogConstellationPicker.show();
        setConstellationData();
    }

    @OnClick(R.id.btn_start_condition_match)
    void startConditionMatch() {
        if (sum == 0) {
            ToastUtil.show("请至少选择一个选项");
        } else {
            mPresenter.startConditionMatch(null, null, null, null, null, null, null);
        }
    }

    @Override
    public void showMatchResult(ArrayList<User> users) {
        MatchResultFragment matchResultFragment = MatchResultFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("result", users);
        matchResultFragment.setArguments(bundle);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_love, matchResultFragment).commit();
    }

    @Override
    public void setContactShield(boolean contactShield) {
        switches[5].setChecked(contactShield);
    }

    private void setHeightData() {
        if (dialogHeightPicker != null && sspHeight == null) {
            sspHeight = (StringScrollPicker) dialogHeightPicker.findViewById(R.id.ssp_single);
            sspHeight.setData(new ArrayList<>(Arrays.asList("150以下", "150–154", "155–159",
                    "160–164", "165–169", "170–174", "175–179", "180–184", "185–189", "190及以上")));
            sspHeight.setSelectedPosition(6);
        }
    }

    private void setAgeData() {
        if (dialogAgePicker != null && sspAge == null) {
            sspAge = (StringScrollPicker) dialogAgePicker.findViewById(R.id.ssp_single);
            sspAge.setData(new ArrayList<>(Arrays.asList("17", "18", "19", "20", "21", "22", "23",
                    "24", "25", "26", "27", "28")));
            sspAge.setSelectedPosition(3);
        }
    }

    private void setCharacterData() {
        if (dialogCharacterPicker != null && sspCharacter == null) {
            sspCharacter = (StringScrollPicker) dialogCharacterPicker.findViewById(R.id.ssp_single);
            sspCharacter.setData(new ArrayList<>(Arrays.asList("幽默", "温柔", "活跃", "呆萌", "内涵", "安静")));
            sspCharacter.setSelectedPosition(2);
        }
    }

    private void setConstellationData() {
        if (dialogConstellationPicker != null && sspConstellation == null) {
            sspConstellation = (StringScrollPicker) dialogConstellationPicker.findViewById(R.id.ssp_single);
            sspConstellation.setData(new ArrayList<>(Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座",
                    "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座")));
            sspConstellation.setSelectedPosition(6);
        }
    }

    @Override
    public void setSchoolData(List<String> data) {
        if (dialogSchoolPicker != null && sspSchool == null) {
            sspSchool = (StringScrollPicker) dialogSchoolPicker.findViewById(R.id.ssp_single);
            if (sspSchool != null) {
                sspSchool.setData(data);
                tvSchoolPicker.setText(sspSchool.getSelectedItem().subSequence(0, 4) + "…");
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_height:
                updateSwitchesStatus(isChecked, 0);
                break;
            case R.id.sw_age:
                updateSwitchesStatus(isChecked, 1);
                break;
            case R.id.sw_school:
                updateSwitchesStatus(isChecked, 2);
                break;
            case R.id.sw_character:
                updateSwitchesStatus(isChecked, 3);
                break;
            case R.id.sw_constellation:
                updateSwitchesStatus(isChecked, 4);
                break;
            case R.id.sw_shield_contact:
                mPresenter.commitContactShield(isChecked);
                break;
        }
    }

    public void updateSwitchesStatus(boolean isChecked, int index) {
        if (sum + prices[index] > 7) {
            ToastUtil.show("你只有七块钱哦");
        }
        sum += isChecked ? (sum + prices[index] > 7 ? 0 : prices[index]) : -prices[index];
        if (sum > 4) {
            switches[0].setClickable(switches[0].isChecked());
            switches[1].setClickable(switches[1].isChecked());
            switches[2].setClickable(sum < 6 || switches[2].isChecked());
            switches[3].setClickable(sum < 6 || switches[3].isChecked());
            switches[4].setClickable(sum < 7 || switches[4].isChecked());
        } else {
            switches[0].setClickable(sum < 3);
            switches[1].setClickable(sum < 4 || switches[1].isChecked());
            switches[2].setClickable(true);
            switches[3].setClickable(true);
            switches[4].setClickable(true);
        }
    }

    @Override
    public void showPD() {
        if (mPD == null) {
            mPD = new ProgressDialog(getContext());
        }
        if (!mPD.isShowing()) {
            mPD.show();
        }
    }

    @Override
    public void dismissPD() {
        if (mPD != null && mPD.isShowing()) {
            mPD.dismiss();
        }
    }

}