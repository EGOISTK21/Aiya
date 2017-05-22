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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.bean.RegionModel;
import com.aiyaschool.aiya.me.db.RegionDao;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataContract;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataPresenter;
import com.aiyaschool.aiya.me.mvpupdate.UpdateUserDataContract;
import com.aiyaschool.aiya.me.mvpupdate.UpdateUserDataPresenter;
import com.aiyaschool.aiya.me.util.DBCopyUtil;
import com.aiyaschool.aiya.me.view.RoundImageView;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.FilletDialog;
import com.aiyaschool.aiya.widget.StringScrollPicker;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;

public class PersonalDataActivity extends AppCompatActivity implements View.OnClickListener, PersonDataContract.View, UpdateUserDataContract.View {


    private static final String TAG = "PersonalDataActivity";

    private FilletDialog dialogDatePicker, dialogSchoolPicker, dialogAgePicker,
            dialogHeightPicker, dialogConstellationPicker, dialogHometownPicker;
    private StringScrollPicker sspSex, sspSchool, sspAge, sspHeight, sspConstellation;
    private StringScrollPicker sspYear, sspMonth, sspDate;
    private StringScrollPicker sspProvince, sspCity, sspArea;
    private StringScrollPicker sspCharacter;


    private static final int REQUEST_IMAGE = 2;
    private static final int REQUEST_NICKNAME = 100;
    private static final int REQUEST_PROFILE = 101;
    private static final int REQUEST_HOBBY = 102;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    private ArrayList<String> mSelectPath, mHeightList;
    private ArrayList<String> mYearDataList, mMonthDataList, mDayDataList;
    private ArrayList<String> mProvinceList, mCityList, mAreaList;
    private List<RegionModel> mRmProvinceList, mRmCityList, mRmAreaList;

    private String mAge, mCharacter;

    private RegionDao mRegionDao;
    private boolean flag = false;

    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.tvv_birth)
    TextView tvvBirth;
    @BindView(R.id.tvv_school)
    TextView tvvSchool;
    @BindView(R.id.tvv_signname)
    TextView tvvSignname;
    @BindView(R.id.tvv_height)
    TextView tvvHeight;
    @BindView(R.id.tvv_hometown)
    TextView tvvHometown;
    @BindView(R.id.tvv_constellation)
    TextView tvvConstellation;
    @BindView(R.id.tvv_hobby)
    TextView tvvHobby;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_school)
    TextView mTvSchool;
    @BindView(R.id.tv_sign_name)
    TextView mTvSignName;
    @BindView(R.id.tv_hometown)
    TextView mTvHometown;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_constellation)
    TextView mTvConstellation;
    @BindView(R.id.tv_height)
    TextView mTvHeight;
    @BindView(R.id.personal_photo)
    ImageView mRvPersonalPhoto;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.rl_person_photo)
    RelativeLayout rlPersonPhoto;
    @BindView(R.id.rl_birth)
    RelativeLayout rlBirth;
    @BindView(R.id.rl_school)
    RelativeLayout rlSchool;
    @BindView(R.id.rl_sign_name)
    RelativeLayout rlSignName;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;
    @BindView(R.id.rl_home_town)
    RelativeLayout rlHomeTown;
    @BindView(R.id.rl_hobby)
    RelativeLayout rlHobby;
    @BindView(R.id.tv_hobby)
    TextView mTvHobby;
    @BindView(R.id.rl_constellation)
    RelativeLayout rlConstellation;

    private PersonDataContract.Presenter mPresenter;
    private UpdateUserDataContract.Presenter mUpdatePresenter;

    private List<String> mSchools;
    private String mSchool;
    private String mProvince;
    private Boolean mProvinceChange;

    private Map<String, String> map;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);


        ButterKnife.bind(this);
        mHeightList = new ArrayList<>();
        for (int i = 150; i < 200; i++) {
            mHeightList.add(String.valueOf(i));
        }
        mYearDataList = new ArrayList<>();
        for (int i = 1985; i < 2001; i++) {
            mYearDataList.add(String.valueOf(i));
        }
        mMonthDataList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            mMonthDataList.add(String.valueOf(i));
        }
        mDayDataList = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            mDayDataList.add(String.valueOf(i));
        }
        mProvinceList = new ArrayList<>();
        mCityList = new ArrayList<>();
        mAreaList = new ArrayList<>();
        DBCopyUtil.copyDataBaseFromAssets(this, "region.db");
        mRegionDao = new RegionDao(this);
        mRmProvinceList = mRegionDao.loadProvinceList();
        for (RegionModel regionModel : mRmProvinceList) {
            mProvinceList.add(regionModel.getName());
        }

        //从MyApplication 中读取数据

        if (UserUtil.getUser() != null) {
            user = UserUtil.getUser();
            if (!TextUtils.isEmpty(user.getUsername())) {
                mTvName.setText(user.getUsername());
            }
            if (!TextUtils.isEmpty(user.getAge())) {
                mTvDate.setText(user.getAge());
            }

            if (!TextUtils.isEmpty(user.getSchool())) {
                mTvSchool.setText(user.getSchool());
                mSchool = user.getSchool();
            }

            if (!TextUtils.isEmpty(user.getProfile())) {
                mTvSignName.setText(user.getProfile());
            }


            if (!TextUtils.isEmpty(user.getHeight())) {
                mTvHeight.setText(user.getHeight());
            }

            if (!TextUtils.isEmpty(user.getCharacter())) {
                mTvHometown.setText(user.getCharacter());
            }
            if (!TextUtils.isEmpty(user.getHobby())) {
                mTvHobby.setText(user.getHobby());
            }
            if (!TextUtils.isEmpty(user.getConstellation())) {
                mTvConstellation.setText(user.getConstellation());
            }
            if (!TextUtils.isEmpty(user.getAvatar().getNormal().getFace())) {
                Glide.with(PersonalDataActivity.this).load(user.getAvatar().getNormal())
                        .error(R.drawable.guanggao1)
                        .centerCrop()
                        .transform(new GlideCircleTransform(PersonalDataActivity.this))
                        .into(mRvPersonalPhoto);
            }

            System.out.println(user.getUsername());
        }

        mPresenter = new PersonDataPresenter(this);
        mUpdatePresenter = new UpdateUserDataPresenter(this);
        map = new HashMap<>();

