package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.view.View;

import com.aiyaschool.aiya.R;
import com.tencent.TIMElem;

/**
 * Created by ShootHzj on 2016/11/2.
 */

public class ChatGiftOneView extends ChatMsgItemView {

    public ChatGiftOneView(Context context, TIMElem messageBean) {
        super(context, messageBean);
    }

    @Override
    protected void loadView() {
        View view = inflate(context, R.layout.view_chat_gift_one,this);
    }

    @Override
    protected void handleData() {

    }

    @Override
    protected void display() {

    }

    @Override
    protected void onClick() {

    }

    @Override
    protected void onLongClick() {

    }
}
