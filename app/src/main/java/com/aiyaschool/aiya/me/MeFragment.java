package com.aiyaschool.aiya.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.base.LazyFragment;
import com.aiyaschool.aiya.util.DBUtil;
import com.aiyaschool.aiya.util.OkHttpUtil;


/**
 * Created by EGOISTK21 on 2017/3/16.
 */

public class MeFragment extends LazyFragment {

    private View rootView;
    private EditText etUsername;
    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_me, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        etUsername = (EditText) rootView.findViewById(R.id.et_username);
        btnLogin = (Button) rootView.findViewById(R.id.btn_login);
        etUsername.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        rootView.findViewById(R.id.btn_change_match).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_username:
                etUsername.setText("");
                break;
            case R.id.btn_login:
                String s = DBUtil.getUserName();
                if (s == null) {
                    OkHttpUtil.initUser(etUsername.getText().toString());
                    s = DBUtil.getUserName();
                    System.out.println("null");
                }
                System.out.println(s);
                break;
            case R.id.btn_change_match:
                MyApplication.getInstance().setMatched(!((MyApplication) getActivity().getApplication()).isMatched());
                ((MainActivity) getActivity()).notifyAdapter();
                break;
        }
    }
}
