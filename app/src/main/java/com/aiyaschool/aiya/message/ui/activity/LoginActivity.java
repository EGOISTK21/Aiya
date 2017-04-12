package com.aiyaschool.aiya.message.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.Constant;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.tencent.TIMUser;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSUserInfo;

public class LoginActivity extends AppCompatActivity {
    EditText userNameEditText,passwordEditText;
    Button loginButton;
    TextView forgetTextView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEditText = (EditText) findViewById(R.id.login_activity_username_edittext);
        passwordEditText = (EditText) findViewById(R.id.login_activity_password_edittext);
        loginButton = (Button) findViewById(R.id.login_activity_login_button);
        forgetTextView = (TextView) findViewById(R.id.login_activity_forget_tv);
        context = this;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIMUser user = new TIMUser();
                user.setAccountType(Constant.ACCOUNT_TYPE+"");
                user.setAppIdAt3rd(Constant.SDK_APPID+"");
                user.setIdentifier(userNameEditText.getText().toString());

                TLSService.loginHelper.TLSPwdLogin(userNameEditText.getText().toString(), "xinxinzm22".getBytes(), new TLSPwdLoginListener() {
                    @Override
                    public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                        Intent i = new Intent(LoginActivity.this,MiddleActivity.class);
                        context.startActivity(i);
                    }

                    @Override
                    public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
                        Toast.makeText(LoginActivity.this, "图片验证码", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
                        Toast.makeText(LoginActivity.this, "需要图片验证码", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                        Toast.makeText(LoginActivity.this, "超时", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        forgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ForgetOneActivity.class);
                startActivity(i);
            }
        });
    }
}
