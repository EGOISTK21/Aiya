package com.aiyaschool.aiya.activity.sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.aiyaschool.aiya.activity.form.FormActivity;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 登陆注册View实现类
 * Created by EGOISTK21 on 2017/4/15.
 */

public class SignActivity extends RxAppCompatActivity implements SignContract.View {

    private final static String TAG = "SignActivity";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mPresenter = new SignPresenter(this);
        StatusBarUtil.init(this);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    private void initView() {
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Spannable spannable = new SpannableString("注册即表示同意《爱呀用户协议》");
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignActivity.this,
                R.color.colorPrimaryDark)), 7, 15, Spanned.SPAN_POINT_MARK);
        tvWarn.setText(spannable);
        phone = SignUtil.getPhone();
        verification = "";
        timeCounter = new TimeCounter(60000, 1000);
        initPhoneView();
    }

    @OnClick(value = R.id.tv_back)
    void back() {
        initPhoneView();
    }

    @OnClick(value = R.id.tv_sub_title)
    void reAcquire() {
        if (tvSubTitle.getText().toString().contains("重新获取")) {
//            SMSSDK.getVerificationCode("86", phone);
            timeCounter.start();
        }
    }

    @OnTextChanged(value = R.id.et_sign)
    void etSignChanged(CharSequence sign) {
        if (!isVerificationView) {
            phone = String.valueOf(sign);
            btnNext.setBackgroundDrawable(SignUtil.isValidPhone(phone)
                    ? ContextCompat.getDrawable(this, R.drawable.button_selector_with_corner_24)
                    : ContextCompat.getDrawable(this, R.drawable.button_unclickable_with_corner_24));
            btnNext.setClickable(SignUtil.isValidPhone(phone));
        } else {
            verification = String.valueOf(sign);
            btnNext.setBackgroundDrawable(SignUtil.isValidVerification(verification)
                    ? ContextCompat.getDrawable(this, R.drawable.button_selector_with_corner_24)
                    : ContextCompat.getDrawable(this, R.drawable.button_unclickable_with_corner_24));
            btnNext.setClickable(SignUtil.isValidVerification(verification));
        }
    }

    @OnClick(R.id.tv_warn)
    void openWarnWebView() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://aiyaschool.com")));
    }

    private void initPhoneView() {
        isVerificationView = false;
        tvBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("请输入手机号");
        tvSubTitle.setVisibility(View.INVISIBLE);
        etSign.setText(phone);
        etSign.setSelection(phone.length());
        initPhoneViewBtnNext();
        tvWarn.setVisibility(View.INVISIBLE);
    }

    private void initVerificationView() {
        isVerificationView = true;
        tvBack.setVisibility(View.VISIBLE);
        tvTitle.setText("请输入验证码");
        tvSubTitle.setVisibility(View.VISIBLE);
        timeCounter.myStart(phone);
        etSign.setText(verification);
        etSign.setSelection(verification.length());
        initVerificationViewBtnNext();
        tvWarn.setVisibility(View.VISIBLE);
    }

    private void initPhoneViewBtnNext() {
        btnNext.setText("获取验证码");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SMSSDK.getVerificationCode("86", phone);
                initVerificationView();
            }
        });
        btnNext.setClickable(SignUtil.isValidPhone(phone));
    }

    private void initVerificationViewBtnNext() {
        btnNext.setText("启动");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sign(phone, verification);
            }
        });
        btnNext.setClickable(SignUtil.isValidVerification(verification));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCurrentFocus() != null) {
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
        Log.i(TAG, "startFormView");
        startActivity(new Intent(this, FormActivity.class));
        finish();
    }

    @Override
    public void startMainView() {
        Log.i(TAG, "startMainView");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private class TimeCounter extends CountDownTimer {

        private String fixPhone;

        TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            tvSubTitle.setVisibility(View.VISIBLE);
        }

        void myStart(String phone) {
            fixPhone = phone;
            this.start();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Spannable spannable = new SpannableString(String.format("验证码已发送至%s，%2ds后可再次获取", fixPhone, millisUntilFinished / 1000));
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignActivity.this,
                    R.color.colorPrimaryDark)), 19, 22, Spanned.SPAN_POINT_MARK);
            tvSubTitle.setText(spannable);
        }

        @Override
        public void onFinish() {
            Spannable spannable = new SpannableString("验证码已发送至" + fixPhone + "，重新获取");
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(SignActivity.this,
                    R.color.colorPrimaryDark)), 19, 23, Spanned.SPAN_POINT_MARK);
            tvSubTitle.setText(spannable);
        }
    }
}
