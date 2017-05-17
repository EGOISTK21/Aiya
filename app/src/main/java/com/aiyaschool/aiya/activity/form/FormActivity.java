package com.aiyaschool.aiya.activity.form;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.util.SchoolDBHelper;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.widget.FilletDialog;
import com.aiyaschool.aiya.widget.ScrollPickerView;
import com.aiyaschool.aiya.widget.StringScrollPicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 表单View实现类，仅在用户注册成功时出现一次
 * Created by EGOISTK21 on 2017/4/16.
 */

public class FormActivity extends BaseActivity implements FormContract.View {

    private static final String TAG = "FormActivity";
    private ProgressDialog mPD;
    private FormContract.Presenter mPresenter;
    private InputMethodManager mInputMethodManager;
    private Bitmap mAvatar;
    private static int mProvince;
    private static int[] mSchoolNo;
    private String mUsername, mSex, mSchool, mAge, mHeight, mConstellation, mCharacter, mHobby;
    private FilletDialog dialogSexPicker, dialogSchoolPicker, dialogAgePicker,
            dialogHeightPicker, dialogConstellationPicker, dialogHometownPicker;
    private StringScrollPicker sspSex, sspProvince, sspSchool, sspAge, sspHeight, sspConstellation, sspHometown;
    @BindView(R.id.ibn_avatar)
    ImageButton ibnAvatar;
    @BindView(R.id.tv_sex_picker)
    TextView tvSexPicker;
    @BindView(R.id.tv_school_picker)
    TextView tvSchoolPicker;
    @BindView(R.id.tv_age_picker)
    TextView tvAgePicker;
    @BindView(R.id.tv_height_picker)
    TextView tvHeightPicker;
    @BindView(R.id.tv_constellation_picker)
    TextView tvConstellationPicker;
    @BindView(R.id.tv_character_picker)
    TextView tvCharacterPicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_form;
    }

    @Override
    protected void initView() {
        mProvince = 0;
        mSchoolNo = new int[31];
        mPresenter = new FormPresenter(this);
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        SchoolDBHelper.closeDB();
        mPresenter.detach();
        super.onDestroy();
    }

    @OnClick(value = R.id.ibn_avatar)
    void setAvatar() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @OnTextChanged(value = R.id.et_username)
    void setUsername(CharSequence username) {
        mUsername = String.valueOf(username);
    }

    @OnClick(value = R.id.tv_sex_picker)
    void showDialogSexPicker() {
        if (dialogSexPicker == null) {
            dialogSexPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("性别")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspSex.setSelectedItem(mSex);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mSex = sspSex.getSelectedItem();
                            tvSexPicker.setText(mSex);
                            dialog.dismiss();
                        }
                    }).create();
            setSexData();
        }
        dialogSexPicker.show();
    }

    @OnClick(value = R.id.tv_school_picker)
    void showDialogSchoolPicker() {
        if (dialogSchoolPicker == null) {
            dialogSchoolPicker = new FilletDialog.Builder(this, R.layout.dialog_school_picker)
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
                            tvSchoolPicker.setText(mSchool);
                            dialog.dismiss();
                        }
                    }).create();
            setProvinceData();
        }
        dialogSchoolPicker.show();
    }

    @OnClick(value = R.id.tv_age_picker)
    void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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
                            dialog.dismiss();
                        }
                    }).create();
            setAgeData();
        }
        dialogAgePicker.show();
    }

    @OnClick(value = R.id.tv_height_picker)
    void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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
                            dialog.dismiss();
                        }
                    }).create();
            setHeightData();
        }
        dialogHeightPicker.show();
    }

    @OnClick(value = R.id.tv_constellation_picker)
    void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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
                            dialog.dismiss();
                        }
                    }).create();
            setConstellationData();
        }
        dialogConstellationPicker.show();
    }

    @OnClick(value = R.id.tv_character_picker)
    void showDialogCharacterPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("家乡")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHometown.setSelectedItem(mCharacter);
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mCharacter = sspHometown.getSelectedItem();
                            tvCharacterPicker.setText(mCharacter);
                            dialog.dismiss();
                        }
                    }).create();
            setCharacterData();
        }
        dialogHometownPicker.show();
    }

    @OnTextChanged(value = R.id.et_hobby)
    void setHobby(CharSequence hobby) {
        mHobby = String.valueOf(hobby);
    }

    @OnClick(value = R.id.btn_start)
    void start() {
        if (null != this.getCurrentFocus()) {
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        if (!SignUtil.isValidUsername(mUsername)) {
            ToastUtil.show("请输入合法用户名");
        } else if (mSex != null && mSchool != null && mAge != null) {
            String mGender = mSex.equals("男") ? "1" : "2";
            mPresenter.firstInit(SignUtil.getLoginToken(), SignUtil.getPhone(),
                    mUsername, mGender, mSchool, mAge, mHeight, mConstellation, mCharacter, mHobby, null);
        } else {
            ToastUtil.show("请至少完善必填信息");
        }
    }

    private void setSexData() {
        if (sspSex == null) {
            sspSex = (StringScrollPicker) dialogSexPicker.findViewById(R.id.ssp_single);
            sspSex.setData(new ArrayList<>(Arrays.asList("男", "女")));
            sspSex.setSelectedPosition(0);
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
    public void setSchoolData(List<String> schools) {
        sspSchool.setData(schools);
        sspSchool.setSelectedPosition(mSchoolNo[mProvince]);
        sspSchool.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                mSchoolNo[mProvince] = position;
            }
        });
    }

    private void setAgeData() {
        if (sspAge == null) {
            sspAge = (StringScrollPicker) dialogAgePicker.findViewById(R.id.ssp_single);
            sspAge.setData(new ArrayList<>(Arrays.asList("17", "18", "19", "20", "21", "22", "23",
                    "24", "25", "26", "27", "28")));
            sspAge.setSelectedPosition(3);
        }
    }

    private void setHeightData() {
        if (sspHeight == null) {
            sspHeight = (StringScrollPicker) dialogHeightPicker.findViewById(R.id.ssp_single);
            sspHeight.setData(new ArrayList<>(Arrays.asList("140", "141", "142", "143", "144", "145",
                    "146", "147", "148", "149", "150", "151", "152", "153", "154", "155", "156", "157",
                    "158", "159", "160", "161", "162", "163", "164", "165", "166", "167", "168", "169",
                    "170", "171", "172", "173", "174", "175", "176", "177", "178", "179", "180", "181",
                    "182", "183", "184", "185", "186", "187", "188", "189", "190", "191", "192", "193",
                    "194", "195", "196", "197", "198", "199", "200", "201", "202", "203", "204", "205",
                    "206", "207", "208", "209", "210")));
            sspHeight.setSelectedPosition(30);
        }
    }

    private void setConstellationData() {
        if (sspConstellation == null) {
            sspConstellation = (StringScrollPicker) dialogConstellationPicker.findViewById(R.id.ssp_single);
            sspConstellation.setData(new ArrayList<>(Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座",
                    "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座")));
            sspConstellation.setSelectedPosition(6);
        }
    }

    private void setCharacterData() {
        if (sspHometown == null) {
            sspHometown = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_single);
            sspHometown.setData(new ArrayList<>(Arrays.asList("幽默", "温柔", "活跃", "呆萌", "内涵", "安静")));
            sspHometown.setSelectedPosition(2);
        }
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.getCurrentFocus() != null) {
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
