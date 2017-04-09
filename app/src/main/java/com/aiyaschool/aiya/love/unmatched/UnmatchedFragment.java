package com.aiyaschool.aiya.love.unmatched;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.FilletDialog;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.base.StringScrollPicker;
import com.aiyaschool.aiya.love.unmatched.presenter.UnmatchedPresenter;
import com.aiyaschool.aiya.love.unmatched.view.UnmatchedContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by EGOISTK21 on 2017/3/16.
 */

public class UnmatchedFragment extends LazyFragment implements UnmatchedContract.View,
        CompoundButton.OnCheckedChangeListener {

    private int sum;
    private int[] prices;
    private String tmpSchool;
    private static boolean isContactShield = false;
    private UnmatchedContract.Presenter presenter;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private View rootView;
    private SwitchCompat[] switches;
    private Button btnHeight, btnAge, btnSchoolMajor, btnHometown, btnConstellation;
    private FilletDialog dialogHeightPicker, dialogAgePicker, dialogSchoolMajorPicker,
            dialogHometownPicker, dialogConstellationPicker;
    private StringScrollPicker sspHeight, sspAge, sspSchoolMajor, sspHometown, sspConstellation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_unmatched, container, false);
        initView();
        presenter = new UnmatchedPresenter(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachLoveUnmatchedView();
    }

    private void initView() {
        sum = 0;
        prices = new int[]{5, 4, 2, 2, 1, 0};
        switches = new SwitchCompat[6];

        fm = getFragmentManager();
        ft = fm.beginTransaction();
        TextView tvLoveMatchAtRandom = (TextView) rootView.findViewById(R.id.tv_random_match);
        btnHeight = (Button) rootView.findViewById(R.id.btn_height_picker);
        btnAge = (Button) rootView.findViewById(R.id.btn_age_picker);
        btnSchoolMajor = (Button) rootView.findViewById(R.id.btn_school_picker);
        btnHometown = (Button) rootView.findViewById(R.id.btn_hometown_picker);
        btnConstellation = (Button) rootView.findViewById(R.id.btn_constellation_picker);
        Button btnLoveStartMatch = (Button) rootView.findViewById(R.id.btn_start_match);
        switches[0] = ((SwitchCompat) rootView.findViewById(R.id.sw_height));
        switches[1] = ((SwitchCompat) rootView.findViewById(R.id.sw_age));
        switches[2] = ((SwitchCompat) rootView.findViewById(R.id.sw_school));
        switches[3] = ((SwitchCompat) rootView.findViewById(R.id.sw_hometown));
        switches[4] = ((SwitchCompat) rootView.findViewById(R.id.sw_constellation));
        switches[5] = (SwitchCompat) rootView.findViewById(R.id.sw_shield_contact);

        tvLoveMatchAtRandom.setOnClickListener(this);
        btnHeight.setOnClickListener(this);
        btnAge.setOnClickListener(this);
        btnSchoolMajor.setOnClickListener(this);
        btnHometown.setOnClickListener(this);
        btnConstellation.setOnClickListener(this);
        btnLoveStartMatch.setOnClickListener(this);
        switches[5].setChecked(isContactShield);
        for (SwitchCompat s : switches) {
            s.setOnCheckedChangeListener(this);
        }

        Spannable spannable = new SpannableString("你当前还是单身状态，快去和你的Ta相遇吧！");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) rootView.findViewById(R.id.tv_single_warn)).setText(spannable);
    }

    public void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("身高")
                    .setSubTitle("(单位:cm)")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHeight.setSelectedItem((String) btnHeight.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHeight.setText(sspHeight.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHeightPicker.show();
        setHeightData();
    }

    public void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("年龄")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspAge.setSelectedItem((String) btnAge.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnAge.setText(sspAge.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogAgePicker.show();
        setAgeData();
    }

    public void showDialogSchoolMajorPicker() {
        if (dialogSchoolMajorPicker == null) {
            dialogSchoolMajorPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("学校")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSchoolMajor.setSelectedItem(tmpSchool);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tmpSchool = sspSchoolMajor.getSelectedItem();
                            if (tmpSchool.length() > 4) {
                                btnSchoolMajor.setText(tmpSchool.subSequence(0, 4) + "…");
                            } else {
                                btnSchoolMajor.setText(tmpSchool);
                            }
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogSchoolMajorPicker.show();
        presenter.loadSchoolMajorData();
    }

    public void showDialogHometownPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("家乡")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHometown.setSelectedItem((String) btnHometown.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHometown.setText(sspHometown.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHometownPicker.show();
        setHometownData();
    }

    public void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(getContext(), R.layout.dialog_single_picker)
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspConstellation.setSelectedItem((String) btnConstellation.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnConstellation.setText(sspConstellation.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogConstellationPicker.show();
        setConstellationData();
    }

    public void setHeightData() {
        if (dialogHeightPicker != null && sspHeight == null) {
            sspHeight = (StringScrollPicker) dialogHeightPicker.findViewById(R.id.ssp_single);
            sspHeight.setData(new ArrayList<>(Arrays.asList("150以下", "150–154", "155–159",
                    "160–164", "165–169", "170–174", "175–179", "180–184", "185–189", "190及以上")));
            sspHeight.setSelectedPosition(6);
        }
    }

    public void setAgeData() {
        if (dialogAgePicker != null && sspAge == null) {
            sspAge = (StringScrollPicker) dialogAgePicker.findViewById(R.id.ssp_single);
            sspAge.setData(new ArrayList<>(Arrays.asList("17", "18", "19", "20", "21", "22", "23",
                    "24", "25", "26", "27", "28")));
            sspAge.setSelectedPosition(3);
        }
    }

    public void setHometownData() {
        if (dialogHometownPicker != null && sspHometown == null) {
            sspHometown = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_single);
            sspHometown.setData(new ArrayList<>(Arrays.asList("澳门", "安徽", "北京", "重庆", "福建",
                    "甘肃", "广东", "广西", "贵州", "海南", "黑龙江", "河北", "河南", "湖北", "湖南", "吉林",
                    "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川",
                    "台湾", "天津", "西藏", "新疆", "香港", "云南", "浙江")));
            sspHometown.setSelectedPosition(24);
        }
    }

    public void setConstellationData() {
        if (dialogConstellationPicker != null && sspConstellation == null) {
            sspConstellation = (StringScrollPicker) dialogConstellationPicker.findViewById(R.id.ssp_single);
            sspConstellation.setData(new ArrayList<>(Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座",
                    "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座")));
            sspConstellation.setSelectedPosition(6);
        }
    }

    @Override
    public void setSchoolMajorData(List<String> data) {
        if (dialogSchoolMajorPicker != null && sspSchoolMajor == null) {
            sspSchoolMajor = (StringScrollPicker) dialogSchoolMajorPicker.findViewById(R.id.ssp_single);
            if (sspSchoolMajor != null) {
                sspSchoolMajor.setData(data);
                btnSchoolMajor.setText(sspSchoolMajor.getSelectedItem().subSequence(0, 4) + "…");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_random_match:
                ft.addToBackStack(null);
                ft.replace(R.id.container_love, new RandomMatchFragment()).commit();
                break;
            case R.id.btn_height_picker:
                showDialogHeightPicker();
                break;
            case R.id.btn_age_picker:
                showDialogAgePicker();
                break;
            case R.id.btn_school_picker:
                showDialogSchoolMajorPicker();
                break;
            case R.id.btn_hometown_picker:
                showDialogHometownPicker();
                break;
            case R.id.btn_constellation_picker:
                showDialogConstellationPicker();
                break;
            case R.id.btn_start_match:
                ft.addToBackStack(null);
                ft.replace(R.id.container_love, new MatchResultFragment()).commit();
                break;
        }
        super.onClick(v);
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
    }

}