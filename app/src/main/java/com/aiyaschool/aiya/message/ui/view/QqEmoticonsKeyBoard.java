package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aiyaschool.aiya.R;

import java.util.ArrayList;

import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;
import sj.keyboard.widget.FuncLayout;

/**
 * Created by XZY on 2017/2/26.
 */

public class QqEmoticonsKeyBoard extends AutoHeightLayout implements EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsToolBarView.OnToolBarItemClickListener, EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener{
    public final int APPS_HEIGHT = 256;

    public static final int FUNC_TYPE_IMAGE = 1;
    public static final int FUNC_TYPE_VOICE = 2;
    public static final int FUNC_TYPE_EMOTICON = 3;
    public static final int FUNC_TYPE_GIFT = 4;

    protected LayoutInflater mInflater;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected QqEmoticonsToolBarView mEmoticonsToolBarView;
    protected boolean mDispatchKeyEventPreImeLock = false;
    private EmoticonsEditText etChat;
    private Button btnSend;
    private ImageView btnVoice;
    private ImageView btnImage;
    private ImageView btnEmoticon;
    private ImageView btnGift;
    private FuncLayout lyKvml;

    private LinearLayout llKeyboard;
    private View view;

    private KeyBoardClickListener keyboardClickListener;

    public QqEmoticonsKeyBoard(Context context, AttributeSet attrs){
        super(context,attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.view_keyboard_qq,this);
        initView();
        initFuncView();
    }

    protected void initView(){
        etChat = (EmoticonsEditText) view.findViewById(R.id.et_chat);
        btnSend = (Button) view.findViewById(R.id.btn_send);
        btnVoice = (ImageView) view.findViewById(R.id.btn_voice);
        btnImage = (ImageView) view.findViewById(R.id.btn_image);
        btnEmoticon = (ImageView) view.findViewById(R.id.btn_emoticon);
        btnGift = (ImageView) view.findViewById(R.id.btn_gift);
        llKeyboard = (LinearLayout) view.findViewById(R.id.ll_keyboard);
        lyKvml = (FuncLayout) view.findViewById(R.id.ly_kvml);

        etChat.setOnBackKeyClickListener(this);
//        btnSend.setOnClickListener(this);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFuncView(FUNC_TYPE_VOICE);
                setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
                if (keyboardClickListener != null) {
                    keyboardClickListener.OnKeyBoardClickListener(FUNC_TYPE_VOICE);
                }
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFuncView(FUNC_TYPE_IMAGE);
                setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
            }
        });
        btnEmoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFuncView(FUNC_TYPE_EMOTICON);
                setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
            }
        });
        btnGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFuncView(FUNC_TYPE_GIFT);
                setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
            }
        });
    }

    protected void initFuncView(){
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView(){
        View keyboardView = inflateFunc();
        lyKvml.addFuncView(FUNC_TYPE_EMOTICON,keyboardView);
        mEmoticonsFuncView = (EmoticonsFuncView) keyboardView.findViewById(R.id.view_epv);
        mEmoticonsIndicatorView = ((EmoticonsIndicatorView) keyboardView.findViewById(R.id.view_eiv));
        mEmoticonsToolBarView = ((QqEmoticonsToolBarView) keyboardView.findViewById(R.id.view_etv));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        lyKvml.setOnFuncChangeListener(this);

    }

    protected View inflateFunc(){
        return mInflater.inflate(R.layout.view_func_emoticon_qq,null);
    }

    protected void initEditView(){
        etChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!etChat.isFocused()){
                    etChat.setFocusable(true);
                    etChat.setFocusableInTouchMode(true);
                }
                return false;
            }
        });
        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg);
                } else {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg_disable);
                }
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter){
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(int type, View view) {
        lyKvml.addFuncView(type, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(getContext());
        lyKvml.hideAllFuncView();
        resetIcon();
    }

    public void resetIcon() {
        btnVoice.setImageResource(R.drawable.qq_skin_aio_panel_ptt);
        btnImage.setImageResource(R.drawable.qq_skin_aio_panel_image);
        btnEmoticon.setImageResource(R.drawable.qq_skin_aio_panel_emotion);
    }

    protected void toggleFuncView(int key) {
        lyKvml.toggleFuncView(key, isSoftKeyboardPop(), etChat);
    }

    @Override
    public void onFuncChange(int key) {
        resetIcon();
        switch (key) {
            case FUNC_TYPE_VOICE:
                btnVoice.setImageResource(R.mipmap.qq_skin_aio_panel_ptt_press);
                break;
            case FUNC_TYPE_IMAGE:
                btnImage.setImageResource(R.mipmap.qq_skin_aio_panel_image_press);
                break;
            case FUNC_TYPE_EMOTICON:
                btnEmoticon.setImageResource(R.mipmap.qq_skin_aio_panel_emotion_press);
                break;
        }
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyKvml.getLayoutParams();
        params.height = height;
        lyKvml.setLayoutParams(params);
        super.OnSoftPop(height);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        lyKvml.updateHeight(height);
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        lyKvml.setVisibility(true);
        onFuncChange(lyKvml.DEF_KEY);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (lyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(lyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        lyKvml.addOnKeyBoardListener(l);
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override
    public void onBackKeyClick() {
        if (lyKvml.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    public boolean canBack() {
        return !lyKvml.isShown();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (lyKvml.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    public EmoticonsEditText getEtChat() {
        return etChat;
    }

    public Button getBtnSend() {
        return btnSend;
    }



    public interface KeyBoardClickListener{
        void OnKeyBoardClickListener(int position);
    }

    public void setKeyBoardInterface(KeyBoardClickListener keyboardClickListener) {
        this.keyboardClickListener = keyboardClickListener;
    }
}
