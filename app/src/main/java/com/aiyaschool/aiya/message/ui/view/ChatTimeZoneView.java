package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.DateTimeUtils;


/**
 * Created by ShootHzj on 2016/10/20.
 */

public class ChatTimeZoneView extends LinearLayout {

    private TextView tvLocalTime;

    public ChatTimeZoneView(Context context) {
        super(context);
        initView(context);
    }

    public ChatTimeZoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChatTimeZoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER_HORIZONTAL);
        View view = View.inflate(context, R.layout.chat_item_timezone, this);
        tvLocalTime = (TextView) view.findViewById(R.id.tv_chat_localTime);
    }

    public void setViews(long time, int timeZone) {
        setVisibility(VISIBLE);
        tvLocalTime.setText(DateTimeUtils.setChatTime(time));
    }

}
