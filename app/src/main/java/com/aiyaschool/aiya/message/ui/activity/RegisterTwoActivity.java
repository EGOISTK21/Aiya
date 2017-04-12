package com.aiyaschool.aiya.message.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.RequestCodeUtils;
import com.aiyaschool.aiya.message.utils.ResultCodeUtils;
import com.bumptech.glide.Glide;

public class RegisterTwoActivity extends AppCompatActivity {
    ImageView personalAvatarButton;
    EditText nameEditText;
    EditText sexualEditText;
    EditText schoolEditText;
    EditText birthdayEditText;
    EditText heightEditText;
    EditText hometownEditText;
    Button confirmButton;
    String phoneNumber;
    boolean hasAvatar = false;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        this.context = this;
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        findViews();
        personalAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterTwoActivity.this,PhotoChooseActivity.class);
                i.putExtra("requestCode", RequestCodeUtils.needCrop);
                RegisterTwoActivity.this.startActivityForResult(i, RequestCodeUtils.needPhotoWithoutCrop);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canNext()){
                    Intent i = new Intent(RegisterTwoActivity.this,RegisterThreeActivity.class);
                    i.putExtra("phoneNumber",phoneNumber);
                    RegisterTwoActivity.this.startActivity(i);
                }
            }
        });
    }




    public boolean canNext(){
        return hasAvatar&&isNameValid()&&isSexualValid()&&isSchoolValid()
                &&isBirthdayValid()&&isHeightValid()&&isHometownValid();
    }

    public boolean isNameValid(){
        String aux = nameEditText.toString();
        return true;
    }

    public boolean isSexualValid(){
        String aux = sexualEditText.toString();
        return true;
    }

    public boolean isSchoolValid(){
        String aux = schoolEditText.toString();
        return true;
    }

    public boolean isBirthdayValid(){
        String aux = birthdayEditText.toString();
        return true;
    }

    public boolean isHeightValid(){
        String aux = heightEditText.toString();
        return true;
    }

    public boolean isHometownValid(){
        String aux = hometownEditText.toString();
        return true;
    }

    private void findViews() {
        personalAvatarButton = (ImageView) findViewById(R.id.register_two_avatar);
        nameEditText = (EditText) findViewById(R.id.register_two_activity_name);
        sexualEditText = (EditText) findViewById(R.id.register_two_activity_sexual);
        schoolEditText = (EditText) findViewById(R.id.register_two_activity_school);
        birthdayEditText = (EditText) findViewById(R.id.register_two_activity_birthday);
        heightEditText = (EditText) findViewById(R.id.register_two_activity_height);
        hometownEditText = (EditText) findViewById(R.id.register_two_activity_hometown);
        confirmButton = (Button) findViewById(R.id.register_two_activity_confirm);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode== RequestCodeUtils.needPhotoWithoutCrop){
            if(resultCode== ResultCodeUtils.SUCCESS){
                Glide.with(context).load(data.getStringExtra("result")).into(personalAvatarButton);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
