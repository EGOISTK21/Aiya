package com.aiyaschool.aiya.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.util.MessageContentObserver;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSSmsLoginListener;
import tencent.tls.platform.TLSSmsRegListener;
import tencent.tls.platform.TLSUserInfo;

public class SplashSignActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    final private static String TAG = "SplashSignActivity";

    public static final int CODE_RECEIVED = 1; //收到短信的验证码
    private MessageContentObserver messageContentObserver;    //回调接口
    private boolean isSignUp, codeMode;
    private TLSLoginHelper loginHelper;
    private TLSSmsLoginListener smsLoginListener;
    private TLSAccountHelper accountHelper;
    private TLSSmsRegListener smsRegListener;
    private String mobile, code;
    private EditText etSign;
    private Button btnNext;
    private TextView tvBack, tvTitle, tvSubTitle, tvWarn;
    private TimeCounter timeCounter;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (codeMode && msg.what == CODE_RECEIVED) {
                code = (String) msg.obj;
                etSign.setText(code);
                etSign.setSelection(code.length());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sign);
        StatusBarUtil.init(this);
        initSign();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(messageContentObserver);
    }

    private void initSign() {
        loginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), 1400001533, 792, "1.0");
        loginHelper.setLocalId(2052);
        loginHelper.setTimeOut(5000);
        smsLoginListener = new TLSSmsLoginListener() {
            @Override
            public void OnSmsLoginAskCodeSuccess(int i, int i1) {
                initCodeView();
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
                startActivity(new Intent(SplashSignActivity.this, MainActivity.class));
            }

            @Override
            public void OnSmsLoginFail(TLSErrInfo tlsErrInfo) {
                Log.i(TAG, "OnSmsLoginFail");
                isSignUp = tlsErrInfo.ErrCode == 229;
                if (isSignUp) {
                    accountHelper = TLSAccountHelper.getInstance().init(getApplicationContext(), 1400001533, 792, "1.0");
                    accountHelper.setCountry(86);
                    accountHelper.setLocalId(2052);
                    accountHelper.setTimeOut(5000);
                    smsRegListener = new TLSSmsRegListener() {
                        @Override
                        public void OnSmsRegAskCodeSuccess(int i, int i1) {
                            initCodeView();
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
                            startActivity(new Intent(SplashSignActivity.this, FormActivity.class));
                        }

                        @Override
                        public void OnSmsRegFail(TLSErrInfo tlsErrInfo) {
                            Log.i(TAG, "OnSmsRegFail");
                        }

                        @Override
                        public void OnSmsRegTimeout(TLSErrInfo tlsErrInfo) {
                            Log.i(TAG, "OnSmsRegTimeout");
                        }
                    };
                    accountHelper.TLSSmsRegAskCode(SignUtil.formatPhoneNumber(mobile), smsRegListener);
                } else {
                    Toast.makeText(SplashSignActivity.this, "网络不通畅，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnSmsLoginTimeout(TLSErrInfo tlsErrInfo) {
                Log.i(TAG, "OnSmsLoginTimeout");
            }
        };
        messageContentObserver = new MessageContentObserver(SplashSignActivity.this, handler);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, messageContentObserver);
    }

    private void initView() {
        isSignUp = false;
        mobile = "";
//        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
//            mobile = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number().substring(3, 14);
//        } else {
//            EasyPermissions.requestPermissions(this,"我需要这个权限来自动填充手机号", 1, Manifest.permission.READ_PHONE_STATE);
//        }
        code = "";
        etSign = (EditText) findViewById(R.id.et_sign);
        btnNext = (Button) findViewById(R.id.btn_next);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMobileView();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvSubTitle = (TextView) findViewById(R.id.tv_sub_title);
        tvWarn = (TextView) findViewById(R.id.tv_warn);
        Spannable spannable = new SpannableString("注册即表示同意《爱呀用户协议》");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SplashSignActivity.this,
                R.color.colorPrimaryDark)), 7, 15, Spanned.SPAN_POINT_MARK);
        tvWarn.setText(spannable);
        initMobileView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {
        mobile = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number().substring(3, 14);
        etSign.setText(mobile);
        etSign.setSelection(mobile.length());
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        mobile = "";
    }

    private void initCodeViewButtonNext() {
        btnNext.setText("下一步");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = String.valueOf(etSign.getText());
                if (!SignUtil.isValidCode(code)) {
                    Toast.makeText(SplashSignActivity.this, "请输入4位验证码", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isSignUp) {
                        loginHelper.TLSSmsLoginVerifyCode(code, smsLoginListener);
                        loginHelper.TLSSmsLogin(SignUtil.formatPhoneNumber(mobile), smsLoginListener);
                    } else {
                        accountHelper.TLSSmsRegVerifyCode(code, smsRegListener);
                        accountHelper.TLSSmsRegCommit(smsRegListener);
                        loginHelper.TLSSmsLoginAskCode(SignUtil.formatPhoneNumber(mobile), smsLoginListener);
                        loginHelper.TLSSmsLoginVerifyCode(code, smsLoginListener);
                        loginHelper.TLSSmsLogin(SignUtil.formatPhoneNumber(mobile), smsLoginListener);
                    }
                }
            }
        });
    }

    private void initMobileView() {
        codeMode = false;
        tvBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("请输入手机号");
        tvSubTitle.setVisibility(View.INVISIBLE);
        if (timeCounter != null) timeCounter.cancel();
        etSign.setText(mobile);
        etSign.setSelection(mobile.length());
        btnNext.setText("下一步");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = String.valueOf(etSign.getText());
                if (!SignUtil.isValidPhoneNumber(mobile)) {
                    Toast.makeText(SplashSignActivity.this, "请输入有效的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    loginHelper.TLSSmsLoginAskCode(SignUtil.formatPhoneNumber(mobile), smsLoginListener);
                }
            }
        });
        tvWarn.setVisibility(View.INVISIBLE);
    }

    private void initCodeView() {
        codeMode = true;
        tvBack.setVisibility(View.VISIBLE);
        tvTitle.setText("请输入验证码");
        timeCounter = new TimeCounter(60000, 1000);
        timeCounter.start();
        etSign.setText(code);
        etSign.setSelection(code.length());
        initCodeViewButtonNext();
        tvWarn.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (codeMode) {
            initMobileView();
        } else {
            super.onBackPressed();
        }
    }

    private class TimeCounter extends CountDownTimer {

        TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            tvSubTitle.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Spannable spannable = new SpannableString("验证码已发送至"
                    + mobile + "，" + String.format("%2d", millisUntilFinished / 1000)
                    + "s后可再次发送");
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SplashSignActivity.this,
                    R.color.colorPrimaryDark)), 7, 18, Spanned.SPAN_POINT_MARK);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SplashSignActivity.this,
                    R.color.colorPrimaryDark)), 19, 22, Spanned.SPAN_POINT_MARK);
            tvSubTitle.setText(spannable);
        }

        @Override
        public void onFinish() {
            tvSubTitle.setVisibility(View.INVISIBLE);
            btnNext.setText("重新获取");
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSignUp) {
                        loginHelper.TLSSmsLoginReaskCode(SignUtil.formatPhoneNumber(mobile), smsLoginListener);
                    } else {
                        accountHelper.TLSSmsRegAskCode(SignUtil.formatPhoneNumber(mobile), smsRegListener);
                    }
                    timeCounter.cancel();
                    timeCounter = new TimeCounter(60000, 1000);
                    timeCounter.start();
                    initCodeViewButtonNext();
                }
            });
        }
    }
}
