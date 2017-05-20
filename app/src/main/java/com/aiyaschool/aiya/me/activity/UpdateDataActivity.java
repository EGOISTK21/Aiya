package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.mvpupdate.UpdateUserDataContract;
import com.aiyaschool.aiya.me.mvpupdate.UpdateUserDataPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateDataActivity extends AppCompatActivity implements UpdateUserDataContract.View {

    private static final String TAG = "UpdateDataActivity";
    private String flag;
    private UpdateUserDataContract.Presenter presenter;
    private Map<String, String> map;

    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_change)
    TextView mTvChange;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_describe)
    TextView mTvDescribe;
    private String oldNickName;
    private String oldSignName;
    private String oldHobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        ButterKnife.bind(this);

        presenter = new UpdateUserDataPresenter(this);
        map = new HashMap<>();

        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra("nick_name"))) {
            oldNickName = intent.getStringExtra("nick_name");
            flag = "nick_name";
            Log.d(TAG, "onCreate: " + oldNickName);
            mTvChange.setText("更改昵称");
            mTvDescribe.setText("好的昵称可以让你的朋友更容易记住你");
            mEtContent.setText(oldNickName);
        } else if (!TextUtils.isEmpty(intent.getStringExtra("sign_name"))) {
            oldSignName = intent.getStringExtra("sign_name");
            flag = "sign_name";
            mTvChange.setText("更改个性签名");
            mTvDescribe.setText("好的个性签名展现你的个性");
            mEtContent.setText(oldSignName);
        } else if (!TextUtils.isEmpty(intent.getStringExtra("oldHobby"))) {
            oldHobby = intent.getStringExtra("oldHobby");
            flag = "hobby";
            mTvChange.setText("更改兴趣爱好");
            mTvDescribe.setText("让别人知道你的兴趣爱好");
            mEtContent.setText(oldHobby);
        }
        mEtContent.setSelection(mEtContent.getText().length());

    }

    @OnClick({R.id.tv_cancel, R.id.tv_change, R.id.tv_save, R.id.et_content, R.id.tv_describe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_save:
                //Todo 保存本地加上传
                User user = MyApplication.getUser();
                String newData = mEtContent.getText().toString();
                Intent intent = new Intent();
                switch (flag) {
                    case "nick_name":
                        if (!newData.equals(oldNickName)) {
                            user.setUsername(newData);
                            Log.d(TAG, "onClick: " + user.getUsername());
                            map.put("username", newData);
                            intent.putExtra("nick_name", newData);
                        }
                        break;
                    case "sign_name":
                        if (!newData.equals(oldSignName)) {
                            user.setProfile(newData);
                            map.put("profile", newData);
                            intent.putExtra("sign_name", newData);
                        }
                        break;
                    case "hobby":
                        if (!newData.equals(oldHobby)) {
                            user.setHobby(newData);
                            map.put("hobby", newData);
                            intent.putExtra("hobby", newData);
                        }
                        break;
                }
                presenter.updateUserData(map);
                MyApplication.setUser(user);
                setResult(RESULT_OK, intent);
                finish();
                break;


        }
    }

    @Override
    public void showUpdateSuccess() {
        switch (flag) {
            case "nick_name":
                Toast.makeText(UpdateDataActivity.this, "修改昵称成功", Toast.LENGTH_SHORT).show();
                break;
            case "sign_name":
                Toast.makeText(UpdateDataActivity.this, "修改个性签名成功", Toast.LENGTH_SHORT).show();
                break;
            case "oldHobby":
                Toast.makeText(UpdateDataActivity.this, "修改兴趣爱好成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
