package com.aiyaschool.aiya.activity.form;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.base.FilletDialog;
import com.aiyaschool.aiya.base.StringScrollPicker;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.aiyaschool.aiya.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 表单View实现类，仅在用户注册成功时出现一次
 * Created by EGOISTK21 on 2017/4/16.
 */

public class FormActivity extends BaseActivity implements FormContract.View {

    private static final String TAG = "FormActivity";
    public static final int CHOOSE_PHOTO = 13;
    private ProgressDialog mPD;
    private FormContract.Presenter mPresenter;
    private InputMethodManager mInputMethodManager;
    private Uri imgUri;
    private Bitmap mAvatar;
    private List<String> mSchools;
    private String mUsername, mSex, mSchool, mAge, mHeight, mConstellation, mHometown, mHobby;
    private FilletDialog dialogSexPicker, dialogSchoolPicker, dialogAgePicker,
            dialogHeightPicker, dialogConstellationPicker, dialogHometownPicker;
    private StringScrollPicker sspSex, sspSchool, sspAge, sspHeight, sspConstellation, sspHometown;
    @BindView(R.id.ibn_avatar)
    AppCompatImageButton ibnAvatar;
    @BindView(R.id.tv_sex_picker)
    AppCompatTextView tvSexPicker;
    @BindView(R.id.tv_school_picker)
    AppCompatTextView tvSchoolPicker;
    @BindView(R.id.tv_age_picker)
    AppCompatTextView tvAgePicker;
    @BindView(R.id.tv_height_picker)
    AppCompatTextView tvHeightPicker;
    @BindView(R.id.tv_constellation_picker)
    AppCompatTextView tvConstellationPicker;
    @BindView(R.id.tv_hometown_picker)
    AppCompatTextView tvHometownPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mPresenter = new FormPresenter(this);
        StatusBarUtil.init(this);
        ButterKnife.bind(this);
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @OnClick(value = R.id.ibn_avatar)
    void setAvatar() {
        imgUri = getTmpUri();
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private Uri getTmpUri() {
        String IMAGE_FILE_DIR = Environment.getExternalStorageDirectory() + "/" + "app_name";
        File dir = new File(IMAGE_FILE_DIR);
        File file = new File(IMAGE_FILE_DIR, Long.toString(System.currentTimeMillis()));
        //非常重要！！！如果文件夹不存在必须先手动创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK) {
            mAvatar = data.getParcelableExtra("data");
            ibnAvatar.setImageBitmap(mAvatar);
            Log.i(TAG, "onActivityResult: " + imgUri);
            mPresenter.submitAvatar(new File(String.valueOf(imgUri)));
        }
    }

    @OnTextChanged(value = R.id.et_username)
    void setUsername(CharSequence username) {
        mUsername = String.valueOf(username);
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
        System.out.println(SignUtil.getLoginToken());
        if (!SignUtil.isValidUsername(mUsername)) {
            ToastUtil.show("请输入合法用户名");
        } else if (mSex == null) {

        } else if (mSchool == null) {

        } else if (mAge == null) {

        } else if (mHeight == null) {

        } else if (mConstellation == null) {

        } else if (mHometown == null) {

        } else {
            String mGender = mSex.equals("男") ? "1" : "2";
            mPresenter.firstInit(SignUtil.getLoginToken(), SignUtil.getPhone(),
                    mUsername, mGender, mSchool, mAge, mHeight, mConstellation, mHometown, mHobby, null);
        }
    }

    @OnClick(value = R.id.tv_sex_picker)
    void showDialogSexPicker() {
        if (dialogSexPicker == null) {
            dialogSexPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("性别")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mSex != null) {
                                sspSex.setSelectedItem(mSex);
                            }
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
        }
        dialogSexPicker.show();
        setSexData();
    }

    @OnClick(value = R.id.tv_school_picker)
    void showDialogSchoolPicker() {
        if (dialogSchoolPicker == null) {
            dialogSchoolPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("学校")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mSchool != null) {
                                sspSchool.setSelectedItem(mSchool);
                            }
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
        }
        dialogSchoolPicker.show();
        if (mSchool == null) {
            mPresenter.loadSchoolData();
        } else {
            setSchoolData(mSchools);
        }
    }

    @OnClick(value = R.id.tv_age_picker)
    void showDialogAgePicker() {
        if (dialogAgePicker == null) {
            dialogAgePicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("年龄")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAge != null) {
                                sspAge.setSelectedItem(mAge);
                            }
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
        }
        dialogAgePicker.show();
        setAgeData();
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
                            if (mHeight != null) {
                                sspHeight.setSelectedItem(mHeight);
                            }
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
        }
        dialogHeightPicker.show();
        setHeightData();
    }

    @OnClick(value = R.id.tv_constellation_picker)
    void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mConstellation != null) {
                                sspConstellation.setSelectedItem(mConstellation);
                            }
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
        }
        dialogConstellationPicker.show();
        setConstellationData();
    }

    @OnClick(value = R.id.tv_hometown_picker)
    void showDialogHometownPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("家乡")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mHometown != null) {
                                sspHometown.setSelectedItem(mHometown);
                            }
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mHometown = sspHometown.getSelectedItem();
                            tvHometownPicker.setText(mHometown);
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

    public void setSchoolData(List<String> schools) {
        if (dialogSchoolPicker != null && sspSchool == null) {
            sspSchool = (StringScrollPicker) dialogSchoolPicker.findViewById(R.id.ssp_single);
            if (sspSchool != null) {
                sspSchool.setData(schools);
                if (schools.get(0).equals("")) {
                    mSchools = null;
                    tvSchoolPicker.setText("");
                } else {
                    mSchools = schools;
                    tvSchoolPicker.setText(sspSchool.getSelectedItem().subSequence(0, 4) + "…");
                }
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
        if (null != this.getCurrentFocus()) {
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}