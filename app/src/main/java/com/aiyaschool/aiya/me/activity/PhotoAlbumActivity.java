package com.aiyaschool.aiya.me.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.Img;
import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.me.mvpphotoAlbum.PhotoAlbumContract;
import com.aiyaschool.aiya.me.mvpphotoAlbum.PhotoAlbumPresenter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

public class PhotoAlbumActivity extends AppCompatActivity implements PhotoAlbumContract.View {

    private final static String TAG = "PhotoAlbumActivity";

    //今天的时间
    private String mDateNow;


    private TextView mTvBack, mTvPhotoEdit;

    //是否在编辑
    private boolean isEdit;

    private RecyclerView mRvPhotoAlbum;
    private RvAlbumAdapter rvAlbumAdapter;
    private RvAlbumAdapter.GridViewAdapter gridViewAdapter;

    private List<ImagePath> mImagePathList;

    //上传图片的URL
    private List<UploadUrl> mUploadUrlList;

    //+号选择图片时，本地图片的路径
    private ArrayList<String> mSelectPath;

    //保存被选中删除的图片的编号
    private List<List<Integer>> mSelectedNumber;

    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;


    private PhotoAlbumContract.Presenter mPhotoAlbumPresenter;
    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);
        initView();
        initData();
    }

    private void initData() {
        mUploadUrlList = new ArrayList<>();

        //初始化现在时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mDateNow = df.format(new Date());

        //初始化mImagePathList
        mImagePathList = new ArrayList<>();
        ImagePath imagepath = new ImagePath();
        imagepath.setCreateTime(mDateNow);
        ArrayList<Gallery> galleryList = new ArrayList<>();
        Gallery gallery = new Gallery();
        Img img = new Img();
        img.setThumb("R.drawable");
        gallery.setImg(img);
        galleryList.add(gallery);
        imagepath.setGalleryList(galleryList);
        mImagePathList.add(imagepath);

        //初始化mSelectedNumber；
        mSelectedNumber = new ArrayList<>();

        mPhotoAlbumPresenter = new PhotoAlbumPresenter(this);
        mPhotoAlbumPresenter.getImgUploadUrl();
        mPhotoAlbumPresenter.getMePhoto("1", "10");

    }

    private void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvPhotoEdit = (TextView) findViewById(R.id.tv_photo_edit);
        mRvPhotoAlbum = (RecyclerView) findViewById(R.id.rv_photo_album);
        rvAlbumAdapter = new RvAlbumAdapter();
        mRvPhotoAlbum.setAdapter(rvAlbumAdapter);
        mRvPhotoAlbum.setLayoutManager(new LinearLayoutManager(this));
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("flag", "me");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mTvPhotoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void showImgUploadUrl(List<UploadUrl> uploadUrlList) {
        mUploadUrlList = uploadUrlList;
    }

    //加载从后台取到的url链接
    @Override
    public void showGetMePhoto(ArrayList<Gallery> GalleryList) {
        if (GalleryList.size() > 0) {
            for (Gallery gallery : GalleryList) {
                boolean isHasImg = false;
                for (ImagePath mImagePath : mImagePathList) {
                    if (mImagePath.getCreateTime().equals(parseDate(gallery.getCreatetime()))) {
                        mImagePath.getGalleryList().add(0, gallery);
                        isHasImg = true;
                        break;
                    }
                }
                if (!isHasImg) {
                    ImagePath imagePath = new ImagePath();
                    imagePath.setCreateTime(parseDate(gallery.getCreatetime()));
                    ArrayList<Gallery> galleryList = new ArrayList<>();
                    galleryList.add(gallery);
                    imagePath.setGalleryList(galleryList);
                    mImagePathList.add(imagePath);
                }
            }

        }
    }

    public String parseDate(String date) {
        String timeString = date + "000";
        long timeLong = Long.valueOf(timeString);
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeLong));
    }


    @Override
    public void startPostPhotoImg() {
        mPhotoAlbumPresenter.startPostPhotoImg(mUploadUrlList.get(i).getImgname());
        i++;
    }


    class RvAlbumAdapter extends RecyclerView.Adapter<RvAlbumAdapter.AlbumViewHolder> {

        @Override
        public RvAlbumAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(PhotoAlbumActivity.this).inflate(R.layout.photo_album_item, parent, false);
            AlbumViewHolder albumViewHolder = new AlbumViewHolder(view);
            return albumViewHolder;
        }

        @Override
        public void onBindViewHolder(RvAlbumAdapter.AlbumViewHolder holder, final int position) {
            holder.mTvDay.setText(mImagePathList.get(position).getCreateTime());
            gridViewAdapter = new GridViewAdapter(PhotoAlbumActivity.this, R.layout.grid_view_item, mImagePathList.get(position).getGalleryList());
            holder.mGvPhoto.setAdapter(gridViewAdapter);
            holder.mGvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                    if (position == 0 &&
                            position1 == mImagePathList.get(position).getGalleryList().size() - 1) {
                        //此时增加tupian
                        String choiceMode = "multi";
                        pickImage(choiceMode);
                    } else {
                        //如果isEdit是true，checkmark选中   如果isEdit是false 此时查看大图
                        ImageView checkmark = (ImageView) view.findViewById(R.id.checkmark);
                        if (isEdit) {
                            if (mSelectedNumber.get(position1).contains(position)) {
                                checkmark.setImageResource(R.drawable.mis_btn_unselected);
                                mSelectedNumber.get(position1).remove(position);
                            } else {
                                checkmark.setImageResource(R.drawable.mis_btn_selected);
                                mSelectedNumber.get(position1).add(position);
                            }
                        } else {

                        }
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return mImagePathList.size();
        }


        class AlbumViewHolder extends RecyclerView.ViewHolder {

            private TextView mTvDay;
            private GridView mGvPhoto;

            public AlbumViewHolder(View itemView) {
                super(itemView);
                mTvDay = (TextView) itemView.findViewById(R.id.tv_day);
                mGvPhoto = (GridView) itemView.findViewById(R.id.grid_photo_album);

            }
        }

        class GridViewAdapter extends ArrayAdapter<String> {
            private Context mContext;
            private int layoutResourceId;
            private List<Gallery> mGridData = new ArrayList<>();


            public GridViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Gallery> objects) {
                super(context, resource);
                this.mContext = context;
                this.layoutResourceId = resource;
                this.mGridData = objects;

            }


            @Override
            public int getCount() {
                if (mGridData.size() > 9) {
                    return 9;
                } else {
                    return mGridData.size();
                }

            }


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                RvAlbumAdapter.GridViewAdapter.ViewHolder holder;

                if (convertView == null) {
                    LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                    convertView = inflater.inflate(layoutResourceId, parent, false);
                    holder = new RvAlbumAdapter.GridViewAdapter.ViewHolder();
                    holder.imageView = (ImageView) convertView.findViewById(R.id.imgview_item);
                    holder.checkmark = (ImageView) convertView.findViewById(R.id.checkmark);
                    convertView.setTag(holder);
                } else {
                    holder = (RvAlbumAdapter.GridViewAdapter.ViewHolder) convertView.getTag();
                }
                String imgThumb = mGridData.get(position).getImg().getThumb();
                if (!TextUtils.isEmpty(mGridData.get(position).getImgid())) {
                    // 显示图片
                    Picasso.with(mContext)
                            .load(imgThumb)
                            .placeholder(R.drawable.mis_default_error)
                            .tag(MultiImageSelectorFragment.TAG)
                            .resize(226, 226)
                            .centerCrop()
                            .into(holder.imageView);
                    if (isEdit) {
                        holder.checkmark.setVisibility(View.VISIBLE);
                    }
                } else if (!imgThumb.equals("R.drawable")) {
                    // 显示图片
                    Picasso.with(mContext)
                            .load(new File(imgThumb))
                            .placeholder(R.drawable.mis_default_error)
                            .tag(MultiImageSelectorFragment.TAG)
                            .resize(226, 226)
                            .centerCrop()
                            .into(holder.imageView);
                    if (isEdit) {
                        holder.checkmark.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.imageView.setImageResource(R.drawable.uploadpic_226x226);
                }

                return convertView;
            }

            private class ViewHolder {
                ImageView imageView;
                ImageView checkmark;
            }


        }
    }

    private void pickImage(String choiceMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            boolean showCamera = false;
            int maxNum = 9;

            MultiImageSelector selector = MultiImageSelector.create(PhotoAlbumActivity.this);
            selector.showCamera(showCamera);
            selector.count(maxNum);
            if (choiceMode.equals("single")) {
                selector.single();
            } else {
                selector.multi();
            }
            selector.origin(mSelectPath);
            selector.start(PhotoAlbumActivity.this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(PhotoAlbumActivity.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String choiceMode = "multi";
                pickImage(choiceMode);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (mImagePathList.get(0).getGalleryList().size() == 10) {
                    return;
                } else {
                    for (String p : mSelectPath) {
                        if (mImagePathList.size() == 10) {
                            break;
                        }
                        Gallery gallery = new Gallery();
                        Img img = new Img();
                        img.setThumb(p);
                        gallery.setImg(img);
                        mImagePathList.get(0).getGalleryList().add(0, gallery);
                        if (mUploadUrlList.size() != 0) {
                            i = 0;
                            mPhotoAlbumPresenter.submitAvatar(mUploadUrlList.get(i).getUpurl(), new File(p));
                        }

                    }
                    rvAlbumAdapter.notifyDataSetChanged();
                }

            }
        }
    }

    class ImagePath {
        private String createTime;
        private List<Gallery> GalleryList;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<Gallery> getGalleryList() {
            return GalleryList;
        }

        public void setGalleryList(List<Gallery> galleryList) {
            GalleryList = galleryList;
        }
    }
}
