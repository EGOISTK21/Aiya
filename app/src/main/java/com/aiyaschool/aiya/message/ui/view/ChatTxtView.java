package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.tencent.TIMElem;
import com.tencent.TIMTextElem;

/**
 * Created by ShootHzj on 2016/11/1.
 */

public class ChatTxtView extends ChatMsgItemView{
    private TextView tvTxt;
    private ChatQQActivity activity;
    public ChatTxtView(Context context, TIMElem messageBean) {
        super(context,messageBean);
    }

    @Override
    protected void loadView() {
        View view = View.inflate(context, R.layout.view_chat_txt, this);
        tvTxt = (TextView) view.findViewById(R.id.tv_chat_text);
    }

    @Override
    protected void handleData() {

    }

    @Override
    protected void display() {
        activity = (ChatQQActivity) context;
        TIMTextElem aux = (TIMTextElem) msgBean;
        tvTxt.setText(aux.getText());
    }

    @Override
    protected void onClick() {

    }

    @Override
    protected void onLongClick() {

    }
}