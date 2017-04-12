package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.aiyaschool.aiya.R;

/**
 * Created by ShootHzj on 2016/10/26.
 */

public class ChatMessageToView extends ChatMessageView {

    private final String TAG = ChatMessageView.class.getSimpleName();
    private byte failureMsgType = 0;

    public ChatMessageToView(Context context) {
        super(context);
    }

    public ChatMessageToView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatMessageToView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View loadContentView() {
        View view = inflate(context, R.layout.chat_item_message_to,this);
        return view;
    }

    @Override
    protected void setBackground() {
        //如果不是动态表情的话
        flMsg.setBackgroundResource(R.drawable.chat_item_to_bg);
    }


}
