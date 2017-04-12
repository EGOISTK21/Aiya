package com.aiyaschool.aiya.message.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;

import com.aiyaschool.aiya.R;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by ShootHzj on 2016/10/20.
 */

public class AudioUtils {
    private static final String TAG = AudioUtils.class.getSimpleName();

    private static Handler playHandler;
    private static MediaPlayer mediaPlayer;
    public static final int START = 2001;
    public static final int END = 2002;
    public static boolean isPlaying = false;

    private boolean isPrepare;
    private String mDir;
    private String mCurrentFilePath;
    private long mCurrentDuration=0l;

    //最大录音时长 1分钟
    private static final int MAX_DURATIONS = 1 * 60 * 1000;
    private static MediaRecorder mRecorder = null;
    private static boolean isRecording = false;
    private static long startTime = 0;
    private static String oldAudioUrl="";

//    private List<String> audioLists; // 未发送的音频集合,发送前合成一个音频文件

    public static void play(Context context, String audioUrl, Handler handler) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            stopMedia();
            if (!oldAudioUrl.equals(audioUrl)){
                oldAudioUrl=audioUrl;
                try {
                    startMedia(context, audioUrl, handler);
                } catch (Exception e) {
                    stopMedia();
                }
            }
        } else {
            try {
                startMedia(context, audioUrl, handler);
            } catch (Exception e) {
                stopMedia();
            }
        }
    }

    private static void startMedia(Context context, String url, final Handler handler) throws Exception {
        playHandler = handler;
        Uri mediaUrl = Uri.parse(url);
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, mediaUrl);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopMedia();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                stopMedia();
                return false;
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
            }
        });
        // 设置是否循环
        mediaPlayer.setLooping(false);
        //        mediaPlayer.prepare();
        mediaPlayer.start();
        sendPlayStatus(START);
        isPlaying=true;
    }

    /**
     * 停止播放
     */
    private static void stopMedia() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            // 重置MediaPlayer到初始状态
            mediaPlayer.release();
        }
        mediaPlayer = null;
        sendPlayStatus(END);
        isPlaying=false;
    }

    private  static void sendPlayStatus(int status) {
        if (playHandler != null) {
            playHandler.sendEmptyMessage(status);
        }
    }


    /**
     * 开始录音
     * 设置录音保存路径
     */
    public void prepareAndStartRecord() {
        try {
            isPrepare = false;
//            if(audioLists == null){
//                audioLists = new ArrayList<>();
//            }
            File dir = new File(Environment.getExternalStorageDirectory() + "/ShootHzj/Audio");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = generateFileName();
            File file = new File(dir, fileName); // 创建音频文件
            mCurrentFilePath = file.getAbsolutePath();
            // 添加音频文件到集合中
//            audioLists.add(mCurrentFilePath);
            startRecord(file.getAbsolutePath().toString());
            // 准备结束
            isPrepare = true;
            if (mAudioStateListener != null) {
                mAudioStateListener.wellPrepared();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 开始录音
     *
     * @param path
     * @throws Exception
     */
    private void startRecord(String path) throws Exception {
        mRecorder = new MediaRecorder();
        mRecorder.setMaxDuration(MAX_DURATIONS);
        mRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mr, int what, int extra) {
                stopRecord();
            }
        });

        mRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
            }
        });
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(path);
        mRecorder.prepare();
        mRecorder.start();
        isRecording = true;
        startTime = SystemClock.elapsedRealtime();
    }

    /**
     * 停止录音
     */
    public long stopRecord() {
        if (isRecording) {
            try {
                mRecorder.stop();
                mRecorder.release();
            } catch (Exception e) {
            } finally {
                mRecorder = null;
                isRecording = false;
            }
            mCurrentDuration = (SystemClock.elapsedRealtime() - startTime)/1000;
            int i = (int) mCurrentDuration;
            return SystemClock.elapsedRealtime() - startTime;
        } else {
            return 0;
        }
    }

    public boolean isRecording() {
        return mRecorder != null && isRecording;
    }

    /**
     * 随机生成文件的名称
     */
    private String generateFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    public int getVoiceLevel(int maxlevel) {
        if (isPrepare) {
            try {
                // mMediaRecorder.getMaxAmplitude() 1~32767
                return maxlevel * mRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
            }
        }
        return 1;
    }

    /**
     * 释放资源
     */
    public void release() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder = null;
        isRecording=false;
    }

    /**
     * 取消录音
     */
    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }

    }
    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }
    public long getCurrentDuration(){
        return mCurrentDuration;
    }
    /**
     * 使用接口 用于回调
     */
    public interface AudioStateListener {
        void wellPrepared();
    }

    public AudioStateListener mAudioStateListener;

    /**
     * 回调方法
     */
    public void setOnAudioStateListener(AudioStateListener listener) {
        mAudioStateListener = listener;
    }

    public interface PlayStateListener {
        void start();
        void stop();
    }

    public PlayStateListener mPlayStateListener;

    /**
     * 回调方法
     */
    public void setOnPlayStateListener(PlayStateListener listener) {
        mPlayStateListener = listener;
    }

    /**
     * 合并ＡＭＲ文件
     * 输出的是amr格式。amr的音频文件的文件头，相对来说是固定的6个字节的固定字符，
     * A.amr文件和B.amr文件的合并，只需将B以字节流读取，去掉前6个字节
     */