//        mPresenter.updateUserHeight("180");
//        mPresenter.getGuestRecord("1","6");
//        mPresenter.getEmotionRecord("1", "1", "3");
        System.out.println("mSchool" + mSchool + "mProvince" + mProvince);
        String demand = "character,height,age,constellation,hobby";
        mPresenter.getMeIndex(demand);
    }


    @OnClick({R.id.ll_name, R.id.tv_cancel, R.id.tv_save, R.id.rl_person_photo, R.id.rl_birth, R.id.rl_school, R.id.rl_sign_name, R.id.rl_height, R.id.rl_home_town, R.id.rl_hobby, R.id.rl_constellation})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_cancel:

                finish();
                break;
            case R.id.tv_save:
                mUpdatePresenter.updateUserData(map);
                Log.d(TAG, "tv_save onClick: age" + user.getAge());
                MyApplication.setUser(user);
                Intent intent1 = new Intent();
                intent1.putExtra("flag", "me");
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.rl_person_photo:
                choosePhoto();
                break;
            case R.id.rl_birth:
                showDialogBirthDatePicker();
                break;
            case R.id.rl_school:
                showDialogSchoolPicker();
                break;
            case R.id.rl_sign_name:
                intent = new Intent(PersonalDataActivity.this, UpdateDataActivity.class);
                String signName = mTvSignName.getText().toString();
                if (TextUtils.isEmpty(signName)) {
                    signName = "我的个性签名";
                }
                intent.putExtra("sign_name", signName);
                startActivityForResult(intent, REQUEST_PROFILE);
                break;
            case R.id.rl_height:
                showDialogHeightPicker();
                break;
            case R.id.rl_home_town:
                showDialogHomeTownPicker();
                break;
            case R.id.rl_constellation:
                showDialogConstellationPicker();
                break;
            case R.id.rl_hobby:
                intent = new Intent(PersonalDataActivity.this, UpdateDataActivity.class);
                String hobby = mTvHobby.getText().toString();
                if (TextUtils.isEmpty(hobby)) {
                    hobby = "我的兴趣爱好";
                }
                intent.putExtra("hobby", hobby);
                startActivityForResult(intent, REQUEST_HOBBY);
                break;
            case R.id.ll_name:
                Log.d(TAG, "onClick: " + mTvName.getText().toString());
                intent = new Intent(PersonalDataActivity.this, UpdateDataActivity.class);
                String nickName = mTvName.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    nickName = "我的昵称";
                }
                intent.putExtra("nick_name", nickName);
                startActivityForResult(intent, REQUEST_NICKNAME);
                break;


        }
    }


    private void showDialogHomeTownPicker() {
        if (dialogHometownPicker == null) {
            dialogHometownPicker = new FilletDialog.Builder(this, R.layout.dialog_single_picker)
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
                            mTvHometown.setText(mCharacter);
                            user.setCharacter(mCharacter);
                            map.put("character", mCharacter);
                            dialog.dismiss();
                        }
                    }).create();
            setCharacterData();
        }
        dialogHometownPicker.show();
    }

    private void setCharacterData() {
        if (sspCharacter == null) {
            sspCharacter = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_single);
            sspCharacter.setData(new ArrayList<>(Arrays.asList("幽默", "温柔", "活跃", "呆萌", "内涵", "安静")));
            sspCharacter.setSelectedPosition(2);
        }
    }

    private void showDialogBirthDatePicker() {
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
                            Log.d(TAG, "onClick: age" + mAge);
                            mTvDate.setText(mAge);
                            user.setAge(mAge);
                            map.put("age", mAge);
                            dialog.dismiss();
                        }
                    }).create();
            setAgeData();
        }
        dialogAgePicker.show();
    }

    private void setAgeData() {
        if (sspAge == null) {
            sspAge = (StringScrollPicker) dialogAgePicker.findViewById(R.id.ssp_single);
            sspAge.setData(new ArrayList<>(Arrays.asList("17", "18", "19", "20", "21", "22", "23",
                    "24", "25", "26", "27", "28")));
            sspAge.setSelectedPosition(3);
        }
    }

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
                            mTvSchool.setText(mSchool);
                            user.setSchool(mSchool);
                            map.put("school", mSchool);
                            dialog.dismiss();
                        }
                    }).create();
        }
        dialogSchoolPicker.show();

        if (mSchools == null || mProvinceChange) {
            mProvinceChange = false;
            if (!TextUtils.isEmpty(mProvince)) {
                mPresenter.loadSchoolData(mProvince);
            } else {
                mPresenter.loadSchoolData("陕西");
            }

        } else {
            setSchoolData(mSchools);
        }
    }

