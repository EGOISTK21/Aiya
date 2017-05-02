package com.aiyaschool.aiya.activity.sign;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.FormActivity;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.util.MessageContentObserver;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.aiyaschool.aiya.util.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by EGOISTK21 on 2017/4/15.
 */

public class SignActivity extends RxAppCompatActivity implements SignContract.View {

    private final static String TAG = "SignActivity";
    public static final int VERIFICATION_RECEIVED = 1; //收到短信的验证码
    private MessageContentObserver messageContentObserver;    //回调接口
    private boolean isVerificationView;
    private String phone, verification;
    private TimeCounter timeCounter;
    private ProgressDialog mPD;
    private SignContract.Presenter mPresenter;
    private InputMethodManager mInputMethodManager;
    @BindView(R.id.tv_back)
    AppCompatTextView tvBack;
    @BindView(R.id.tv_title)
    AppCompatTextView tvTitle;
    @BindView(R.id.tv_sub_title)
    AppCompatTextView tvSubTitle;
    @BindView(R.id.tv_warn)
    AppCompatTextView tvWarn;
    @BindView(R.id.et_sign)
    AppCompatEditText etSign;
    @BindView(R.id.btn_next)
    AppCompatButton btnNext;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isVerificationView && msg.what == VERIFICATION_RECEIVED) {
                verification = (String) msg.obj;
                etSign.setText(verification);
                etSign.setSelection(verification.length());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new SignPresenter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        StatusBarUtil.init(this);
        ButterKnife.bind(this);
        initSmsObserver();
        phone = SignUtil.getPhone();
        initView();
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(messageContentObserver);
        mPresenter.detach();
        super.onDestroy();
    }

    private void initSmsObserver() {
        messageContentObserver = new MessageContentObserver(SignActivity.this, handler);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, messageContentObserver);
    }

    private void initView() {
        Spannable spannable = new SpannableString("注册即表示同意《爱呀用户协议》");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignActivity.this,
                R.color.colorPrimaryDark)), 7, 15, Spanned.SPAN_POINT_MARK);
        tvWarn.setText(spannable);
        initPhoneView();
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        initPhoneView();
    }

    @OnTextChanged(value = R.id.et_sign)
    void etSignChanged(CharSequence sign) {
        if (!isVerificationView) {
            phone = String.valueOf(sign);
        } else {
            verification = String.valueOf(sign);
        }
    }

    private void initPhoneViewBtnNext() {
        btnNext.setText("获取验证码");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SignUtil.isValidPhone(phone)) {
//                    SMSSDK.getVerificationCode("86", phone);
                    initCodeView();
                } else {
                    ToastUtil.show("请输入有效的手机号");
                }
            }
        });
    }

    private void initVerificationViewBtnNext() {
        btnNext.setText("启动");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != getCurrentFocus()) {
                    mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                if (SignUtil.isValidVerification(verification)) {
                    mPresenter.sign(phone, verification);
                } else {
                    ToastUtil.show("请输入有效的验证码");
                }
            }
        });
    }

    private void initPhoneView() {
        isVerificationView = false;
        tvBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("请输入手机号");
        etSign.setText(phone);
        etSign.setSelection(phone.length());
        initPhoneViewBtnNext();
        tvWarn.setVisibility(View.INVISIBLE);
    }

    private void initCodeView() {
        isVerificationView = true;
        tvBack.setVisibility(View.VISIBLE);
        tvTitle.setText("请输入验证码");
        timeCounter = new TimeCounter(60000, 1000);
        timeCounter.start();
        etSign.setText(verification);
        etSign.setSelection(verification.length());
        initVerificationViewBtnNext();
        tvWarn.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != getCurrentFocus()) {
            return mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (isVerificationView) {
            initPhoneView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showPD() {
        Log.i(TAG, "showPD");
        if (mPD == null) {
            mPD = new ProgressDialog(this);
        }
        if (!mPD.isShowing()) {
            mPD.show();
        }
    }

    @Override
    public void dismissPD() {
        Log.i(TAG, "dismissPD");
        if (mPD != null && mPD.isShowing()) {
            mPD.dismiss();
        }
    }

    @Override
    public void startFormView() {
        startActivity(new Intent(this, FormActivity.class));
        finish();
    }

    @Override
    public void startMainView() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private class TimeCounter extends CountDownTimer {

        TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            tvSubTitle.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            Spannable spannable = new SpannableString(String.format("验证码已发送至%s,%2ds后可再次获取",phone, millisUntilFinished / 1000));
            Spannable spannable = new SpannableString("验证码已发送至" + phone + "，"
                    + String.format("%2d", millisUntilFinished / 1000) + "s后可再次获取");
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignActivity.this,
                    R.color.colorPrimaryDark)), 7, 18, Spanned.SPAN_POINT_MARK);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignActivity.this,
                    R.color.colorPrimaryDark)), 19, 22, Spanned.SPAN_POINT_MARK);
            tvSubTitle.setText(spannable);
            btnNext.setClickable(!btnNext.getText().equals("获取验证码"));
        }


        @Override
        public void onFinish() {
            tvSubTitle.setVisibility(View.INVISIBLE);
            if (isVerificationView) {
                btnNext.setText("重新获取");
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != getCurrentFocus()) {
                            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
//                    SMSSDK.getVerificationCode("86", phone);
//                    timeCounter.cancel();
//                    timeCounter = new TimeCounter(60000, 1000);
                        timeCounter.start();
                        initVerificationViewBtnNext();
                    }
                });
            } else {
                initPhoneViewBtnNext();
            }
        }
    }
}
