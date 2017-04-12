package com.aiyaschool.aiya.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ShootHzj on 2016/11/24.
 */

public class PhotoChooseAdapter extends RecyclerView.Adapter<PhotoChooseAdapter.ViewHolder>{

    private Context context;
    private String curDir;
    private ArrayList<String> photos = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhotoChooseAdapter(Context context, String curDir, ArrayList<String> photos) {
        this.context =  context;
        updatePhoto(curDir,photos);
    }

    public void updatePhoto(String curDir,ArrayList<String> photos){
        this.curDir = curDir;
        this.photos.clear();
        this.photos.add(0, "takePhotos");
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(context, R.layout.photos_grid_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewHolder viewHolder = holder;
        if(onItemClickListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(position,null,curDir+"/"+photos.get(position));
                }
            });
            viewHolder.itemView.setLongClickable(true);
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
        if (position == 0) {
            viewHolder.img.setImageResource(R.mipmap.icon_takephotos);
        } else {
            Glide.with(context).load(new File(curDir + File.separator + photos.get(position))).into(viewHolder.img);
        }
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img_photos_item);
        }
    }

    public interface OnItemClickListener {

        void OnItemClick(int position, View view, String path);

        boolean  OnItemLongClick(int position, View view);
    }
}
