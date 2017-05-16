package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.love.unmatched.fateMatch.FateMatchFragment;
import com.aiyaschool.aiya.love.unmatched.matchResult.MatchResultFragment;
import com.aiyaschool.aiya.util.SchoolDBHelper;
import com.aiyaschool.aiya.util.TextViewGravitySpan;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.widget.FilletDialog;
import com.aiyaschool.aiya.widget.ScrollPickerView;
import com.aiyaschool.aiya.widget.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * 恋爱记尚未匹配页面，条件匹配View实现类
 * Created by EGOISTK21 on 2017/3/16.
 */

public class ConditionMatchFragment extends BaseFragment
        implements ConditionMatchContract.View, CompoundButton.OnCheckedChangeListener {

    private int sum, mProvince;
    private int[] prices, mSchoolNo;
    private String mHeight, mAge, mSchool, mCharacter, mConstellation;
    private FragmentTransaction ft;
    private ConditionMatchContract.Presenter mPresenter;
    @BindView(R.id.tv_fate_match)
    TextView tvFateMatch;
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
    @BindViews({R.id.iv_height, R.id.iv_age, R.id.iv_school, R.id.iv_character, R.id.iv_constellation})
    ImageView[] mImageViews;
    @BindViews({R.id.sw_height, R.id.sw_age, R.id.sw_school, R.id.sw_character, R.id.sw_constellation, R.id.sw_shield_contact})
    SwitchCompat[] switches;
    private FilletDialog dialogHeightPicker, dialogAgePicker,
            dialogSchoolPicker, dialogCharacterPicker, dialogConstellationPicker;
    private StringScrollPicker sspHeight, sspAge, sspProvince, sspSchool, sspCharacter, sspConstellation;
    private ProgressDialog mPD;

    public static ConditionMatchFragment newInstance() {
        return new ConditionMatchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_condition_match;
    }

    @Override
    protected void initView() {
        sum = 0;
        prices = new int[]{5, 4, 2, 2, 1, 0};
        mHeight = (String) tvHeightPicker.getText();
        mAge = (String) tvAgePicker.getText();
        mSchool = "西安电子科技大学";
        mCharacter = (String) tvCharacterPicker.getText();
        mConstellation = (String) tvConstellationPicker.getText();
        mSchoolNo = new int[31];
        mPresenter = new ConditionMatchPresenter(this);
        mPresenter.initContactShield();
        ft = getFragmentManager().beginTransaction();
        Spannable spannable = new SpannableString("你当前还是单身状态，快去和你的Ta相遇吧！");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_single_warn)).setText(spannable);
        Parcel p = Parcel.obtain();
        p.writeFloat(1.5f);
        p.setDataPosition(0);
        spannable = new SpannableString("X7快去DIY一个七天的Ta吧!");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 0, 1, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new RelativeSizeSpan(p), 1, 2, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new TextViewGravitySpan(getContext(), 23), 1, 2, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_DIY_ta)).setText(spannable);
        spannable = new SpannableString("X5");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 0, 1, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new RelativeSizeSpan(p), 1, 2, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new TextViewGravitySpan(getContext(), 23), 1, 2, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_height_price)).setText(spannable);
        spannable = new SpannableString("X4");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 0, 1, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new RelativeSizeSpan(p), 1, 2, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new TextViewGravitySpan(getContext(), 23), 1, 2, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_age_price)).setText(spannable);
        spannable = new SpannableString("X2");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 0, 1, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new RelativeSizeSpan(p), 1, 2, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new TextViewGravitySpan(getContext(), 23), 1, 2, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_school_price)).setText(spannable);
        ((TextView) rootView.findViewById(R.id.tv_character_price)).setText(spannable);
        spannable = new SpannableString("X1");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 0, 1, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new RelativeSizeSpan(p), 1, 2, Spanned.SPAN_POINT_MARK);
        spannable.setSpan(new TextViewGravitySpan(getContext(), 23), 1, 2, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_constellation_price)).setText(spannable);
        initListener();
    }

    private void initListener() {
        for (SwitchCompat s : switches) {
            s.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onDestroy() {
        SchoolDBHelper.closeDB();
        mPresenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.tv_fate_match)
    void fateMatch() {
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container_love, FateMatchFragment.newInstance()).commit();
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
                            sspHeight.setSelectedItem(mHeight);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mHeight = sspHeight.getSelectedItem();
                            tvHeightPicker.setText(mHeight);
                            if (switches[0].isClickable()) {
                                switches[0].setChecked(true);
                            } else {
                                ToastUtil.show("最多只能选7❤️哦");
                            }
                            dialog.dismiss();
                        }
                    }).create();
            setHeightData();
        }
        dialogHeightPicker.show();
    }

    @OnClick(value = R.id.tv_age_picker)
    void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("年龄")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspAge.setSelectedItem(mAge);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAge = sspAge.getSelectedItem();
                            tvAgePicker.setText(mAge);
                            if (switches[1].isClickable()) {
                                switches[1].setChecked(true);
                            } else {
                                ToastUtil.show("最多只能选7❤️哦");
                            }
                            dialog.dismiss();
                        }
                    }).create();
            setAgeData();
        }
        dialogAgePicker.show();
    }

    @OnClick(value = R.id.tv_school_picker)
    void showDialogSchoolPicker() {
        if (dialogSchoolPicker == null) {
            dialogSchoolPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_school_picker)
                    .setTitle("学校")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSchool.setSelectedItem(mSchool);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSchool = sspSchool.getSelectedItem();
                            tvSchoolPicker.setText(mSchool.length() > 4 ? mSchool.subSequence(0, 4) + "…" : mSchool);
                            if (switches[2].isClickable()) {
                                switches[2].setChecked(true);
                            } else {
                                ToastUtil.show("最多只能选7❤️哦");
                            }
                            dialog.dismiss();
                        }
                    }).create();
            setProvinceData();
        }
        dialogSchoolPicker.show();
    }

    @OnClick(R.id.tv_character_picker)
    void showDialogCharacterPicker() {
        if (dialogCharacterPicker == null) {
            dialogCharacterPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("性格")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspCharacter.setSelectedItem(mCharacter);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mCharacter = sspCharacter.getSelectedItem();
                            tvCharacterPicker.setText(mCharacter);
                            if (switches[3].isClickable()) {
                                switches[3].setChecked(true);
                            } else {
                                ToastUtil.show("最多只能选7❤️哦");
                            }
                            dialog.dismiss();
                        }
                    }).create();
            setCharacterData();
        }
        dialogCharacterPicker.show();
    }

    @OnClick(R.id.tv_constellation_picker)
    void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspConstellation.setSelectedItem(mConstellation);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mConstellation = sspConstellation.getSelectedItem();
                            tvConstellationPicker.setText(mConstellation);
                            if (switches[4].isClickable()) {
                                switches[4].setChecked(true);
                            } else {
                                ToastUtil.show("最多只能选7❤️哦");
                            }
                            dialog.dismiss();
                        }
                    }).create();
            setConstellationData();
        }
        dialogConstellationPicker.show();
    }

    @OnClick(R.id.btn_start_condition_match)
    void startConditionMatch() {
        if (sum == 0) {
            ToastUtil.show("请至少选择一个选项");
        } else {
            mPresenter.startConditionMatch(
                    switches[0].isChecked() ? mHeight : null,
                    switches[1].isChecked() ? mAge : null,
                    switches[2].isChecked() ? mSchool : null,
                    switches[3].isChecked() ? mCharacter : null,
                    switches[4].isChecked() ? mConstellation : null);
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

    public void setProvinceData() {
        if (sspProvince == null || sspSchool == null) {
            sspProvince = (StringScrollPicker) dialogSchoolPicker.findViewById(R.id.ssp_province);
            sspSchool = (StringScrollPicker) dialogSchoolPicker.findViewById(R.id.ssp_school);
            sspProvince.setData(SchoolDBHelper.PROVINCE);
            sspProvince.setSelectedPosition(mProvince);
            mPresenter.loadSchoolData(SchoolDBHelper.PROVINCE.get(mProvince));
            sspProvince.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
                @Override
                public void onSelected(ScrollPickerView scrollPickerView, int position) {
                    mProvince = position;
                    mPresenter.loadSchoolData(SchoolDBHelper.PROVINCE.get(position));
                }
            });
        }
    }

    @Override
    public void setSchoolData(List<String> data) {
        sspSchool.setData(data);
        sspSchool.setSelectedPosition(mSchoolNo[mProvince]);
        sspSchool.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                mSchoolNo[mProvince] = position;
            }
        });
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

    private void updateImageViewStatus(boolean isChecked, int index) {
        mImageViews[index].setImageDrawable(ContextCompat.getDrawable(getContext(),
                isChecked ? R.drawable.heart_on : R.drawable.heart_off));
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
        sum += isChecked ? (sum + prices[index] > 7 ? 0 : prices[index]) : -prices[index];
        if (sum == 7) {
            ToastUtil.show("你已经选满7❤了️");
        }
        updateImageViewStatus(isChecked, index);
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