package com.aiyaschool.aiya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.util.SignUtil;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSSmsLoginListener;
import tencent.tls.platform.TLSSmsRegListener;
import tencent.tls.platform.TLSUserInfo;

public class SplashSignActivity extends AppCompatActivity {

    final private static String TAG = "SplashSignActivity";

    private boolean codeMode;
    private TLSLoginHelper loginHelper;
    private TLSSmsLoginListener smsLoginListener;
    private TLSAccountHelper accountHelper;
    private String account, code;
    private EditText etAccount;
    private Button btnStart;
    private TextView tvBack, tvTitle, tvSubTitle, tvWarn;
    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_sign);
        initView();
        initListener();
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.et_account);
        btnStart = (Button) findViewById(R.id.btn_start);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSubTitle = (TextView) findViewById(R.id.tv_sub_title);
        tvWarn = (TextView) findViewById(R.id.tv_warn);
        Spannable spannable = new SpannableString("注册即表示同意《爱呀用户协议》");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SplashSignActivity.this,
                R.color.colorPrimaryDark)), 7, 15, Spanned.SPAN_POINT_MARK);
        tvWarn.setText(spannable);
        loginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), 1400001533, 792, "1.0");
        loginHelper.setLocalId(2052);
        loginHelper.setTimeOut(5000);
        smsLoginListener = new TLSSmsLoginListener() {
            @Override
            public void OnSmsLoginAskCodeSuccess(int i, int i1) {
                Log.i(TAG, "OnSmsLoginAskCodeSuccess");
            }

            @Override
            public void OnSmsLoginReaskCodeSuccess(int i, int i1) {
                Log.i(TAG, "OnSmsLoginReaskCodeSuccess");
            }

            @Override
            public void OnSmsLoginVerifyCodeSuccess() {
                Log.i(TAG, "OnSmsLoginVerifyCodeSuccess");
            }

            @Override
            public void OnSmsLoginSuccess(TLSUserInfo tlsUserInfo) {
                Log.i(TAG, "OnSmsLoginSuccess");
            }

            @Override
            public void OnSmsLoginFail(TLSErrInfo tlsErrInfo) {
                accountHelper = TLSAccountHelper.getInstance().init(getApplicationContext(), 1400001533, 792, "1.0");
                accountHelper.setCountry(86);
                accountHelper.setLocalId(2052);
                accountHelper.setTimeOut(5000);
                accountHelper.TLSSmsRegAskCode("86-13152132810", new TLSSmsRegListener() {
                    @Override
                    public void OnSmsRegAskCodeSuccess(int i, int i1) {
                        Log.i(TAG, "OnSmsRegAskCodeSuccess");
                    }

                    @Override
                    public void OnSmsRegReaskCodeSuccess(int i, int i1) {
                        Log.i(TAG, "OnSmsRegReaskCodeSuccess");
                    }

                    @Override
                    public void OnSmsRegVerifyCodeSuccess() {
                        Log.i(TAG, "OnSmsRegVerifyCodeSuccess");
                    }

                    @Override
                    public void OnSmsRegCommitSuccess(TLSUserInfo tlsUserInfo) {
                        Log.i(TAG, "OnSmsRegCommitSuccess");
                    }

                    @Override
                    public void OnSmsRegFail(TLSErrInfo tlsErrInfo) {
                        Log.i(TAG, "OnSmsRegFail");
                    }

                    @Override
                    public void OnSmsRegTimeout(TLSErrInfo tlsErrInfo) {
                        Log.i(TAG, "OnSmsRegTimeout");
                    }
                });
            }

            @Override
            public void OnSmsLoginTimeout(TLSErrInfo tlsErrInfo) {
                Log.i(TAG, "OnSmsLoginTimeout");
            }
        };
    }

    private void initListener() {
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPhoneNumber();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!codeMode) {
                    account = String.valueOf(etAccount.getText());
                    if (!SignUtil.isValidPhoneNumber(account)) {
                        Toast.makeText(SplashSignActivity.this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        loginHelper.TLSSmsLoginAskCode(SignUtil.formatPhoneNumber(account), smsLoginListener);
                        inputCode();
                    }
                } else {
                    startActivity(new Intent(SplashSignActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void inputPhoneNumber() {
        codeMode = false;
        tvBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("请输入手机号");
        tvSubTitle.setVisibility(View.INVISIBLE);
        etAccount.setText(account);
        etAccount.setSelection(account.length());
        tvWarn.setVisibility(View.INVISIBLE);
    }

    private void inputCode() {
        codeMode = true;
        tvBack.setVisibility(View.VISIBLE);
        tvTitle.setText("请输入验证码");
        tvSubTitle.setVisibility(View.VISIBLE);
        Spannable spannable = new SpannableString("验证码已发送至" + account + "，60s后可再次发送");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SplashSignActivity.this,
                R.color.colorPrimaryDark)), 7, 18, Spanned.SPAN_POINT_MARK);
        etAccount.setText("");
        tvWarn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (codeMode) {
            inputPhoneNumber();
        } else {
            super.onBackPressed();
        }
    }
}
