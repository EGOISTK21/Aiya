package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PersonalDataActivity extends AppCompatActivity implements View.OnClickListener {


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

    }


    @OnClick({R.id.tv_cancel, R.id.tv_save, R.id.rl_person_photo, R.id.rl_birth, R.id.rl_school, R.id.rl_major, R.id.rl_sign_name, R.id.rl_height, R.id.rl_home_town, R.id.rl_hobby})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                Intent intent = new Intent(PersonalDataActivity.this,MainActivity.class);
                intent.putExtra("Flag","Me");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_save:
                //Todo 将个人资料保存sharepreference  并上传到后台
                break;
            case R.id.rl_person_photo:
                choosePhoto();
                break;
            case R.id.rl_birth:
                break;
            case R.id.rl_school:
                break;
            case R.id.rl_major:
                break;
            case R.id.rl_sign_name:
                break;
            case R.id.rl_height:
                break;
            case R.id.rl_home_town:
                break;
            case R.id.rl_hobby:
                break;
        }
    }

    private void choosePhoto() {
    }
}
