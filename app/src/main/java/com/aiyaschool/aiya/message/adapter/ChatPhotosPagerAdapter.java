package com.aiyaschool.aiya.message.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.cache.Cache;
import com.bumptech.glide.Glide;
import com.tencent.TIMImage;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ShootHzj on 2016/11/14.
 */

public class ChatPhotosPagerAdapter extends PagerAdapter{

    private final String TAG = ChatPhotosPagerAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<TIMImage> imgList;
    private HashMap<Integer,View> viewMap;

    public ChatPhotosPagerAdapter(Context context,ArrayList<TIMImage> imgList){
        this.context = context;
        this.imgList = imgList;
        viewMap = new HashMap<>();
    }


    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (viewMap.containsKey(position)) {
            container.removeView(viewMap.get(position));
            viewMap.remove(position);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.view_photo_preview_item,null);
        if(!viewMap.containsKey(position)){
            viewMap.put(position,view);
        }
        handleView(position);
        container.addView(view,0);
        return view;
    }

    private void handleView(int position){
        if(viewMap==null||!viewMap.containsKey(position)){
            return;
        }
        View view = viewMap.get(position);
        ImageView img = (ImageView) view.findViewById(R.id.img_preview);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
        display(position,imgList.get(position),img);
    }

        private void display(int position, final TIMImage i, final ImageView imgView) {
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
}
