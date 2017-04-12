package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.aiyaschool.aiya.R;

/**
 * Created by ShootHzj on 2016/10/26.
 */

public class ChatMessageFromView extends ChatMessageView {

    public ChatMessageFromView(Context context) {
        super(context);
    }

    public ChatMessageFromView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatMessageFromView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View loadContentView() {
        View view = inflate(context, R.layout.chat_item_message_from,this);
        return view;
    }

    @Override
    protected void setBackground() {
        //如果不是动态表情
        flMsg.setBackgroundResource(R.drawable.chat_item_from_bg);
    }
}
