package com.aiyaschool.aiya.message.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aiyaschool.aiya.R;

public class ForgetTwoActivity extends AppCompatActivity {
    EditText passwordEditText;
    EditText againEditText;
    Button confirmButton;
    String data1 = "";
    String data2 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_two);
        data1 = getIntent().getStringExtra("phoneNumber");
        passwordEditText = (EditText) findViewById(R.id.forget_two_activity_password);
        againEditText = (EditText) findViewById(R.id.forget_two_activity_again);
        confirmButton = (Button) findViewById(R.id.forget_two_activity_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(canNext()){
                    goTomain();
                }
            }
        });
    }
    public boolean canNext(){
        //judge it
        return true;
    }

    public void goTomain(){
       //改密码，进主界面
    }
}
