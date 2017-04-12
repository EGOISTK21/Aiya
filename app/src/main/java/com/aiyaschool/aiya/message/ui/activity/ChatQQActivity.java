package com.aiyaschool.aiya.message.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.ExpressionType;
import com.aiyaschool.aiya.message.adapter.ChatAdapter;
import com.aiyaschool.aiya.message.adapter.ChatPhotosPagerAdapter;
import com.aiyaschool.aiya.message.bean.OptionBean;
import com.aiyaschool.aiya.message.ui.view.ChatRecordVoiceView;
import com.aiyaschool.aiya.message.ui.view.ChatSelectGiftView;
import com.aiyaschool.aiya.message.ui.view.ChatSelectImageView;
import com.aiyaschool.aiya.message.ui.view.QqEmoticonsKeyBoard;
import com.aiyaschool.aiya.message.utils.SimpleCommonUtils;
import com.sj.emoji.EmojiBean;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ChatQQActivity extends AppCompatActivity implements FuncLayout.OnFuncKeyBoardListener{

    private static ChatAdapter chatAdapter;//保持了一个chatAdapter的引用
    private SwipeRefreshLayout refreshLayout;//页面刷新时使用
    private RecyclerView recyclerView;//显示页面的recyclerView
    private QqEmoticonsKeyBoard ekBar;
    private Activity context;
    //聊天对象ID
    private static long chatID = 0;
    private boolean isRefreshing = false;//是否在加载历史纪录
    private boolean isBottom = true; //如果view不在底部，也就是在查看消息，那么如果有新消息，不滚动到底部
    private static ArrayList<String> msgList = new ArrayList<>();
    private TIMMessage dragHelper=null;
    private TIMConversation conversation;
    private boolean firstLoad;
    private boolean isChatShowing;
    /*

     */
    private LinearLayout chatPhotosLayout;
    private TIMImageElem nowShowing;
    private ArrayList<TIMElem> list;
    private int index = 0;
    private TextView progressTextView;
    private ViewPager viewPager;
    private ChatPhotosPagerAdapter adapter;
    private ArrayList<TIMImage> imgList = new ArrayList<>();
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_main);
        firstLoad = true;
        context = this;
        String identifier = getIntent().getStringExtra("identifier");
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,identifier);
        recyclerView = (RecyclerView) findViewById(R.id.rc_chat);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        ekBar = (QqEmoticonsKeyBoard) findViewById(R.id.ek_bar);
        //
        chatPhotosLayout = (LinearLayout) findViewById(R.id.activity_chat_photos);
        progressTextView = (TextView) findViewById(R.id.photo_progress_showText);
        viewPager =(ViewPager) findViewById(R.id.pager);
        //
        ArrayList<TIMAddFriendRequest> aux1024 = new ArrayList<>();
        TIMFriendshipManager.getInstance().addFriend(aux1024, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {

            }
        });
        setViews();
        setListener();
