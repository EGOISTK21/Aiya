package com.aiyaschool.aiya.message.ui.view;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.message.utils.AudioUtils;
import com.aiyaschool.aiya.message.utils.GeometryUtil;
import com.tencent.TIMConversation;
import com.tencent.TIMMessage;
import com.tencent.TIMSoundElem;
import com.tencent.TIMValueCallBack;


/**
 * Created by ShootHzj on 2016/10/20.
 */

public class ChatRecordVoiceView extends RelativeLayout implements View.OnClickListener{
    private static final String TAG = ChatRecordVoiceView.class.getSimpleName();
    private RelativeLayout rl_record;
    private RelativeLayout rl_try_play;
    protected View contentView; // 布局的根对象
    protected Context context;
    private ImageView recorderButton;
    private ImageView tryPlayImg;
    private ImageView deleteVoiceImg;
    private TextView mTextTip; // 显示时间与提示信息
    private RelativeLayout funclayout;
    private Button cancleBtn;
    private Button sendBtn;
    private ImageView playImageView;
    private TextView mTimeTextView;
    private float absoluteDistance; // 播放按钮的圆心与试听按钮圆心(或者取消圆心）的距离,两者应当相同
    // 三个圆心的坐标（相对于屏幕坐标系）
    private int[] bottomLocation;   // 录音按钮圆心位置
    private int[] leftLocation;     // 左侧圆心位置
    private int[] rightLocation;    // 右侧圆心位置
    private Bitmap funcLayoutBGBitmap; // 连接三个圆心的弧线
    private boolean isGetLocationData = true; // 是否是第一次布局完成（第一次获取坐标数据，并进行绘制）
    // 左右两个圆的半径均为布局高度的1/3, 录音按钮的圆心在布局的底部中心位置
    private int width; // 三个圆心所在布局的宽度
    private int height; // ...所在布局的高度
    private int radius; // 左右小圆半径
    private float minDistance; // 触摸屏幕时，能够触发试听或者取消功能的最小距离（可以认为是小圆的半径）
    private static final int MIN_RADIUS = 30; // 左右两侧小圆的半径

    private AudioUtils audioUtils;
    private boolean isStiilingTouch = false;//手指是否在触摸屏幕
    private long audioInfo; // 音频时长
    private TIMConversation conversation;

    public ChatRecordVoiceView(Context context,TIMConversation conversation) {
        this(context, null,conversation);
    }

    public ChatRecordVoiceView(Context context, AttributeSet attrs,TIMConversation conversation) {
        this(context, attrs, 0,conversation);
    }

    public ChatRecordVoiceView(Context context, AttributeSet attrs, int defStyleAttr,TIMConversation conversation) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.conversation = conversation;
        contentView = LayoutInflater.from(this.context).inflate(R.layout.view_chat_record_voice2, this);
        findView();
        setListener();
        AudioUtils.prepareTipsAudio(context);
    }

    private void findView() {
        rl_record = (RelativeLayout) contentView.findViewById(R.id.rl_record);
        rl_try_play = (RelativeLayout) contentView.findViewById(R.id.rl_try_play);

        funclayout = (RelativeLayout) contentView.findViewById(R.id.rl_func_layout);
        recorderButton= (ImageView) contentView.findViewById(R.id.img_record);
        tryPlayImg = (ImageView) contentView.findViewById(R.id.img_try_play);
        deleteVoiceImg = (ImageView) contentView.findViewById(R.id.img_voice_delete);
        mTextTip = (TextView) contentView.findViewById(R.id.tv_tip);
        cancleBtn = (Button) contentView.findViewById(R.id.btn_cancle);
        sendBtn = (Button) contentView.findViewById(R.id.btn_send);
        playImageView = (ImageView) contentView.findViewById(R.id.listen_audio);
        mTimeTextView = (TextView) contentView.findViewById(R.id.tv_tip_time);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initData();
    }

    /**
     * 仅在功能布局不是gone时获取初始化坐标、宽高等数据,进行初始绘制
     */
    private void initData() {

        if(funclayout.getVisibility() == View.GONE){
            funclayout.setVisibility(View.INVISIBLE);
            return;
        }
        if(!isGetLocationData)
            return;
        // 获取功能按键布局的宽高
        width = funclayout.getWidth();
        height = funclayout.getHeight();
        leftLocation = new int[2];
        tryPlayImg.getLocationOnScreen(leftLocation);
        rightLocation = new int[2];
        deleteVoiceImg.getLocationOnScreen(rightLocation);
        radius = height / 3;
        leftLocation[0] += radius;
        leftLocation[1] += radius;
        rightLocation[0] += radius;
        rightLocation[1] += radius;
        bottomLocation = new int[2];
        recorderButton.getLocationOnScreen(bottomLocation);
        int bottomCircleRadius = recorderButton.getHeight() / 2; // 录音大圆半径
        bottomLocation[0] += bottomCircleRadius;
        bottomLocation[1] += bottomCircleRadius;
        absoluteDistance = caculateDistance(new PointF(leftLocation[0], leftLocation[1]),
                new PointF(bottomLocation[0], bottomLocation[1]));

        minDistance = radius;
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE); // 设置为非填充
        // 以下使用的是功能布局funcLayout的自身坐标系
        Path path = new Path();
        path.moveTo(radius, radius);// 起时点(radius, radius)
        path.quadTo(funclayout.getWidth() / 2, (float)(height * (1 + 0.6)), width - radius, radius); // 控制点, 第二个点
        funcLayoutBGBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(funcLayoutBGBitmap);
        mCanvas.drawPath(path, mPaint);
