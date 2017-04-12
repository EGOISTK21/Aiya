package com.aiyaschool.aiya.message.ui.view;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.BaseRecyclerAdapter;
import com.aiyaschool.aiya.message.BaseRecyclerViewHolder;
import com.aiyaschool.aiya.message.bean.OptionBean;
import com.aiyaschool.aiya.message.interfaces.OnItemClickListener;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.message.ui.activity.PhotosThumbnailActivity;
import com.aiyaschool.aiya.message.utils.ImageUtil;
import com.bumptech.glide.Glide;
import com.tencent.TIMConversation;
import com.tencent.TIMImageElem;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by XZY on 2017/2/26.
 */

public class ChatSelectImageView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = ChatSelectImageView.class.getSimpleName();
    protected View view;
    protected Context context;
    private ScanHandler handler;
    private ArrayList<String> photos = new ArrayList<>();
    private ArrayList<String> selectList = new ArrayList<>();
    private boolean isMulti = true;//是否为多选，显示checkbox
    private PhotosThumbAdapter adapter;
    private Button ivAlbum;
    private Button ivTakePphoto;
    private Button ivSend;
    private Button ivPreview;
    private RecyclerView recyclerView;
    private TIMConversation conversation;

    public ChatSelectImageView(Context context, TIMConversation conversation) {
        this(context,null, conversation);
    }

    public ChatSelectImageView(Context context, AttributeSet attrs,TIMConversation conversation) {
        super(context, attrs);
        this.context = context;
        this.conversation = conversation;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_chat_option_img, this);
        setBackgroundColor(getResources().getColor(R.color.white));
        findById();
        init();
        getImg();
        setListener();
    }

    protected void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter = new PhotosThumbAdapter());
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                adapter.updateSelectData(position);
            }

            @Override
            public boolean OnItemLongClick(int position, View view) {
                return false;
            }
        });
    }

    private void findById() {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        ivAlbum = (Button) findViewById(R.id.iv_album);
        ivTakePphoto = (Button) findViewById(R.id.iv_take_photo);
        ivSend = (Button) findViewById(R.id.iv_send);
        ivPreview = (Button) findViewById(R.id.iv_preview);
    }

    private void setListener() {
        ivAlbum.setOnClickListener(this);
        ivTakePphoto.setOnClickListener(this);
        ivSend.setOnClickListener(this);
        ivPreview.setOnClickListener(this);
    }

    private void getImg() {
        handler = new ScanHandler();
        new Thread(new ScanRunnable(handler)).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_album:
                PhotosThumbnailActivity.startForResult((ChatQQActivity) context, OptionBean.TYPE_PIC);
                break;
            case R.id.iv_take_photo:
                ChatQQActivity.takePhotoPath = ImageUtil.takePic((ChatQQActivity) context, ChatQQActivity.TAKEPHOTO_CODE);
                break;
            case R.id.iv_send:
                for (String path : selectList) {
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
                selectList.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    //遍历扫描图片线程
    class ScanRunnable implements Runnable {
        private ScanHandler handler;

        public ScanRunnable(ScanHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendEmptyMessage(SHOW_PROGRESS);
            try {
                scanPhotos();
            } catch (Exception e) {
                handler.sendEmptyMessage(ERROR);
            }
        }

        //扫描图片
        private void scanPhotos() throws Exception {
            //扫描 缩略图
            ContentResolver cr = context.getContentResolver();

            String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC LIMIT 20";
            Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy);
            if (cursor == null) {
                //发送扫描失败通知
                handler.sendEmptyMessage(ERROR);
                return;
            }
            photos.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (new File(imgPath).exists()) {
                    photos.add(imgPath);
                }
            }
            cursor.close();
            // 通知Handler扫描图片完成
            handler.sendEmptyMessage(FINISH);
        }
    }

    private final int FINISH = 1;
    private final int ERROR = 2;
    private final int SHOW_PROGRESS = 3;

    class ScanHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_PROGRESS:
                    break;
                case FINISH:
                    adapter.notifyDataSetChanged();
                    break;
                case ERROR:

                    break;
            }
        }
    }

    private class PhotosThumbAdapter extends BaseRecyclerAdapter<PhotosThumbAdapter.PhotosThumbViewHolder> {

        public void updateSelectData(int position) {
            if (!selectList.contains(photos.get(position))) {
                selectList.add(photos.get(position));
            } else {
                selectList.remove(photos.get(position));
            }
            notifyItemChanged(position);
        }

        @Override
        public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhotosThumbViewHolder(View.inflate(context, R.layout.view_chat_option_img_item, null));
        }

        @Override
        public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
            PhotosThumbViewHolder viewHolder = (PhotosThumbViewHolder) holder;
            bindOnItemClickListener(viewHolder, position);
            Glide.with(context).load(new File(photos.get(position))).into(viewHolder.img);
            if (isMulti) {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                if (!selectList.contains(photos.get(position))) {
                    viewHolder.checkBox.setChecked(false);
                } else {
                    viewHolder.checkBox.setChecked(true);
                }
            } else {
                viewHolder.checkBox.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            if (photos == null)
                return 0;
            return photos.size();
        }

        class PhotosThumbViewHolder extends BaseRecyclerViewHolder {
            private ImageView img;
            private CheckBox checkBox;

            public PhotosThumbViewHolder(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img_photos_item);
                checkBox = (CheckBox) itemView.findViewById(R.id.ch_photos_item);
            }
        }
    }

}