//        ekBar.getEtChat().setText(getDraftMessage(getChatID() + ""));
        //此处应该拿到chat对象，看看之前是否有历史聊天记录，如果没有就 refreshLayout.setRefreshing(false)否则的话，就要getHistoryMsg
        getHistoryMsg(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 如果之前发送过推送，现在就应该清除推送
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO 把所有消息设置为已读
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(msgList!=null){
            msgList.clear();
        }
    }
    private LinearLayoutManager layoutManager;

    protected void setViews(){
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<TIMElem> sh = new ArrayList<>();
        ArrayList<Integer> sh1 = new ArrayList<>();
        ArrayList<Long> sh2 = new ArrayList<>();
        chatAdapter = new ChatAdapter((ChatQQActivity) context,sh,sh1,sh2);
        recyclerView.setAdapter(chatAdapter);
        initEmoticonsKeyBoardBar();
    }

    protected void setListener(){
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ekBar.reset();
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                isBottom = chatAdapter.getItemCount() - lastVisibleItem <= 3;
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isRefreshing){
                    refreshLayout.setRefreshing(false);
                }
                isRefreshing = true;
                getHistoryMsg(dragHelper);

            }
        });

        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                for(int i=0;i<list.size();i++){
                    TIMMessage aux = list.get(i);
                    addChatMsg(aux);
                }
                return true;
            }
        });
    }



    EmoticonClickListener emojiCLickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                String content = null;
                if (o instanceof EmojiBean) {
                    content = ((EmojiBean) o).emoji;
                } else if (o instanceof EmoticonEntity) {
                    content = ((EmoticonEntity) o).getIconUri();
                }
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                if (actionType == ExpressionType.EMOTICON_CLICK_TEXT) {
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };


    private void initEmoticonsKeyBoardBar(){
        SimpleCommonUtils utils = new SimpleCommonUtils();
        utils.initEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(utils.getCommonAdapter(this, emojiCLickListener));

        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_IMAGE,new ChatSelectImageView(this,conversation));
        ekBar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_VOICE,new ChatRecordVoiceView(this,conversation));
        ChatSelectGiftView selectGiftView = new ChatSelectGiftView(this,conversation);
        ekBar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_GIFT,selectGiftView);
        selectGiftView.getQqGridView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        sendGift();
                        Toast.makeText(context, "发送礼物", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int i, int i1, int i2, int i3) {
                scrollToBottom();
            }
        });
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ekBar.getEtChat().getText().toString().equals("")) return;
                final TIMMessage msg = new TIMMessage();
                final TIMTextElem elem = new TIMTextElem();
                elem.setText(ekBar.getEtChat().getText().toString());
                msg.addElement(elem);
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
                    public void onError(int i, String s) {
                        Log.e("GJA", "send message failed. code: " + i + " errmsg: " + s);
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        addChatMsg(msg);
                    }
                });
                ekBar.getEtChat().setText("");
                scrollToBottom();
            }
        });
    }

    private void sendGift(){
        TIMCustomElem customElem = new TIMCustomElem();
        byte[] data = new byte[2];
        data[0] = 0;
        data[1] = 1;
        customElem.setData(data);
        final TIMMessage tim = new TIMMessage();
        tim.addElement(customElem);
        conversation.sendMessage(tim, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                addChatMsg(tim);
            }
        });
    }

    public void scrollToBottom() {
        recyclerView.requestLayout();
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (chatAdapter.getItemCount() > 0) {
                        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                    }
                }
            });
    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        ekBar.reset();
    }

    private long lastMsgID = 0;
    private final int msgOffSet = 12;
    private long maxMsgID = 0;

    public int getMsgOffSet() {
        return msgOffSet;
    }

    private void getHistoryMsg(TIMMessage temp){
        conversation.getMessage(10, temp, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMMessage> list1) {
                ArrayList<TIMMessage> list = new ArrayList<TIMMessage>();
                for(TIMMessage timMessage:list1){
                    if(timMessage.getElement(0).getType()== TIMElemType.Image){
                        if (((TIMImageElem)timMessage.getElement(0)).getImageList().size()==0)
                            continue;
                    }
                    list.add(timMessage);
                }
                if (list != null && list.size() > 0) {
                    int size = list.size();
                    if (list != null && list.size() != 0) {
                        dragHelper = list.get(list.size()-1);
                    }
                    chatAdapter.addAll(0,list);
                    chatAdapter.customeNotifyItemRangeInserted(0, size, recyclerView);// todo:聊天数据集合改变，应该显示的时间集合相应的改变。
                    if(firstLoad){
                        scrollToBottom();
                        firstLoad = false;
                    }
                }
                refreshLayout.setRefreshing(false);
                isRefreshing = false;
            }
        });
    }
    public void addChatMsg(TIMMessage msg){
        if(msg.getElement(0).getType()== TIMElemType.Image){
            if (((TIMImageElem)msg.getElement(0)).getImageList().size()<0)
            return;
        }
        chatAdapter.addChatMsg(msg);
    }

    public void addBeforeChatMsg(List<TIMMessage> msgs) {
//        chatAdapter.addChatMsg(msg);
        chatAdapter.addBeforeChatMsgs(msgs);
    }

    public void addChatMsg(List<TIMMessage> msgs){
        for(TIMMessage m:msgs){
            chatAdapter.addChatMsg(m);
        }
    }

    public static final int TAKEPHOTO_CODE = 1101;
    private final int CROP_CODE = 1102;
    public static final int TYPE_FORWORD_MSG = 1103;
    public static String takePhotoPath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //todo 待测试
            case OptionBean.TYPE_PIC://图片返回
                if(data==null) return;
                ArrayList<String> imgList = data.getStringArrayListExtra("data");
                for (String path : imgList) {
                    Luban.get(context).load(new File(path)).putGear(Luban.THIRD_GEAR)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    TIMMessage timMessage = new TIMMessage();
                                    TIMImageElem aux = new TIMImageElem();
                                    aux.setPath(file.getPath());
                                    timMessage.addElement(aux);
                                    conversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {
                                        @Override
                                        public void onError(int i, String s) {

                                        }

                                        @Override
                                        public void onSuccess(TIMMessage timMessage) {
                                            ChatQQActivity a = (ChatQQActivity) context;
                                            a.addChatMsg(timMessage);
                                        }
                                    });
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }).launch();
                }
                chatAdapter.notifyDataSetChanged();
                break;
            case ChatQQActivity.TAKEPHOTO_CODE:
                String xx = takePhotoPath;
                int i = 11;
                    Luban.get(context).load(new File(takePhotoPath)).putGear(Luban.THIRD_GEAR)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    TIMMessage timMessage = new TIMMessage();
                                    TIMImageElem aux = new TIMImageElem();
                                    aux.setPath(file.getPath());
                                    timMessage.addElement(aux);
                                    conversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {
                                        @Override
                                        public void onError(int i, String s) {

                                        }

                                        @Override
                                        public void onSuccess(TIMMessage timMessage) {
                                            ChatQQActivity a = (ChatQQActivity) context;
                                            a.addChatMsg(timMessage);
                                        }
                                    });
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }).launch();
                break;
        }
    }

    public void startPhotoPreview(TIMImageElem m){
        ekBar.setVisibility(View.GONE);
        chatPhotosLayout.setVisibility(View.VISIBLE);
        imgList = new ArrayList<>();
        ArrayList<TIMElem> list = chatAdapter.msgList;
        int auxxxx = 0;
        for(TIMElem i : list){
            if(i.getType()== TIMElemType.Image){
                TIMImageElem gi = (TIMImageElem) i;
                if(gi==m) index = auxxxx;
                if(gi.getImageList().size()>0){
                    imgList.add(gi.getImageList().get(0));
                    auxxxx++;
                }

            }
        }
        adapter = new ChatPhotosPagerAdapter(this,imgList);
        size = imgList.size();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);

        progressTextView.setText(index+1+"/"+size);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressTextView.setText(position+1+"/"+size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        chatPhotosLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vanishThePreview();
            }
        });
    }

    private void vanishThePreview() {
        ekBar.setVisibility(View.VISIBLE);
        chatPhotosLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(chatPhotosLayout.getVisibility()==View.VISIBLE){
                vanishThePreview();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