//         funclayout.setBackground(bitmap2Drawable(funcLayoutBGBitmap)); // api 16
        funclayout.setBackgroundDrawable(bitmap2Drawable(funcLayoutBGBitmap));
        funclayout.setVisibility(View.GONE);
        isGetLocationData = false;
    }

    private void setListener() {
        recorderButton.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isStiilingTouch = true;
                        playRecoderAnimation();
                        touchInitView();
                        playFuncAnimation();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updateView(event.getRawX(), event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        mTextTip.setText("按住说话");
                        funclayout.setVisibility(View.INVISIBLE);

                        if(isInner){
                            if(currentLocation == ScaleType.LEFT){
                                rl_record.setVisibility(View.GONE);
                                rl_try_play.setVisibility(View.VISIBLE);
                                if(listener != null)
                                    listener.tryPlayRelase();
                            }else if(currentLocation == ScaleType.RIGHT){
                                if(listener != null)
                                    listener.cancleRelase();
                            }
                        }else{
                            if(listener != null)
                                listener.recordeRelase();
                        }
                        isInner = false;

                        isStiilingTouch = false;
                        if(mValuewAnimator != null){
                            mValuewAnimator.cancel();
                        }
                        break;
                }
                return true;
            }
        });

        setRecordListener(new RecordStatusListener() {
            @Override
            public void recordeNoRelase() {
                mTextTip.setText("录音中...");
            }

            @Override
            public void recordeRelase() {
                audioInfo = audioUtils.stopRecord();
                createAndSendMsg();
            }

            @Override
            public void tryPlayNoRelase() {
                mTextTip.setText("松手试听");
            }

            @Override
            public void tryPlayRelase() {
                audioInfo = audioUtils.stopRecord();
            }

            @Override
            public void cancleNoRelase() {
                mTextTip.setText("松手取消发送");
            }

            @Override
            public void cancleRelase() {
                audioUtils.clearAudioFile();
                audioUtils = null; // 取消之前的录音(置空后，对象的属性取不到)
            }
        });

        cancleBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        playImageView.setOnClickListener(this);
    }

    private void createAndSendMsg() {
        TIMMessage timMessage = new TIMMessage();
        TIMSoundElem timSoundElem = new TIMSoundElem();
        timSoundElem.setPath(audioUtils.getCurrentFilePath());
        timSoundElem.setDuration(audioInfo/1000);
        timMessage.addElement(timSoundElem);
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

    private Drawable initDrawable; // 初始化状态时左右两侧ImageView的内容
    private void touchInitView() {
        funclayout.setVisibility(View.VISIBLE);
        initDrawable = drawCircle(1, ScaleType.INIT, false);
        tryPlayImg.setBackgroundDrawable(initDrawable);
        deleteVoiceImg.setBackgroundDrawable(initDrawable);
    }

    private ObjectAnimator mValuewAnimator;
    /**
     * 播放录音按钮的动画
     */
    private void playRecoderAnimation() {

        if(mValuewAnimator != null){
            mValuewAnimator.cancel(); // 预防疯狂点击
            mValuewAnimator = null;
        }
        mValuewAnimator = ObjectAnimator.ofFloat(recorderButton, "scaleX", 1F, 0.5F, 1F);
        mValuewAnimator.setDuration((long)(0.6 * 1000));
        mValuewAnimator.setEvaluator(new FloatEvaluator());
        mValuewAnimator.setRepeatCount(0);
        mValuewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        mValuewAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValuewAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTextTip.setText("准备中...");
                AudioUtils.playTipsAudio();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mTextTip.setText("0:00");
                mValuewAnimator = null;
                if(isStiilingTouch){
                    audioUtils = new AudioUtils();
                    audioUtils.prepareAndStartRecord();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mValuewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if((float)animation.getAnimatedValue() == 0.5F){

                }
            }
        });
        mValuewAnimator.start();

        ObjectAnimator mValuewAnimator2 = ObjectAnimator.ofFloat(recorderButton, "scaleY", 1F, 0.5F, 1F);
        mValuewAnimator2.setDuration((long)(0.6 * 1000));
        mValuewAnimator2.setEvaluator(new FloatEvaluator());
        mValuewAnimator2.setRepeatCount(0);
        mValuewAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
