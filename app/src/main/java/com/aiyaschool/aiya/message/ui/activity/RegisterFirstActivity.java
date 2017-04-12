package com.aiyaschool.aiya.message.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.TLSService;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSSmsRegListener;
import tencent.tls.platform.TLSUserInfo;

public class RegisterFirstActivity extends AppCompatActivity {
    EditText phoneNumber;
    EditText verifyCodeEditText;
    Button getVerifyButton;
    Button nextButton;
    final String PREFIX = "获取验证码";
    final String SUFFIX = "秒";
    int countt = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);
        phoneNumber = (EditText) findViewById(R.id.register_first_activity_phone_editText);
        verifyCodeEditText = (EditText) findViewById(R.id.register_first_activity_verify_editText);
        getVerifyButton = (Button) findViewById(R.id.register_first_activity_verify_button);
        nextButton = (Button) findViewById(R.id.register_first_activity_next_button);
        getVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TLSService.getInstance().accountHelper.TLSSmsRegAskCode("86-" + phoneNumber.getText().toString(), new TLSSmsRegListener() {
                    @Override
                    public void OnSmsRegAskCodeSuccess(int i, int i1) {

                    }

                    @Override
                    public void OnSmsRegReaskCodeSuccess(int i, int i1) {

                    }

                    @Override
                    public void OnSmsRegVerifyCodeSuccess() {

                    }

                    @Override
                    public void OnSmsRegCommitSuccess(TLSUserInfo tlsUserInfo) {

                    }

                    @Override
                    public void OnSmsRegFail(TLSErrInfo tlsErrInfo) {

                    }

                    @Override
                    public void OnSmsRegTimeout(TLSErrInfo tlsErrInfo) {

                    }
                });
                getVerifyButton.setClickable(false);
                getVerifyButton.setBackgroundColor(Color.GRAY);
                getVerifyButton.setText(PREFIX+countt+SUFFIX);
                Thread hzjThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this){
                            int count = 59;
                            while(count!=0){
                                try {
                                    wait(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        minusOne();
                                    }
                                });
                                count--;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getVerifyButton.setBackgroundColor(Color.WHITE);
                                    getVerifyButton.setClickable(true);
                                    getVerifyButton.setText("获取验证码");
                                    countt = 60;
                                }
                            });
                        }
                    }
                });
                hzjThread.start();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TLSService.getInstance().accountHelper.TLSSmsRegVerifyCode(verifyCodeEditText.getText().toString(), new TLSSmsRegListener() {
                    @Override
                    public void OnSmsRegAskCodeSuccess(int i, int i1) {

                    }

                    @Override
                    public void OnSmsRegReaskCodeSuccess(int i, int i1) {

                    }

                    @Override
                    public void OnSmsRegVerifyCodeSuccess() {
                        Intent i = new Intent(RegisterFirstActivity.this,RegisterTwoActivity.class);
                        i.putExtra("phoneNumber",phoneNumber.getText().toString());
                        RegisterFirstActivity.this.startActivity(i);
                    }

                    @Override
                    public void OnSmsRegCommitSuccess(TLSUserInfo tlsUserInfo) {

                    }

                    @Override
                    public void OnSmsRegFail(TLSErrInfo tlsErrInfo) {

                    }

                    @Override
                    public void OnSmsRegTimeout(TLSErrInfo tlsErrInfo) {

                    }
                });
            }
        });
    }

    private void minusOne() {
        countt--;
        getVerifyButton.setText(PREFIX+countt+SUFFIX);
    }
}
