package xyz.egoistk21.aiya.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.base.LazyFragment;
import xyz.egoistk21.aiya.util.OkHttpUtil;

/**
 * Created by EGOISTK on 2017/3/16.
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_username:
                etUsername.setText("");
                break;
            case R.id.btn_login:
                OkHttpUtil.initUser(etUsername.getText().toString());
                break;
        }
        super.onClick(v);
    }
}
