package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.sign.SignActivity;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.StatusBarUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoreSettingActivity extends RxAppCompatActivity {

    private static final String TAG = "MoreSettingActivity";
    @BindView(R.id.tv)
    AppCompatTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
        ButterKnife.bind(this);
        StatusBarUtil.init(this);
    }

    @OnClick(value = R.id.btn_login_out)
    void loginOut() {
        SignUtil.clearLoginToken();
        SignUtil.removeAccessToken();
        startActivity(new Intent(this, SignActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @OnClick(value = R.id.btn_clear_all)
    void clearAll() {
        UserUtil.clearAll();
    }

    @OnClick(value = R.id.btn_print)
    void print() {
        tv.setText(MyApplication.getUser().toString());
    }

    @OnClick(value = R.id.btn_destroy_love)
    void destroyLove() {
        APIUtil.getDestroyLoveApi()
                .destroyLove(MyApplication.getUser().getLoveId())
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: destroyLove");
                    }

                    @Override
                    public void onNext(@NonNull HttpResult httpResult) {
                        Log.i(TAG, "onNext: destroyLove " + httpResult);
                        if ("2000".equals(httpResult.getState())) {
                            UserUtil.setLoveId("0");
                            setResult(RESULT_OK, new Intent().putExtra("flag", "destroyLove"));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: destroyLove");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: destroyLove");
                    }
                });
    }
}
