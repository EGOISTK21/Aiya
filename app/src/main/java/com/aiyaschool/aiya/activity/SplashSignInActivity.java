package com.aiyaschool.aiya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.aiyaschool.aiya.R;

public class SplashSignInActivity extends AppCompatActivity {

    private EditText etAccount;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_sign_in);
        initView();
        initListener();
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.et_account);
        btnStart = (Button) findViewById(R.id.btn_start);
    }

    private void initListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = String.valueOf(etAccount.getText());
                startActivity(account.equals("")
                        ? new Intent(SplashSignInActivity.this, SignUpActivity.class)
                        : new Intent(SplashSignInActivity.this, MainActivity.class));
            }
        });
    }


}
