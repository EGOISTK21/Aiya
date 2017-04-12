package com.aiyaschool.aiya.message.singleton;

import android.media.MediaPlayer;

/**
 * Created by ShootHzj on 2016/11/11.
 */

public class SoundPlayAux {
    public static MediaPlayer mediaPlayer;
    public static MediaPlayer getMediaPlayer(){
        if(mediaPlayer==null){
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }
}
