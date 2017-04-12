package com.aiyaschool.aiya.message.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.aiyaschool.aiya.R;

/**
 * Created by XZY on 2017/2/26.
 * 单例模式
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
//    为了实例化新的fragment，我们新增了
//    名为createFragment()的抽象方法。 SingleFragmentActivity的子类会实现该方法，来返回
//    由activity托管的fragment实例。需要建立fragment的子类继承该单例即可。
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
