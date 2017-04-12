package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.widget.LinearLayout;

import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.tencent.TIMElem;

/**
 * Created by ShootHzj on 2016/10/26.
 */

public abstract class ChatMsgItemView extends LinearLayout {
    private final String TAG = ChatMsgItemView.class.getSimpleName();

    protected Context context;
    protected ChatQQActivity activity;
    protected TIMElem msgBean;
    protected int type;
    protected boolean isMe = false;
    protected boolean from;

    public ChatMsgItemView(Context context,TIMElem messageBean){
        super(context);
        this.msgBean = messageBean;
        this.activity = (ChatQQActivity) context;
        init(context);
    }
    public ChatMsgItemView(Context context, TIMElem messageBean, int isSend){
        super(context);
        this.msgBean = messageBean;
        this.activity = (ChatQQActivity) context;
        from = isSend==0;
        init(context);
    }

    private void init(Context context){
        this.context = context;
        loadView();
        handleData();
        display();
    }

    protected abstract void loadView();

    protected abstract void handleData();

    protected abstract void display();

    protected abstract void onClick();

    protected abstract void onLongClick();




    public void setProgress(long localID,int progress){

    }
}