//        mValuewAnimator2.setRepeatMode(ValueAnimator.REVERSE);
        mValuewAnimator2.start();

    }

    private void playFuncAnimation() {
        // scaleY
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(funclayout, "scaleY", 0.2F, 1F);
        objectAnimatorY.setDuration((long)(0.3 * 1000));
        objectAnimatorY.setEvaluator(new FloatEvaluator());
        objectAnimatorY.setRepeatCount(0);
        objectAnimatorY.setInterpolator(new LinearInterpolator());
        objectAnimatorY.start();
        // scaleX
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(funclayout, "scaleX", 0.2F, 1F);
        objectAnimatorX.setDuration((long)(0.3 * 1000));
        objectAnimatorX.setEvaluator(new FloatEvaluator());
        objectAnimatorX.setRepeatCount(0);
        objectAnimatorX.setInterpolator(new LinearInterpolator());
        objectAnimatorX.start();
        // alpha
        ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(funclayout, "alpha", 0.2F, 1F);
        objectAnimatorAlpha.setDuration((long)(0.3 * 1000));
        objectAnimatorAlpha.setEvaluator(new FloatEvaluator());
        objectAnimatorAlpha.setRepeatCount(0);
        objectAnimatorAlpha.setInterpolator(new LinearInterpolator());
        objectAnimatorAlpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimatorAlpha.start();
    }

    /**
     * 根据手指的触摸位置动态改变界面
     * @param touchRawX 相对于Screen的横坐标
     * @param touchRawY　相对于Screen的纵坐标
     */
    private void updateView(float touchRawX, float touchRawY) {
        float tempLeftDistance = caculateDistance(new PointF(leftLocation[0], leftLocation[1]),
                new PointF(touchRawX, touchRawY));
        float tempRightDistance = caculateDistance(new PointF(rightLocation[0], rightLocation[1]),
                new PointF(touchRawX, touchRawY));
        if(tempLeftDistance < absoluteDistance){
            updateCircleBg(ScaleType.LEFT, tempLeftDistance);
        }else if(tempRightDistance < absoluteDistance){
            updateCircleBg(ScaleType.RIGHT, tempRightDistance);
        }else{
            updateCircleBg(ScaleType.INIT, -1);
        }

//        VIMLog.i(TAG, "标准距离：" + absoluteDistance + "    tempLeftDistance:" + tempLeftDistance + "    tempRightDistance:" + tempRightDistance);
    }

    /**
     * 计算一个点与圆心连线与圆的交点的距离，
     * @param pointF1
     * @param pointF2
     * @return 永远返回非负值且大于等于0
     */
    private float caculateDistance(PointF pointF1, PointF pointF2) {
        float distanceBetween2Points = GeometryUtil.getDistanceBetween2Points(pointF1,
                pointF2) - radius; // 正值表示未到达raius圆的范围，负值表示在小圆内
        return Float.compare(distanceBetween2Points, 0F) <= 0 ? 0F : distanceBetween2Points;
    }
    private float calculateRate(float absoluteDistance , float currentDistance){
        return currentDistance / absoluteDistance;
    }

    private static Drawable bitmap2Drawable(Bitmap bitmap){
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        return (Drawable)bitmapDrawable;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancle:
                rl_record.setVisibility(View.VISIBLE);
                rl_try_play.setVisibility(View.GONE);

                if(audioUtils != null){
                    audioUtils.clearAudioFile();
                    audioUtils = null; // 取消之前的录音(置空后，对象的属性取不到)
                }
                break;
            case R.id.btn_send:
//                ToastUtil.showShort(context, "发送语音");
                rl_record.setVisibility(View.VISIBLE);
                rl_try_play.setVisibility(View.GONE);
                createAndSendMsg();
                break;
            case R.id.listen_audio:
                AudioUtils.play(context, audioUtils.getCurrentFilePath(), new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                    }
                });
                break;
        }
    }

    private enum ScaleType{
        LEFT,RIGHT,INIT
    }

    private boolean isInner = false; // 触摸点是否在小圆内部
    private ScaleType currentLocation = ScaleType.INIT;

    private void updateCircleBg(ScaleType type, float distance){
        float rate = calculateRate(absoluteDistance, distance);
        isInner = Float.compare(rate, 0F) == 0 ? true: false;
        currentLocation = type;

//        VIMLog.i(TAG, "rate:" + rate + "    isInner:" + isInner +  "    radius:" + radius);
        switch (type){
            case LEFT:
                deleteVoiceImg.setBackgroundDrawable(initDrawable);
                tryPlayImg.setBackgroundDrawable(drawCircle(rate, ScaleType.LEFT, isInner));  // 设置背景
                tryPlayImg.requestLayout(); // 刷新布局
                if(listener != null && isInner)
                    listener.tryPlayNoRelase();
                break;
            case RIGHT:
                tryPlayImg.setBackgroundDrawable(initDrawable);
                deleteVoiceImg.setBackgroundDrawable(drawCircle(rate, ScaleType.RIGHT, isInner));
                deleteVoiceImg.requestLayout();
                if(listener != null && isInner)
                    listener.cancleNoRelase();
                break;
            case INIT: //　触摸点不在左右两侧圆的出发范围内
                if(listener != null && !isInner){
                    listener.recordeNoRelase();
                }
                break;
            default:

                break;
        }

    }

    /**
     *
     * @param rate　
     * @param scaleType
     * @return drawable 绘制出的对象
     */
    private Drawable drawCircle(float rate, ScaleType scaleType, boolean isInner){
        // 1024 * 768　屏幕半径为60dp
        int bgRadius = (int)((radius - MIN_RADIUS) * (1-rate) + 0.5F) + MIN_RADIUS;
//        VIMLog.i(TAG, "bgRadius:" + bgRadius);

        //白色圆形
        Paint bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.parseColor("#ffffff"));
        bgPaint.setStyle(Paint.Style.FILL);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if(isInner){
            paint.setColor(Color.parseColor("#66000000"));
            paint.setStyle(Paint.Style.FILL);
        }else{
            paint.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.STROKE);
        }

        Bitmap bitmap = Bitmap.createBitmap(2 * radius, 2 * radius, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.argb(0, 0, 0, 0));
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(radius, radius, bgRadius, bgPaint);
        canvas.drawCircle(radius, radius, bgRadius, paint);
        return bitmap2Drawable(bitmap);
    }

    public interface RecordStatusListener{
        void recordeNoRelase();
        void recordeRelase();
        void tryPlayNoRelase(); // 暂停录音
        void tryPlayRelase();
        void cancleNoRelase(); // 暂停录音
        void cancleRelase();
    }
    private RecordStatusListener listener;

    public void setRecordListener(RecordStatusListener recordListener){
        this.listener = recordListener;
    }

    public void setRecordTime(String time){
        mTextTip.setText(time);
    }


}

