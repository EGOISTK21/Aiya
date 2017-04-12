package com.aiyaschool.aiya.message.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.TLSService;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSStrAccRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegisterThreeActivity extends AppCompatActivity {
    EditText passwordEditText;
    EditText againEditText;
    Button completeButton;
    String data1 = "";
    String data2 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);
        data1 = getIntent().getStringExtra("phoneNumber");
        passwordEditText = (EditText) findViewById(R.id.register_three_activity_password);
        againEditText = (EditText) findViewById(R.id.register_three_activity_again);
        completeButton = (Button) findViewById(R.id.register_three_activity_complete);
        completeButton.setOnClickListener(new View.OnClickListener() {
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
        TLSService.getInstance().accountHelper.TLSStrAccReg("SH" + data1, data2, new TLSStrAccRegListener() {
            @Override
            public void OnStrAccRegSuccess(TLSUserInfo tlsUserInfo) {
                Toast.makeText(RegisterThreeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnStrAccRegFail(TLSErrInfo tlsErrInfo) {

            }

            @Override
            public void OnStrAccRegTimeout(TLSErrInfo tlsErrInfo) {

            }
        });
        //注册腾讯账号
        //登陆腾讯账号
        //跳转到主界面
    }
}
