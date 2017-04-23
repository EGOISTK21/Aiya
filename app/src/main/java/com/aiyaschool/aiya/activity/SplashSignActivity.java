package com.aiyaschool.aiya.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by EGOISTK21 on 2017/4/15.
 */

public class SplashSignActivity extends AppCompatActivity {

    final private static String TAG = "SplashSignActivity";

    public static final int CODE_RECEIVED = 1; //收到短信的验证码
    public static final int SDK_COMPLETE = 2; //mob调用成功
    private MessageContentObserver messageContentObserver;    //回调接口
    private boolean codeMode;
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
            } else if (msg.what == SDK_COMPLETE) {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Log.i(TAG, "handleMessage: " + event);
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        startActivity(new Intent(SplashSignActivity.this, MainActivity.class));
                        finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        initCodeView();
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
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
        SMSSDK.unregisterAllEventHandler();
        getContentResolver().unregisterContentObserver(messageContentObserver);
    }

    private void initSign() {
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = Message.obtain();
                msg.what = SDK_COMPLETE;
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });
        messageContentObserver = new MessageContentObserver(SplashSignActivity.this, handler);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, messageContentObserver);
    }

    private void initView() {
        mobile = "";
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

    private void initCodeViewButtonNext() {
        btnNext.setText("下一步");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = String.valueOf(etSign.getText());
                if (!SignUtil.isValidCode(code)) {
                    Toast.makeText(SplashSignActivity.this, "请输入4位验证码", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.submitVerificationCode("86", mobile, code);
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
                    SMSSDK.getVerificationCode("86", mobile);
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
                    SMSSDK.getVerificationCode("86", mobile);
                    timeCounter.cancel();
                    timeCounter = new TimeCounter(60000, 1000);
                    timeCounter.start();
                    initCodeViewButtonNext();
                }
            });
        }
    }
}