//    private void showDialogHomeTownPicker() {
//        if (dialogHometownPicker == null) {
//            dialogHometownPicker = new FilletDialog.Builder(this, R.layout.dialog_school_picker)
//                    .setTitle("家乡")
//                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dialog.cancel();
//                        }
//                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String tmp = sspProvince.getSelectedItem();
//                            mProvince = tmp.substring(0, tmp.length() - 1);
//                            mProvinceChange = true;
//                            if (sspCity.getData().size() == 0) {
//                                tmp += "0";
//                            } else {
//                                tmp += "–" + sspCity.getSelectedItem();
//                            }
//                            if (sspArea.getData().size() == 0) {
//                                tmp += "";
//                            } else {
//                                tmp += "–" + sspArea.getSelectedItem();
//                            }
//
//                            mTvHometown.setText(tmp);
//                            dialog.dismiss();
//                        }
//                    }).create();
//        }
//        dialogHometownPicker.show();
    //setHomeTownData();
//    }

//    private void setHomeTownData() {
//        if (dialogHometownPicker != null
//                && (sspProvince == null || sspCity == null || sspArea == null)) {
//            sspProvince = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_province);
//            sspCity = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_city);
//            sspArea = (StringScrollPicker) dialogHometownPicker.findViewById(R.id.ssp_area);
//            sspProvince.setData(mProvinceList);
//            sspProvince.setSelectedPosition(0);
//            sspProvince.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
//                @Override
//                public void onSelected(ScrollPickerView scrollPickerView, int position) {
//                    mCityList.clear();
//                    mAreaList.clear();
//                    sspArea.setData(mAreaList);
//                    System.out.println("position  " + position);
//                    System.out.println("mProvinceList.get(position)  " + mProvinceList.get(position));
//                    RegionModel rmProvince = mRmProvinceList.get(position);
//                    System.out.println("regionModel.getName()  " + rmProvince.getName());
//                    mRmCityList = mRegionDao.loadCityList(rmProvince.getId());
//                    for (RegionModel regionModel : mRmCityList) {
//                        mCityList.add(regionModel.getName());
//                    }
//                    sspCity.setData(mCityList);
//                }
//            });
//
//            sspCity.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
//                @Override
//                public void onSelected(ScrollPickerView scrollPickerView, int position) {
//                    mAreaList.clear();
//                    System.out.println(mCityList.size());
//                    if (mCityList.size() > 0) {
//                        RegionModel rmCity = mRmCityList.get(position);
//                        System.out.println("rmCity.getName()  " + rmCity.getName());
//                        mRmAreaList = mRegionDao.loadCountyList(rmCity.getId());
//                        System.out.println("mRmAreaList.size() " + mRmAreaList.size());
//                        for (RegionModel regionModel : mRmAreaList) {
//                            mAreaList.add(regionModel.getName());
//                        }
//                    }
//                    sspArea.setData(mAreaList);
//                }
//            });
//        }
//    }


