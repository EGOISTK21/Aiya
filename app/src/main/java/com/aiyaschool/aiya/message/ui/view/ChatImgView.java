package com.aiyaschool.aiya.message.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.cache.Cache;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.message.utils.Utils;
import com.bumptech.glide.Glide;
import com.tencent.TIMElem;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;

/**
 * Created by ShootHzj on 2016/11/1.
 */

public class ChatImgView extends ChatMsgItemView{

    private ImageView imgView;
    private TIMImageElem msgImage;
    private RelativeLayout sendInfolayout;
    private TIMImage i;
    public ChatImgView(Context context, TIMElem messageBean) {
        super(context, messageBean);

    }

    @Override
    protected void loadView() {
        View view = View.inflate(context, R.layout.view_chat_img,this);
        imgView = (ImageView) view.findViewById(R.id.img_chat_image);
        sendInfolayout = (RelativeLayout) view.findViewById(R.id.rl_send_info);
    }

    @Override
    protected void handleData() {

    }

    @Override
    protected void display() {

        final ChatQQActivity sh = (ChatQQActivity) context;
        msgImage = (TIMImageElem) msgBean;
        ArrayList<TIMImage> shoot = msgImage.getImageList();
        if(shoot.size()<1) return;
        i = shoot.get(0);
        setViewParam();
            if(Cache.getMemoryImageCache().get(i.getUuid())!=null){
                Glide.with(context).load(Cache.getMemoryImageCache().get(i.getUuid())).into(imgView);
            }else{
                i.getImage(new TIMValueCallBack<byte[]>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(byte[] bytes) {
                        Cache.getMemoryImageCache().put(i.getUuid(),bytes);
                        Glide.with(context).load(bytes).into(imgView);
                    }
                });

        }
    }

    private void setViewParam(){
        int height = (int) i.getHeight();
        int width = (int) i.getWidth();
        if(Math.min(width,height)<=0){
            return;
        }
        int max = Utils.dip2px(context,120);
        if (Math.max(width, height) > max) {
            if (height > max) {
                width = width * max / height;
                height = max;
            } else {
                height = height * max / width;
                width = max;
            }
        }
        ViewGroup.LayoutParams params = imgView.getLayoutParams();
        params.width = width;
        params.height = height;
        imgView.setLayoutParams(params);

        // 设置前景布局宽高
        sendInfolayout.setLayoutParams(params);
    }

    @Override
    protected void onClick() {
        activity.startPhotoPreview(msgImage);
    }

    @Override
    protected void onLongClick() {

    }
}
