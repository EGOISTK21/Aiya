package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.utils.ChatMsgItemFactory;
import com.tencent.TIMElem;


/**
 * Created by ShootHzj on 2016/10/20.
 */

public abstract class ChatMessageView extends LinearLayout implements View.OnClickListener,View.OnLongClickListener{

    private final String TAG = ChatMessageView.class.getSimpleName();

    protected Context context;
    private TIMElem messageBean;
    protected boolean showName = false;//显示名称
    private boolean isMe = false;//是否是自己发的消息
    private int isSend = 1;



    private MsgStatus status = MsgStatus.NORMAL;

    public enum MsgStatus {
        NORMAL,//正常消息
    }

    protected TextView tvFromName;//发消息人
    protected LinearLayout llMsgStatusTime;//延迟消息提醒时间
    protected TextView tvMsgStatus;//延迟或提醒
    protected TextView tvMsgStatusTime;//延迟消息提醒时间
    protected FrameLayout flMsg;//消息的实体
    protected ChatMsgItemView itemView;//单个item消息
    protected ProgressBar proSend;//发送消息

    public ChatMessageView(Context context){
        super(context);
        initView(context);
    }

    public ChatMessageView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView(context);
    }

    public ChatMessageView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    protected abstract void setBackground();
    protected abstract View loadContentView();

    public void initView(Context context){
        this.context = context;
        View view = loadContentView();
        findViews(view);
    }

    private void findViews(View view) {
        //点击消息控件（包括所有的消息类型）
        flMsg = (FrameLayout) view.findViewById(R.id.fl_chat_content);
        //显示名称/昵称
        tvFromName = (TextView) view.findViewById(R.id.tv_chat_fromName);
        //消息活动类型（延迟。提醒）
        llMsgStatusTime = (LinearLayout) view.findViewById(R.id.ll_chat_remindTime);
        tvMsgStatusTime = (TextView) view.findViewById(R.id.tv_chat_delayOrRemind_time);
        tvMsgStatus = (TextView) view.findViewById(R.id.tv_chat_delayOrRemind);
        proSend = (ProgressBar) view.findViewById(R.id.progress_chat_send);
    }
    public void setViews(TIMElem message, int isSend){
        this.messageBean = message;
        this.isSend = isSend;
        setMsgDisplay();
        setListeners();
    }

    public void setMsgDisplay(){
        displayNormalMsg();
    }

    protected void displayNormalMsg(){
        flMsg.removeAllViews();

        itemView = ChatMsgItemFactory.createItemView(context,messageBean,isSend);
        flMsg.addView(itemView);
    }

    @Override
    public void onClick(View v) {
        itemView.onClick();
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    protected void setListeners() {
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }
}
