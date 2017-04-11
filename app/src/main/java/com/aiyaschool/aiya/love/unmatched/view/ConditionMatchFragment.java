package com.aiyaschool.aiya.love.unmatched.view;

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
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.FilletDialog;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.base.StringScrollPicker;
import com.aiyaschool.aiya.love.unmatched.presenter.ConditionMatchPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/3/16.
 */

public class ConditionMatchFragment extends LazyFragment
        implements ConditionMatchContract.View, CompoundButton.OnCheckedChangeListener {

    private int sum;
    private int[] prices;
    private String tmpSchool;
    private boolean isContactShield;
    private FragmentTransaction ft;
    private View rootView;
    private ConditionMatchContract.Presenter presenter;
    private TextView tvRandomMatch;
    private Button btnHeightPicker, btnAgePicker,
            btnSchoolPicker, btnHometownPicker, btnConstellationPicker, btnStartConditionMatch;
    private SwitchCompat[] switches;
    private FilletDialog dialogHeightPicker, dialogAgePicker,
            dialogSchoolPicker, dialogHometownPicker, dialogConstellationPicker;
    private StringScrollPicker sspHeight, sspAge, sspSchool, sspHometown, sspConstellation;

    public static ConditionMatchFragment newInstance() {
        ConditionMatchFragment instance = new ConditionMatchFragment();
        Bundle args = new Bundle();
        args.putBoolean("isContactShield", false);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sum = 0;
        prices = new int[]{5, 4, 2, 2, 1, 0};
        isContactShield = getArguments().getBoolean("isContactShield");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_unmatched, container, false);
        initView();
        initListener();
        presenter = new ConditionMatchPresenter(getContext(), this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void initView() {
        ft = getFragmentManager().beginTransaction();
        tvRandomMatch = (TextView) rootView.findViewById(R.id.tv_random_match);
        btnHeightPicker = (Button) rootView.findViewById(R.id.btn_height_picker);
        btnAgePicker = (Button) rootView.findViewById(R.id.btn_age_picker);
        btnSchoolPicker = (Button) rootView.findViewById(R.id.btn_school_picker);
        btnHometownPicker = (Button) rootView.findViewById(R.id.btn_hometown_picker);
        btnConstellationPicker = (Button) rootView.findViewById(R.id.btn_constellation_picker);
        btnStartConditionMatch = (Button) rootView.findViewById(R.id.btn_start_condition_match);
        switches = new SwitchCompat[6];
        switches[0] = ((SwitchCompat) rootView.findViewById(R.id.sw_height));
        switches[1] = ((SwitchCompat) rootView.findViewById(R.id.sw_age));
        switches[2] = ((SwitchCompat) rootView.findViewById(R.id.sw_school));
        switches[3] = ((SwitchCompat) rootView.findViewById(R.id.sw_hometown));
        switches[4] = ((SwitchCompat) rootView.findViewById(R.id.sw_constellation));
        switches[5] = (SwitchCompat) rootView.findViewById(R.id.sw_shield_contact);
        Spannable spannable = new SpannableString("你当前还是单身状态，快去和你的Ta相遇吧！");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 5, 7, Spanned.SPAN_POINT_MARK);
        ((TextView) rootView.findViewById(R.id.tv_single_warn)).setText(spannable);
    }

    private void initListener() {
        tvRandomMatch.setOnClickListener(this);
        btnHeightPicker.setOnClickListener(this);
        btnAgePicker.setOnClickListener(this);
        btnSchoolPicker.setOnClickListener(this);
        btnHometownPicker.setOnClickListener(this);
        btnConstellationPicker.setOnClickListener(this);
        btnStartConditionMatch.setOnClickListener(this);
        btnStartConditionMatch.setClickable(false);
        switches[5].setChecked(isContactShield);
        for (SwitchCompat s : switches) {
            s.setOnCheckedChangeListener(this);
        }
    }

    private void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("身高")
                    .setSubTitle("(单位:cm)")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHeight.setSelectedItem((String) btnHeightPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHeightPicker.setText(sspHeight.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHeightPicker.show();
        setHeightData();
    }

    private void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("年龄")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspAge.setSelectedItem((String) btnAgePicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnAgePicker.setText(sspAge.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogAgePicker.show();
        setAgeData();
    }

    private void showDialogSchoolPicker() {
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
                                btnSchoolPicker.setText(tmpSchool.subSequence(0, 4) + "…");
                            } else {
                                btnSchoolPicker.setText(tmpSchool);
                            }
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogSchoolPicker.show();
        presenter.loadSchoolData();
    }

    private void showDialogHometownPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("家乡")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHometown.setSelectedItem((String) btnHometownPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHometownPicker.setText(sspHometown.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHometownPicker.show();
        setHometownData();
    }

    private void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspConstellation.setSelectedItem((String) btnConstellationPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnConstellationPicker.setText(sspConstellation.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogConstellationPicker.show();
        setConstellationData();
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

    private void setHometownData() {
        if (dialogHometownPicker != null && sspHometown == null) {
            sspHometown = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_single);
            sspHometown.setData(new ArrayList<>(Arrays.asList("澳门", "安徽", "北京", "重庆", "福建",
                    "甘肃", "广东", "广西", "贵州", "海南", "黑龙江", "河北", "河南", "湖北", "湖南", "吉林",
                    "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川",
                    "台湾", "天津", "西藏", "新疆", "香港", "云南", "浙江")));
            sspHometown.setSelectedPosition(24);
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
                btnSchoolPicker.setText(sspSchool.getSelectedItem().subSequence(0, 4) + "…");
            }
        }
    }

    @Override
    public void toastNetworkError() {
        Toast.makeText(getContext(), "网络错误(＞д＜)", Toast.LENGTH_SHORT).show();
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
            case R.id.sw_hometown:
                updateSwitchesStatus(isChecked, 3);
                break;
            case R.id.sw_constellation:
                updateSwitchesStatus(isChecked, 4);
                break;
            case R.id.sw_shield_contact:
                isContactShield = isChecked;
                break;
        }
    }

    public void updateSwitchesStatus(boolean isChecked, int index) {
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
        btnStartConditionMatch.setClickable(sum != 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_random_match:
                sum = 0;
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container_love, RandomMatchFragment.newInstance()).commit();
                break;
            case R.id.btn_height_picker:
                showDialogHeightPicker();
                break;
            case R.id.btn_age_picker:
                showDialogAgePicker();
                break;
            case R.id.btn_school_picker:
                showDialogSchoolPicker();
                break;
            case R.id.btn_hometown_picker:
                showDialogHometownPicker();
                break;
            case R.id.btn_constellation_picker:
                showDialogConstellationPicker();
                break;
            case R.id.btn_start_condition_match:
                sum = 0;
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container_love, MatchResultFragment.newInstance()).commit();
                break;
        }
    }

}