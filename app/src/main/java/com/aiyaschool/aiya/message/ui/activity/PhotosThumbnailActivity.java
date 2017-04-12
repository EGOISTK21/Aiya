package com.aiyaschool.aiya.message.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.adapter.PhotosThumbAdapter;
import com.aiyaschool.aiya.message.bean.FileBean;
import com.aiyaschool.aiya.message.interfaces.OnItemClickListener;
import com.aiyaschool.aiya.message.utils.DialogUtil;
import com.aiyaschool.aiya.message.utils.ImageUtil;
import com.aiyaschool.aiya.message.utils.SDKUtils;
import com.aiyaschool.aiya.message.utils.Utils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

//import static com.tencent.qalsdk.base.a.R;

public class PhotosThumbnailActivity extends AppCompatActivity {

    private final String TAG = PhotosThumbnailActivity.class.getSimpleName();
    private static final String KEY_MULTI = "multi";

    private RecyclerView recyclerView;
    private PhotosThumbAdapter adapter;

    //所有图片文件夹
    private ArrayList<FileBean> imgFolders = new ArrayList<>();
    //当前预览目录
    private String curDir;
    //当前目录下的所有图片
    private ArrayList<String> photos = new ArrayList<>();
    private ScanHandler handler;
    private ListPopupWindow dirPop;
    private TextView tvDir,tvSend;

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PhotosThumbnailActivity.class);
        activity.startActivity(intent);
    }

    public static void startForResult(Activity activity, int requestCode, boolean isMulti) {
        Intent intent = new Intent();
        intent.setClass(activity, PhotosThumbnailActivity.class);
        intent.putExtra(KEY_MULTI, isMulti);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startForResult(Activity activity, int requestCode) {
        startForResult(activity, requestCode, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_thumbnail);
        handler = new ScanHandler();
        new Thread(new ScanRunnable(handler)).start();
        recyclerView = (RecyclerView) findViewById(R.id.rc_thumb);
        tvDir = (TextView) findViewById(R.id.tv_thumb_dir);
        tvSend = (TextView) findViewById(R.id.tv_thumb_send);
        tvDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDirPopWind(v);
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("data", adapter.getSelectList());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PhotosThumbAdapter(this, curDir, photos, true);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                if (position == 0) {
                     takePhotoPath = ImageUtil.takePic( PhotosThumbnailActivity.this, TAKEPHOTO_CODE);
                } else {
                    adapter.updateSelectData(position);
                }
            }

            @Override
            public boolean OnItemLongClick(int position, View view) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }
    @Override
    protected void onStop() {
        if (dirPop != null) {
            dirPop.dismiss();
            dirPop = null;
        }
        super.onStop();
    }

    private void initDirPopWind(View v) {
        if (dirPop == null) {
            dirPop = new ListPopupWindow(PhotosThumbnailActivity.this);
            dirPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            dirPop.setHeight(Utils.dip2px(PhotosThumbnailActivity.this, 400));
            dirPop.setAdapter(new DirAdapter(PhotosThumbnailActivity.this, imgFolders));

            dirPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dirPop.dismiss();
                    curDir = imgFolders.get(position).getDir();
                    updatePhoto();
                    setSelectView(imgFolders.get(position));
                }
            });
        }
        dirPop.setAnchorView(v);
        dirPop.show();
    }

    private final int TAKEPHOTO_CODE = 1;
    private final int PREVIEW_CODE = 2;
    private final int CROP_CODE = 3;
    private String takePhotoPath = "";

    private void updatePhoto() {
        photos.clear();
        File file = new File(curDir);
        if (file.exists()) {
            List<File> newfiles = SortFiles(file);
            for (File file1 : newfiles) {
                String filename = file1.getName();
                if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg")) {
                    photos.add(filename);
                }
            }
            adapter.updatePhoto(curDir, photos);
        }
    }

    private void setSelectView(FileBean imgFolder) {
        tvDir.setText(imgFolder.getName());
    }
    private List<File> SortFiles(File dir) {
        List<File> newfiles = Arrays.asList(dir.listFiles());
        Collections.sort(newfiles, new Comparator<File>() {
            @Override
            public int compare(File lhs, File rhs) {
                if (lhs.lastModified() > rhs.lastModified()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return newfiles;
    }

    private String[] filterFile(File dir) {
        return dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg");
            }
        });
    }

    private void setActivityResult(ArrayList<String> paths) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("data", paths);
        setResult(RESULT_OK, intent);
        finish();
    }

    class ScanRunnable implements Runnable {
        private ScanHandler handler;

        public ScanRunnable(ScanHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendEmptyMessage(SHOW_PROGRESS);//显示进度条
            try {
                scanPhotos();
            } catch (Exception e) {
                handler.sendEmptyMessage(ERROR);
            }
        }

        //扫描图片
        private void scanPhotos() throws Exception {
            // 临时的辅助哈希表，用于防止同一个文件夹的多次扫描
            HashSet<String> dirHash = new HashSet<>();
            //扫描 缩略图
            ContentResolver cr = PhotosThumbnailActivity.this.getContentResolver();

            String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            // Add xa zxj 2015/12/23 增加 orderby 按 最后修改时间降序排序
            String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
            Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy);
            if (cursor == null) {
                //发送扫描失败通知
                handler.sendEmptyMessage(ERROR);
                return;
            }
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String imgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                File parentFile = new File(imgPath).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                String dirPath = parentFile.getAbsolutePath();
                // 利用一个HashSet防止多次扫描同一个文件夹
                if (dirHash.contains(dirPath)) {
                    continue;
                }
                dirHash.add(dirPath);
                String[] files = filterFile(parentFile);
                if (files == null || files.length == 0) {
                    continue;
                }
                FileBean imgFile = new FileBean();
                imgFile.setDir(dirPath);//绝对路径
                imgFile.setCount(files.length);
                imgFolders.add(imgFile);//路径和总数加入到了imgFoilder里
            }
            cursor.close();
            handler.sendEmptyMessage(FINISH);
        }
    }

    private final int FINISH = 1;
    private final int ERROR = 2;
    private final int SHOW_PROGRESS = 3;
    private MaterialDialog progressDialog;

    class ScanHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            switch (msg.what) {
                case SHOW_PROGRESS:
                    if (progressDialog == null) {
                        progressDialog = DialogUtil.buildProgressDialog(PhotosThumbnailActivity.this);
                    }
                    progressDialog.show();
                    break;
                case FINISH:
                    if (imgFolders != null && imgFolders.size() > 0) {
                        curDir = imgFolders.get(0).getDir();
                    }
                    updatePhoto();//更新图片
                    break;
                case ERROR:

                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKEPHOTO_CODE && resultCode == RESULT_OK) {
            ArrayList<String> paths = new ArrayList<>();
            paths.add(takePhotoPath);
            setActivityResult(paths);
        } else if (requestCode == PREVIEW_CODE && resultCode == RESULT_OK) {
            setActivityResult(adapter.getSelectList());
        } else if (requestCode == CROP_CODE && resultCode == RESULT_OK) {//裁剪图片返回裁剪路径
            ArrayList<String> paths = new ArrayList<>();
            paths.add(data.getStringExtra("data"));
            setActivityResult(paths);
        }
    }

    class DirAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<FileBean> dirs;
        private final String ROOTPATH = SDKUtils.getSDPath();

        public DirAdapter(Context context, ArrayList<FileBean> dirs) {
            this.context = context;
            this.dirs = dirs;
        }

        @Override
        public int getCount() {
            return dirs.size();
        }

        @Override
        public Object getItem(int position) {
            return dirs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.view_photo_dir_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            FileBean fileBean = dirs.get(position);
            Glide.with(context).load(new File(fileBean.getDir() + File.separator + fileBean.getFirstPath())).into(viewHolder.img);
            viewHolder.tvCount.setText(fileBean.getCount() + "");
            if (fileBean.getDir().contains(ROOTPATH)) {
                viewHolder.tvName.setText(fileBean.getDir().replace(ROOTPATH, ""));
            } else {
                viewHolder.tvName.setText(fileBean.getDir());
            }
            if (curDir.equals(fileBean.getDir())) {
                viewHolder.imgStatus.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imgStatus.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        class ViewHolder {
            private ImageView img;
            private TextView tvName;
            private TextView tvCount;
            private ImageView imgStatus;

            public ViewHolder(View itemView) {
                img = (ImageView) itemView.findViewById(R.id.img_item_icon);
                tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
                tvName.setEllipsize(TextUtils.TruncateAt.START);
                tvCount = (TextView) itemView.findViewById(R.id.tv_item_count);
                imgStatus = (ImageView) itemView.findViewById(R.id.img_item_status);
            }
        }
    }
}
