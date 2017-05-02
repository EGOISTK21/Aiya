package com.aiyaschool.aiya.activity.form;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.FilletDialog;
import com.aiyaschool.aiya.base.StringScrollPicker;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 表单View实现类，仅在用户注册成功时出现一次
 * Created by EGOISTK21 on 2017/4/16.
 */

public class FormActivity extends RxAppCompatActivity
        implements FormContract.View, View.OnClickListener {

    private static final String TAG = "FormActivity";
    private ProgressDialog mPD;
    private FormContract.Presenter mPresenter;
    private InputMethodManager mInputMethodManager;
    private Button btnFinish;
    private EditText etNick, etHobby;
    private TextView tvSexPicker, tvSchoolPicker, tvAgePicker,
            tvHeightPicker, tvConstellationPicker, tvHometownPicker;
    private FilletDialog dialogSexPicker, dialogSchoolPicker, dialogAgePicker,
            dialogHeightPicker, dialogConstellationPicker, dialogHometownPicker;
    private StringScrollPicker sspSex, sspSchool, sspAge, sspHeight, sspConstellation, sspHometown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        StatusBarUtil.init(this);
        initView();
        initListener();
        mPresenter = new FormPresenter(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    private void initView() {
        btnFinish = (Button) findViewById(R.id.btn_finish);
        etNick = (EditText) findViewById(R.id.et_nick);
        etHobby = (EditText) findViewById(R.id.et_hobby);
        tvSexPicker = (TextView) findViewById(R.id.tv_sex_picker);
        tvSchoolPicker = (TextView) findViewById(R.id.tv_school_picker);
        tvAgePicker = (TextView) findViewById(R.id.tv_age_picker);
        tvHeightPicker = (TextView) findViewById(R.id.tv_height_picker);
        tvConstellationPicker = (TextView) findViewById(R.id.tv_constellation_picker);
        tvHometownPicker = (TextView) findViewById(R.id.tv_hometown_picker);
    }

    private void initListener() {
        btnFinish.setOnClickListener(this);
        tvSexPicker.setOnClickListener(this);
        tvSchoolPicker.setOnClickListener(this);
        tvAgePicker.setOnClickListener(this);
        tvHeightPicker.setOnClickListener(this);
        tvConstellationPicker.setOnClickListener(this);
        tvHometownPicker.setOnClickListener(this);
    }

    private void showDialogSexPicker() {
        if (dialogSexPicker == null) {
            dialogSexPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("性别")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSex.setSelectedItem((String) tvSexPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvSexPicker.setText(sspSex.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogSexPicker.show();
        setSexData();
    }

    private void showDialogSchoolPicker() {
        if (dialogSchoolPicker == null) {
            dialogSchoolPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("学校")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSchool.setSelectedItem((String) tvSchoolPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvSchoolPicker.setText(sspSchool.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogSchoolPicker.show();
        //presenter.loadSchoolData();
    }

    private void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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

    private void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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

    private void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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

    private void showDialogHometownPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("家乡")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHometown.setSelectedItem((String) tvHometownPicker.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvHometownPicker.setText(sspHometown.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHometownPicker.show();
        setHometownData();
    }

    private void setSexData() {
        if (dialogSexPicker != null && sspSex == null) {
            sspSex = (StringScrollPicker) dialogSexPicker.findViewById(R.id.ssp_single);
            sspSex.setData(new ArrayList<>(Arrays.asList("男", "女")));
            sspSex.setSelectedPosition(0);
        }
    }

    public void setSchoolData(List<String> data) {
        if (dialogSchoolPicker != null && sspSchool == null) {
            sspSchool = (StringScrollPicker) dialogSchoolPicker.findViewById(R.id.ssp_single);
            if (sspSchool != null) {
                sspSchool.setData(data);
                tvSchoolPicker.setText(sspSchool.getSelectedItem().subSequence(0, 4) + "…");
            }
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

    private void setHeightData() {
        if (dialogHeightPicker != null && sspHeight == null) {
            sspHeight = (StringScrollPicker) dialogHeightPicker.findViewById(R.id.ssp_single);
            sspHeight.setData(new ArrayList<>(Arrays.asList("150以下", "150–154", "155–159",
                    "160–164", "165–169", "170–174", "175–179", "180–184", "185–189", "190及以上")));
            sspHeight.setSelectedPosition(6);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sex_picker:
                showDialogSexPicker();
                break;
            case R.id.tv_school_picker:
                showDialogSchoolPicker();
                break;
            case R.id.tv_age_picker:
                showDialogAgePicker();
                break;
            case R.id.tv_height_picker:
                showDialogHeightPicker();
                break;
            case R.id.tv_constellation_picker:
                showDialogConstellationPicker();
                break;
            case R.id.tv_hometown_picker:
                showDialogHometownPicker();
                break;
            case R.id.btn_finish:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void showPD() {
        Log.i(TAG, "showPD");
        if (mPD == null) {
            mPD = new ProgressDialog(this);
        }
        if (!mPD.isShowing()) {
            mPD.show();
        }
    }

    @Override
    public void dismissPD() {
        Log.i(TAG, "dismissPD");
        if (mPD != null && mPD.isShowing()) {
            mPD.dismiss();
        }
    }

    @Override
    public void startMainView() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
