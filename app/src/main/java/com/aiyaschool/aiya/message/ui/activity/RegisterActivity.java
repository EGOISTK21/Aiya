package com.aiyaschool.aiya.message.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.TLSService;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSStrAccRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegisterActivity extends AppCompatActivity {
    private TLSService tlsService;
    String telephoneNumber;
    EditText usernameEditText,telephoneEditText,passwordEditText,rePasswordEditText,verifyCodeEditText;
    Button registerButton,verifyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TLSService.accountHelper.TLSStrAccReg(usernameEditText.getText().toString(), "xinxinzm22", new TLSStrAccRegListener() {
                    @Override
                    public void OnStrAccRegSuccess(TLSUserInfo tlsUserInfo) {
                        String xx = "ddd";
                    }

                    @Override
                    public void OnStrAccRegFail(TLSErrInfo tlsErrInfo) {
                        String xx = "ddd";
                    }

                    @Override
                    public void OnStrAccRegTimeout(TLSErrInfo tlsErrInfo) {
                        String xx = "ddd";
                    }
                });
            }
        });
    }

    private void findViews() {
        usernameEditText = (EditText) findViewById(R.id.register_activity_username_edittext);
        telephoneEditText = (EditText) findViewById(R.id.register_activity_telephonenumber_edittext);
        passwordEditText = (EditText) findViewById(R.id.register_activity_password_edittext);
        rePasswordEditText = (EditText) findViewById(R.id.register_activity_repassword_edittext);
        verifyCodeEditText = (EditText) findViewById(R.id.register_activity_verify_edittext);
        registerButton = (Button) findViewById(R.id.register_activity_register_button);
        verifyButton = (Button) findViewById(R.id.register_activity_verify_button);
    }
}
