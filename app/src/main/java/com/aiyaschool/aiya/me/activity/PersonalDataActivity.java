package com.aiyaschool.aiya.me.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.FilletDialog;
import com.aiyaschool.aiya.base.StringScrollPicker;
import com.aiyaschool.aiya.me.view.RoundImageView;
import com.aiyaschool.aiya.multi_image_selector.MultiImageSelector;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonalDataActivity extends AppCompatActivity implements View.OnClickListener {


    private FilletDialog dialogDatePicker, dialogSchoolPicker, dialogAgePicker,
            dialogHeightPicker, dialogConstellationPicker, dialogHometownPicker;
    private StringScrollPicker sspSex, sspSchool, sspAge, sspHeight, sspConstellation, sspHometown;
    private StringScrollPicker sspYear, sspMonth, sspDate;

    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    private ArrayList<String> mSelectPath,mHeightList,mYearDataList,mMonthDataList,mDayDataList;


    @InjectView(R.id.tv_date)
    TextView mTvDate;
    @InjectView(R.id.tv_constellation)
    TextView mTvConstellation;
    @InjectView(R.id.tv_height)
    TextView mTvHeight;
    @InjectView(R.id.personal_photo)
    RoundImageView mRvPersonalPhoto;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.tv_save)
    TextView tvSave;
    @InjectView(R.id.rl_person_photo)
    RelativeLayout rlPersonPhoto;
    @InjectView(R.id.rl_birth)
    RelativeLayout rlBirth;
    @InjectView(R.id.rl_school)
    RelativeLayout rlSchool;
    @InjectView(R.id.rl_major)
    RelativeLayout rlMajor;
    @InjectView(R.id.rl_sign_name)
    RelativeLayout rlSignName;
    @InjectView(R.id.rl_height)
    RelativeLayout rlHeight;
    @InjectView(R.id.rl_home_town)
    RelativeLayout rlHomeTown;
    @InjectView(R.id.rl_hobby)
    RelativeLayout rlHobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.inject(this);
        mHeightList = new ArrayList<>();
        for (int i = 150; i < 200; i++) {
            mHeightList.add(String.valueOf(i));
        }
        mYearDataList = new ArrayList<>();
        for (int i = 1985; i < 2001; i++) {
            mYearDataList.add(String.valueOf(i));
        }
        mMonthDataList = new ArrayList<>();
        for (int i = 1; i < 13; i++){
            mMonthDataList.add(String.valueOf(i));
        }
        mDayDataList = new ArrayList<>();
        for (int i = 1; i < 32; i++){
            mDayDataList.add(String.valueOf(i));
        }

    }


    @OnClick({R.id.tv_cancel, R.id.tv_save, R.id.rl_person_photo, R.id.rl_birth, R.id.rl_school, R.id.rl_major, R.id.rl_sign_name, R.id.rl_height, R.id.rl_home_town, R.id.rl_hobby})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                finish();
                break;
            case R.id.tv_save:
                //Todo 将个人资料保存sharepreference  并上传到后台
                break;
            case R.id.rl_person_photo:
                choosePhoto();
                break;
            case R.id.rl_birth:
                showBirthDialogDatePicker();
                break;
            case R.id.rl_school:
                break;
            case R.id.rl_major:
                break;
            case R.id.rl_sign_name:
                break;
            case R.id.rl_height:
                showDialogHeightPicker();
                break;
            case R.id.rl_home_town:

                break;
            case R.id.rl_hobby:
                showDialogConstellationPicker();
                break;
        }
    }

    private void showBirthDialogDatePicker() {
        if (dialogDatePicker == null) {
            dialogDatePicker = new FilletDialog.Builder(this, R.layout.dialog_date_picker)
                    .setTitle("日期")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspYear.setSelectedItem((String) mTvDate.getText().subSequence(0, 4));
                            sspMonth.setSelectedDate((String) mTvDate.getText().subSequence(5, 7));
                            sspDate.setSelectedDate((String) mTvDate.getText().subSequence(8, 10));
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
                            mTvDate.setText(tmp);
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogDatePicker.show();
        setDateData();
    }

    private void setDateData() {
        if (dialogDatePicker != null
                && (sspYear == null || sspMonth == null || sspDate == null)) {
            sspYear = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_year);
            sspMonth = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_month);
            sspDate = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_date);
            sspYear.setData(mYearDataList);
            sspYear.setSelectedPosition(0);
            sspMonth.setData(mMonthDataList);
            sspMonth.setSelectedPosition(0);
            sspDate.setData(mDayDataList);
            sspDate.setSelectedPosition(0);
        }
    }

    private void showDialogConstellationPicker() {
        if (dialogConstellationPicker == null) {
            dialogConstellationPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("星座")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspConstellation.setSelectedItem((String) mTvConstellation.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mTvConstellation.setText(sspConstellation.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogConstellationPicker.show();
        setConstellationData();
    }

    private void setConstellationData() {
        if (dialogConstellationPicker != null && sspConstellation == null) {
            sspConstellation = (StringScrollPicker) dialogConstellationPicker.findViewById(R.id.ssp_single);
            sspConstellation.setData(new ArrayList<>(Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座",
                    "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座")));
            sspConstellation.setSelectedPosition(6);
        }
    }

    private void showDialogHeightPicker() {
        if (dialogHeightPicker == null) {
            dialogHeightPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
                    .setTitle("身高")
                    .setSubTitle("(单位:cm)")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sspHeight.setSelectedItem((String) mTvHeight.getText());
                            dialog.cancel();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mTvHeight.setText(sspHeight.getSelectedItem());
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogHeightPicker.show();
        setHeightData();
    }

    private void setHeightData() {
        if (dialogHeightPicker != null && sspHeight == null) {
            sspHeight = (StringScrollPicker) dialogHeightPicker.findViewById(R.id.ssp_single);
            sspHeight.setData(mHeightList);
            sspHeight.setSelectedPosition(6);
        }
    }

    private void choosePhoto() {

        String choiceMode = "single";
        pickImage(choiceMode);
    }

    private void pickImage(String choiceMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            boolean showCamera = false;
            int maxNum = 9;

            MultiImageSelector selector = MultiImageSelector.create(PersonalDataActivity.this);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            if (choiceMode.equals("single")) {
                selector.single();
            } else {
                selector.multi();
            }
            selector.origin(mSelectPath);
            selector.start(PersonalDataActivity.this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PersonalDataActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String choiceMode = "single";
                pickImage(choiceMode);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                System.out.println(mSelectPath.get(0));
//                Picasso.with(PersonalDataActivity.this)
//                        .load(mSelectPath.get(0))
//                        .placeholder(R.drawable.mis_default_error)
//                        .tag(MultiImageSelectorFragment.TAG)
//                        .resize(100, 100)
//                        .centerCrop()
//                        .into(mRvPersonalPhoto);
                Bitmap bitmap = BitmapFactory.decodeFile(mSelectPath.get(0));
                mRvPersonalPhoto.setImageBitmap(bitmap);

            }
        }
    }

    @OnClick(R.id.tvv_height)
    public void onClick() {
    }
}