//    private void showDialogBirthDatePicker() {
//        if (dialogDatePicker == null) {
//            dialogDatePicker = new FilletDialog.Builder(this, R.layout.dialog_date_picker)
//                    .setTitle("日期")
//                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            sspYear.setSelectedItem((String) mTvDate.getText().subSequence(0, 4));
//                            sspMonth.setSelectedDate((String) mTvDate.getText().subSequence(5, 7));
//                            sspDate.setSelectedDate((String) mTvDate.getText().subSequence(8, 10));
//                            dialog.cancel();
//                        }
//                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String tmp = sspYear.getSelectedItem() + "–";
//                            if (sspMonth.getSelectedItem().length() == 1) {
//                                tmp += "0";
//                            }
//                            tmp += sspMonth.getSelectedItem() + "–";
//                            if (sspDate.getSelectedItem().length() == 1) {
//                                tmp += "0";
//                            }
//                            tmp += sspDate.getSelectedItem();
//                            mTvDate.setText(tmp);
//                            dialog.dismiss();
//                        }
//                    }).create();
//        }
//        dialogDatePicker.show();
//        setDateData();
//    }
//
//    private void setDateData() {
//        if (dialogDatePicker != null
//                && (sspYear == null || sspMonth == null || sspDate == null)) {
//            sspYear = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_year);
//            sspMonth = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_month);
//            sspDate = (StringScrollPicker) dialogDatePicker.findViewById(R.id.ssp_date);
//            sspYear.setData(mYearDataList);
//            sspYear.setSelectedPosition(0);
//            sspMonth.setData(mMonthDataList);
//            sspMonth.setSelectedPosition(0);
//            sspDate.setData(mDayDataList);
//            sspDate.setSelectedPosition(0);
//        }
//    }

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
                            //Todo 将星座保存到本地
                            map.put("constellation", sspConstellation.getSelectedItem());
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
                            String height = sspHeight.getSelectedItem();
                            mTvHeight.setText(height);
                            user.setHeight(height);
                            map.put("height", height);
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
        Log.d(TAG, "onActivityResult: " + requestCode + resultCode);
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
        } else if (requestCode == REQUEST_NICKNAME) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: " + data.getStringExtra("nick_name"));
                if (!TextUtils.isEmpty(data.getStringExtra("nick_name"))) {
                    Log.d(TAG, "onActivityResult: " + data.getStringExtra("nick_name"));
                    mTvName.setText(data.getStringExtra("nick_name"));
                }
            }

        } else if (requestCode == REQUEST_PROFILE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: " + data.getStringExtra("sign_name"));
                if (!TextUtils.isEmpty(data.getStringExtra("sign_name"))) {
                    mTvSignName.setText(data.getStringExtra("sign_name"));
                }
            }

        } else if (requestCode == REQUEST_HOBBY) {
            if (resultCode == RESULT_OK) {
                if (!TextUtils.isEmpty(data.getStringExtra("hobby"))) {
                    mTvHobby.setText(data.getStringExtra("hobby"));
                }
            }

        }
    }


    @Override
    public void setSchoolData(List<String> schools) {
        for (String s : schools) {
            System.out.println(s);
        }

        if (dialogSchoolPicker != null && sspSchool == null) {
            sspSchool = (StringScrollPicker) dialogSchoolPicker.findViewById(R.id.ssp_single);
            if (sspSchool != null) {
                sspSchool.setData(schools);
                if (schools.get(0).equals("")) {
                    mSchools = null;
                    mTvSchool.setText("");
                } else {
                    mSchools = schools;
                    mTvSchool.setText(sspSchool.getSelectedItem());
                }
            }
        }
    }

    @Override
    public void showGetMeIndex(User user) {
//        String demand = "height,age,constellation,hobby";
        User appUsr = UserUtil.getUser();
        appUsr.setHeight(user.getHeight());
        appUsr.setAge(user.getAge());
        appUsr.setConstellation(user.getConstellation());
        appUsr.setHobby(user.getHobby());
        MyApplication.setUser(appUsr);
        if (!TextUtils.isEmpty(user.getAge())) {
            mTvDate.setText(user.getAge());
        }

        if (!TextUtils.isEmpty(user.getHeight())) {
            mTvHeight.setText(user.getHeight());
        }

        if (!TextUtils.isEmpty(user.getCharacter())) {
            mTvHometown.setText(user.getCharacter());
        }
        if (!TextUtils.isEmpty(user.getHobby())) {
            mTvHobby.setText(user.getHobby());
        }
        if (!TextUtils.isEmpty(user.getConstellation())) {
            mTvConstellation.setText(user.getConstellation());
        }
    }


    @Override
    public void showUpdateSuccess() {
        Toast.makeText(PersonalDataActivity.this, "修改个人信息成功", Toast.LENGTH_SHORT).show();
    }
}
