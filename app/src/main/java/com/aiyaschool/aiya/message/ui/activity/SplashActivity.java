package com.aiyaschool.aiya.message.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.Constant;
import com.aiyaschool.aiya.message.bean.UserInfo;
import com.aiyaschool.aiya.message.utils.TLSService;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;

import tencent.tls.platform.TLSLoginHelper;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        init();

    }

    private void init() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);

        String id = TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSLoginHelper.getInstance().getUserSig(id));
//        new Handler().postDelayed(new Runnable() {
//        },1000);
//            }
//                }
//                    navToHello();
//                }else{
//                    navToHome();
//                if(isUserLogin()){
//            public void run() {
//            @Override
//        加以下两行跳过注册
        Intent i = new Intent(SplashActivity.this, MiddleActivity.class);

        startActivity(i);
    }

    public void navToHome() {

        TIMUser user = new TIMUser();
        user.setAccountType(Constant.ACCOUNT_TYPE + "");
        user.setAppIdAt3rd(Constant.SDK_APPID + "");

        user.setIdentifier(UserInfo.getInstance().getId());
        TIMManager.getInstance().login(Constant.SDK_APPID, user, UserInfo.getInstance().getUserSig(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(SplashActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess() {
                Intent i = new Intent(SplashActivity.this, MiddleActivity.class);
                startActivity(i);
//                SplashActivity.this.onDestroy();
            }
        });
        //LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    public void navToHello() {

        Intent i = new Intent(SplashActivity.this, HelloActivity.class);
        startActivity(i);
    }

    public boolean isUserLogin() {
//        return false;

        return UserInfo.getInstance().getId() != null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));
    }

}
