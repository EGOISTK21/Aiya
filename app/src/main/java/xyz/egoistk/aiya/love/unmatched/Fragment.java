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
import xyz.egoistk.aiya.base.LazyFragment;
import xyz.egoistk.aiya.base.StringScrollPicker;
import xyz.egoistk.aiya.love.unmatched.presenter.UnmatchedPresenter;
import xyz.egoistk.aiya.love.unmatched.view.UnmatchedContract;

/**
 * Created by EGOISTK on 2017/3/16.
 */

public class Fragment extends LazyFragment implements UnmatchedContract.View, CompoundButton.OnCheckedChangeListener {

    private int sum;
    private int[] prices;
    private UnmatchedContract.Presenter presenter;
    private Switch[] switches;
    private android.view.View rootView;
    private Button btnHeight, btnAge, btnSchoolMajor, btnHometown, btnConstellation;
    private android.support.v7.app.AlertDialog dialogHeightPicker, dialogAgePicker,
            dialogSchoolMajorPicker, dialogHometownPicker, dialogConstellationPicker;
    private StringScrollPicker sspHeight, sspAge, sspSchoolMajor, sspHometown, sspConstellation;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        switches = new Switch[5];

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
            dialogHeightPicker = new android.support.v7.app.AlertDialog.Builder(getContext())
                    .setTitle("身高")
                    .setView(LayoutInflater.from(getContext()).inflate(
                            R.layout.dialog_single_picker, null))
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnHeight.setText(sspHeight.getSelectedItem());
                        }
                    })
                    .show();
        } else {
            dialogHeightPicker.show();
        }
        setHeightData();
    }

    public void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new android.support.v7.app.AlertDialog.Builder(getContext())
                    .setTitle("年龄")
                    .setView(LayoutInflater.from(getContext()).inflate(
                            R.layout.dialog_single_picker, null))
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnAge.setText(sspAge.getSelectedItem());
                        }
                    })
                    .show();
        } else {
            dialogAgePicker.show();
        }
        setAgeData();
    }

    public void showDialogSchoolMajorPicker() {
        if (dialogSchoolMajorPicker == null) {
            dialogSchoolMajorPicker = new android.support.v7.app.AlertDialog.Builder(getContext())
                    .setTitle("学校专业")
                    .setView(LayoutInflater.from(getContext()).inflate(
                            R.layout.dialog_single_picker, null))
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btnSchoolMajor.setText(sspSchoolMajor.getSelectedItem());
                        }
                    })
                    .show();
        } else {
            dialogSchoolMajorPicker.show();
        }
        presenter.getSchoolMajorData();
    }

    public void showDialogHometownPicker() {
    }

    public void showDialogConstellationPicker() {
    }

    public void setHeightData() {
        if (dialogHeightPicker != null && sspHeight == null) {
            sspHeight = (StringScrollPicker) dialogHeightPicker.findViewById(R.id.ssp_single);
            sspHeight.setData(new ArrayList<>(Arrays.asList("150以下", "150–154", "155–159",
                    "160–164", "165–169", "170–174", "175–179", "180–184", "185–189", "190及以上")));
        }
    }

    public void setAgeData() {
        if (dialogAgePicker != null && sspAge == null) {
            sspAge = (StringScrollPicker) dialogAgePicker.findViewById(R.id.ssp_single);
            sspAge.setData(new ArrayList<>(Arrays.asList("17", "18", "19", "20", "21", "22", "23",
                    "24", "25", "26", "27", "28")));
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