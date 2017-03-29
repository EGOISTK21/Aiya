package xyz.egoistk.aiya.love.unmatched;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.egoistk.aiya.R;
import xyz.egoistk.aiya.base.ArcDialog;
import xyz.egoistk.aiya.base.LazyFragment;
import xyz.egoistk.aiya.base.StringScrollPicker;
import xyz.egoistk.aiya.love.unmatched.presenter.UnmatchedPresenter;
import xyz.egoistk.aiya.love.unmatched.view.UnmatchedContract;

/**
 * Created by EGOISTK on 2017/3/16.
 */

public class UnmatchedFragment extends LazyFragment implements UnmatchedContract.View,
        CompoundButton.OnCheckedChangeListener {

    private int sum;
    private int[] prices;
    private String tmpSchool;
    private UnmatchedContract.Presenter presenter;
    private Switch[] switches;
    private android.view.View rootView;
    private Button btnHeight, btnAge, btnSchoolMajor, btnHometown, btnConstellation;
    private ArcDialog dialogHeightPicker, dialogAgePicker, dialogSchoolMajorPicker,
            dialogHometownPicker, dialogConstellationPicker;
    private StringScrollPicker sspHeight, sspAge, sspSchoolMajor, sspHometown, sspConstellation;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
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
        switches = new Switch[6];

        TextView tvLoveMatchAtRandom = (TextView) rootView.findViewById(R.id.tv_love_match_at_random);
        btnHeight = (Button) rootView.findViewById(R.id.btn_love_height);
        btnAge = (Button) rootView.findViewById(R.id.btn_love_age);
        btnSchoolMajor = (Button) rootView.findViewById(R.id.btn_love_school_major);
        btnHometown = (Button) rootView.findViewById(R.id.btn_love_hometown);
        btnConstellation = (Button) rootView.findViewById(R.id.btn_love_constellation);
        Button btnLoveStartMatch = (Button) rootView.findViewById(R.id.btn_love_unmatched_start_match);
        switches[0] = ((Switch) rootView.findViewById(R.id.switch_love_height));
        switches[1] = ((Switch) rootView.findViewById(R.id.switch_love_age));
        switches[2] = ((Switch) rootView.findViewById(R.id.switch_love_school_major));
        switches[3] = ((Switch) rootView.findViewById(R.id.switch_love_hometown));
        switches[4] = ((Switch) rootView.findViewById(R.id.switch_love_constellation));
        switches[5] = (Switch) rootView.findViewById(R.id.switch_love_shield);

        tvLoveMatchAtRandom.setOnClickListener(this);
        btnHeight.setOnClickListener(this);
        btnAge.setOnClickListener(this);
        btnSchoolMajor.setOnClickListener(this);
        btnHometown.setOnClickListener(this);
        btnConstellation.setOnClickListener(this);
        btnLoveStartMatch.setOnClickListener(this);
        for (Switch s : switches) {
            s.setOnCheckedChangeListener(this);
            if (Build.VERSION.SDK_INT < 20) { //发现在API 19的机器上Swich样式很糟糕
                s.setMaxWidth(160);
            }
        }

        Spannable spannable = new SpannableString("你当前还是单身状态，快去和你的Ta相遇吧！");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
                R.color.colorPrimaryDark)), 5, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((TextView) rootView.findViewById(R.id.tv_love_warn_single)).setText(spannable);
    }

    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.tv_love_match_at_random:
                break;
            case R.id.btn_love_height:
                showDialogHeightPicker();
                break;
            case R.id.btn_love_age:
                showDialogAgePicker();
                break;
            case R.id.btn_love_school_major:
                showDialogSchoolMajorPicker();
                break;
            case R.id.btn_love_hometown:
                showDialogHometownPicker();
                break;
            case R.id.btn_love_constellation:
                showDialogConstellationPicker();
                break;
            case R.id.btn_love_unmatched_start_match:
                break;
        }
        super.onClick(v);
    }

    public void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new ArcDialog.Builder(getContext())
                    .setTitle("身高")
                    .setSubTitle("(单位:cm)")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHeight.setSelectedItem((String) btnHeight.getText());
                            dialogHeightPicker.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHeight.setText(sspHeight.getSelectedItem());
                            dialogHeightPicker.dismiss();
                        }
                    }).create();
        }
        dialogHeightPicker.show();
        setHeightData();
    }

    public void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new ArcDialog.Builder(getContext())
                    .setTitle("年龄")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspAge.setSelectedItem((String) btnAge.getText());
                            dialogAgePicker.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnAge.setText(sspAge.getSelectedItem());
                            dialogAgePicker.dismiss();
                        }
                    }).create();
        }
        dialogAgePicker.show();
        setAgeData();
    }

    public void showDialogSchoolMajorPicker() {
        if (dialogSchoolMajorPicker == null) {
            dialogSchoolMajorPicker = new ArcDialog.Builder(getContext())
                    .setTitle("学校")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSchoolMajor.setSelectedItem(tmpSchool);
                            dialogSchoolMajorPicker.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tmpSchool = sspSchoolMajor.getSelectedItem();
                            if (tmpSchool.length() > 4) {
                                btnSchoolMajor.setText(tmpSchool.subSequence(0, 4) + "…");
                            } else {
                                btnSchoolMajor.setText(tmpSchool);
                            }
                            dialogSchoolMajorPicker.dismiss();
                        }
                    }).create();
        }
        dialogSchoolMajorPicker.show();
        presenter.loadSchoolMajorData();
    }

    public void showDialogHometownPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new ArcDialog.Builder(getContext())
                    .setTitle("家乡")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHometown.setSelectedItem((String) btnHometown.getText());
                            dialogHometownPicker.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHometown.setText(sspHometown.getSelectedItem());
                            dialogHometownPicker.dismiss();
                        }
                    }).create();
        }
        dialogHometownPicker.show();
        setHometownData();
    }

    public void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new ArcDialog.Builder(getContext())
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspConstellation.setSelectedItem((String) btnConstellation.getText());
                            dialogConstellationPicker.dismiss();
                        }
                    })
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnConstellation.setText(sspConstellation.getSelectedItem());
                            dialogConstellationPicker.dismiss();
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_love_height:
                updateSwitchesStatus(isChecked, 0);
                break;
            case R.id.switch_love_age:
                updateSwitchesStatus(isChecked, 1);
                break;
            case R.id.switch_love_school_major:
                updateSwitchesStatus(isChecked, 2);
                break;
            case R.id.switch_love_hometown:
                updateSwitchesStatus(isChecked, 3);
                break;
            case R.id.switch_love_constellation:
                updateSwitchesStatus(isChecked, 4);
                break;
            case R.id.switch_love_shield:
                // TODO: 2017/3/28 屏蔽手机通讯录
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