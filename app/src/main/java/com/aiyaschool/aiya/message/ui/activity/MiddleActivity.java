package com.aiyaschool.aiya.message.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendAllowType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

public class MiddleActivity extends AppCompatActivity {
    Context context;
    EditText accountEditText;
    Button addButton;
    Button chatButton;
    Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
        findViews();
        context = this;
        TIMFriendshipManager.getInstance().setAllowType(TIMFriendAllowType.TIM_FRIEND_ALLOW_ANY,null);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TIMAddFriendRequest> shoothzj  = new ArrayList<>();
                TIMAddFriendRequest aux1024 = new TIMAddFriendRequest();
                aux1024.setIdentifier(accountEditText.getText().toString());
                shoothzj.add(aux1024);
                TIMFriendshipManager.getInstance().addFriend(shoothzj, new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> timFriendResults) {
                        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(MiddleActivity.this, MsgListActivity.class);
                context.startActivity(i);
//                TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>() {
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
//                        String extra = timUserProfiles.get(0).getIdentifier();
//                        i.putExtra("identifier",extra);
//                        Toast.makeText(context, extra, Toast.LENGTH_SHORT).show();
//                        context.startActivity(i);
//                    }
//                });

            }
        });
    }

    private void findViews() {
        accountEditText = (EditText) findViewById(R.id.middle_activity_account_edittext);
        addButton = (Button) findViewById(R.id.middle_activity_add_button);
        chatButton = (Button) findViewById(R.id.middle_activity_chat_button);
        test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MiddleActivity.this,ForgetOneActivity.class);
                MiddleActivity.this.startActivity(i);
            }
        });
    }
}
