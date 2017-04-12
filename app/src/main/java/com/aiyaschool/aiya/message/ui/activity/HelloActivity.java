package com.aiyaschool.aiya.message.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aiyaschool.aiya.R;

public class HelloActivity extends AppCompatActivity {
    Button registerButton,loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        registerButton = (Button) findViewById(R.id.hello_activity_register);
        loginButton = (Button) findViewById(R.id.hello_activity_login);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HelloActivity.this,RegisterFirstActivity.class);
                startActivity(i);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HelloActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