//    public File mergeAMRFile(boolean isSend){
//        if(!isSend){
//            audioLists = null;
//            return null;
//        }
//        // 合成
//        File targetAudioFile = null;
//        if(audioLists != null && audioLists.size() != 0){
//            List<String> tempLists = new ArrayList<>();
//            //　清除不符合的文件
//            for(String filePath : audioLists){
//                File tempFile = new File(filePath);
//                if(!tempFile.exists() || filePath.endsWith(".amr")){
//                    tempLists.add(filePath);
//                }
//            }
//            audioLists.removeAll(tempLists);
//            if(audioLists.size() != 0){
//                // 创建音频文件,合并的文件放这里
//                targetAudioFile = new File(ConfigApi.getCachePath(), "添加方法获取年月日时分秒");
//                FileOutputStream fileOutputStream = null;
//                if(targetAudioFile.exists()){
//                    targetAudioFile.delete();
//                }
//                try {
//                    targetAudioFile.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    fileOutputStream=new FileOutputStream(targetAudioFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //list里面为暂停录音 所产生的 几段录音文件的名字，第一段文件以后的文件都需要的减去前面的6个字节头文件
//                for(int i=0;i<audioLists.size();i++) {
//                    File tempFile = new File(audioLists.get(i));
//                    try {
//                        FileInputStream fileInputStream = new FileInputStream(tempFile);
//                        byte[] myByte = new byte[fileInputStream.available()];
//                        //文件长度
//                        int length = myByte.length;
//                        //头文件
//                        if (i == 0) {
//                            while (fileInputStream.read(myByte) != -1) {
//                                fileOutputStream.write(myByte, 0, length);
//                            }
//                        } else {//之后的文件，去掉头文件就可以了
//                            while (fileInputStream.read(myByte) != -1) {
//                                fileOutputStream.write(myByte, 6, length - 6); // 去掉文件前６个字节
//                            }
//                        }
//                        fileOutputStream.flush();
//                        fileInputStream.close();
//                        System.out.println("合成文件长度：" + targetAudioFile.length());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    //结束后关闭流
//                    try {
//                        fileOutputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }else{
//            System.out.println("音频录制内部错误");
//            }
//        }else{
//            System.out.println("音频录制内部错误");
//        }
//        audioLists = null;
//        return targetAudioFile;
//    }

    private void deleteAudioLists(){

    }

    public void clearAudioFile(){
        File file = new File(this.getCurrentFilePath());
        if(file.exists()){
            file.delete();
        }
    }

    // 播放录音时的提示音
    private static HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    private static SoundPool soundPool;
    public static void prepareTipsAudio(Context context){
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        hashMap.put(1, soundPool.load(context, R.raw.di, 1));
    }

    public static void playTipsAudio(){
        if(soundPool == null || hashMap.size() == 0){
            throw  new UnsupportedOperationException("相关变量未初始化");
        }
        soundPool.play(hashMap.get(1), 1, 1, 0, 0, 1);
    }
}
