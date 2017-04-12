package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.singleton.SoundPlayAux;
import com.aiyaschool.aiya.message.utils.ScreenUtils;
import com.tencent.TIMElem;
import com.tencent.TIMSoundElem;
import com.tencent.TIMValueCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ShootHzj on 2016/11/1.
 */

public class ChatSoundView extends ChatMsgItemView{

    private View imgView;
    private TextView tvTime;
    private AnimationDrawable animationDrawable;
    private int minItemWidth;
    private int maxItemWidth;
    private RelativeLayout rlAudioRoot;
    View view;
    private static long playingMsgID;
    TIMSoundElem aux;
    public ChatSoundView(Context context, TIMElem messageBean, int isSend) {
        super(context, messageBean,isSend);
    }

    @Override
    protected void loadView() {
        aux = (TIMSoundElem) msgBean;
        View view = View.inflate(context, R.layout.view_chat_sound,this);
        tvTime = (TextView) view.findViewById(R.id.tv_sound_time);
        rlAudioRoot = (RelativeLayout) view.findViewById(R.id.rl_audio_root);
        if (from) {
//            tvTime.setText(aux.getDuration() +"'s");

            imgView = view.findViewById(R.id.img_sound_from);
            //            imgView.setImageResource(R.drawable.chat_audio_from);
            imgView.setBackgroundResource(R.mipmap.chat_audio_from3);
            tvTime.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            tvTime.setTextColor(getResources().getColor(R.color.tx_gray));
            view.findViewById(R.id.img_sound_to).setVisibility(GONE);
        } else {
//            tvTime.setText(aux.getDuration() +"'s");
            imgView = view.findViewById(R.id.img_sound_to);
            //            imgView.setImageResource(R.drawable.chat_audio_to);
            imgView.setBackgroundResource(R.mipmap.v_anim3);
            tvTime.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            tvTime.setTextColor(getResources().getColor(R.color.white));
            view.findViewById(R.id.img_sound_from).setVisibility(GONE);
        }

        int[] screen = ScreenUtils.getInstance().getScreenHeight(context);
        maxItemWidth = (int) (screen[0] * 0.35f);
        minItemWidth = (int) (screen[0] * 0.15f);

    }

    @Override
    protected void handleData() {

    }

    @Override
    protected void display() {
        if (aux == null) {
            return;
        }
        tvTime.setText(aux.getDuration()+"'s");
        ViewGroup.LayoutParams lp = rlAudioRoot.getLayoutParams();
        long duration = aux.getDuration();
        if (duration > 60) {
            duration = 60;
        }
        lp.width = (int) (minItemWidth + maxItemWidth / 60f * duration);
        if (from) {
            imgView.setBackgroundResource(R.drawable.chat_audio_from);
        } else {
            imgView.setBackgroundResource(R.drawable.chat_audio_to);
        }
//        animationDrawable = (AnimationDrawable) imgView.getBackground();
//        if (AudioUtils.isPlaying && playingMsgID == msgBean.getMessageID()) {
//            animationDrawable.start();
//        } else {
//            animationDrawable.stop();
//            animationDrawable.selectDrawable(0);
//        }
    }

    @Override
    protected void onClick() {
        play();
    }

    @Override
    protected void onLongClick() {

    }

    private void play(){
        aux = (TIMSoundElem) msgBean;
        aux.getSound(new TIMValueCallBack<byte[]>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(byte[] bytes) {
                String deName = "Sh"+aux.getUuid();
                File fileDir = new File(Environment.getExternalStorageDirectory() + "/ShootHzj/Video/");
                if(!fileDir.exists()){
                    fileDir.mkdirs();
                }
                if(fileDir.exists()){
                    File[] files = fileDir.listFiles();
                    for(File f:files){
                        String xx = f.getName();
                        int i = 1;
                        String temp1 = f.getName().substring(0,f.getName().length()-4);
                        String temp2 = deName;
                        String x2x = "111";
                        if(f.getName().substring(0,f.getName().length()-13).equals(deName)){
                            MediaPlayer mediaPlayer = SoundPlayAux.getMediaPlayer();
                            FileInputStream fis = null;
                            try {
                                fis = new FileInputStream(f);
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(fis.getFD());
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                try {
                    File tempMp3 = File.createTempFile(deName,".mp3",fileDir);
                    tempMp3.deleteOnExit();
                    FileOutputStream fos = new FileOutputStream(tempMp3);
                    fos.write(bytes);
                    fos.close();
                    MediaPlayer mediaPlayer = SoundPlayAux.getMediaPlayer();
                    FileInputStream fis = new FileInputStream(tempMp3);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(fis.getFD());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
