package com.aiyaschool.aiya.love.matched;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.FilletDialog;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.base.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by EGOISTK21 on 2017/4/4.
 */

public class NewMissionFragment extends LazyFragment {

    private View rootView;
    private FragmentManager fm;
    private TextView tvDate;
    private EditText etMission;
//    private TagFlowLayout tflRecommendMisson;
    private Button btnInvite;
    private FilletDialog dialogDatePicker;
    private StringScrollPicker sspYear, sspMonth, sspDate;
    private List<String> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_mission, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        fm = getFragmentManager();
        rootView.findViewById(R.id.tv_back).setOnClickListener(this);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        tvDate.setOnClickListener(this);
        btnInvite = (Button) rootView.findViewById(R.id.btn_invite);
        btnInvite.setOnClickListener(this);
        btnInvite.setClickable(false);
        etMission = (EditText) rootView.findViewById(R.id.et_mission);
        etMission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!android.text.TextUtils.isEmpty(s.toString().trim())) {
                    btnInvite.setClickable(true);
                    btnInvite.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_selector));
                } else {
                    btnInvite.setClickable(false);
                    btnInvite.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_unclickable));
                }
            }
        });
//        tflRecommendMisson = (TagFlowLayout) rootView.findViewById(R.id.tfl_recommend_mission);
        data = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            data.add(String.valueOf(i));
        }
    }

    private void showDialogDatePicker() {
        if (dialogDatePicker == null) {
            dialogDatePicker = new FilletDialog.Builder(getContext(), R.layout.dialog_date_picker)
                    .setTitle("日期")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspYear.setSelectedItem((String) tvDate.getText().subSequence(0, 4));
                            sspMonth.setSelectedDate((String) tvDate.getText().subSequence(5, 7));
                            sspDate.setSelectedDate((String) tvDate.getText().subSequence(8, 10));
                            dialog.cancel();
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tmp = sspYear.getSelectedItem() + "–";
                            if (sspMonth.getSelectedItem().length() == 1) {
                                tmp += "0";
                            }
                            tmp += sspMonth.getSelectedItem() + "–";
                            if (sspDate.getSelectedItem().length() == 1) {
                                tmp += "0";
                            }
                            tmp += sspDate.getSelectedItem();
                            tvDate.setText(tmp);
                            dialog.dismiss();
//                            tflRecommendMisson.setAdapter(new TagAdapter<String>(
//                                    new ArrayList<>(
//                                            Arrays.asList("听一场演唱会", "DIY制作一个共有的小物件", "看一场电影",
//                                                    "一起倒数跨年321", "送上新年祝福", "交换写着祝福的卡片"))) {
//                                @Override
//                                public View getView(FlowLayout flowLayout, int i, String s) {
//                                    TextView tv =(TextView) LayoutInflater.from(getContext())
//                                            .inflate(R.layout.tagflowlayout_recommend_mission, tflRecommendMisson, false);
//                                    tv.setText(s);
//                                    return tv;
//                                }
//                            });
                        }
                    }).create();
        }
        dialogDatePicker.show();
        setDateData();
        chainSsp();
    }

    private void setDateData() {
        if (dialogDatePicker != null
                && (sspYear == null || sspMonth == null || sspDate == null)) {
            sspYear = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_year);
            sspMonth = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_month);
            sspDate = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_date);
            sspYear.setData(new ArrayList<>(Arrays.asList("2017", "2018", "2019", "2020", "2021",
                    "2022", "2023", "2024", "2025", "2026", "2027")));
            sspYear.setSelectedPosition(0);
            sspMonth.setData(data.subList(0, 12));
            sspMonth.setSelectedPosition(0);
            sspDate.setData(data.subList(0, 31));
            sspDate.setSelectedPosition(0);
        }
    }

    private void chainSsp() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                fm.popBackStack();
                break;
            case R.id.tv_date:
                showDialogDatePicker();
                break;
            case R.id.btn_invite:
                break;
        }
    }
}
