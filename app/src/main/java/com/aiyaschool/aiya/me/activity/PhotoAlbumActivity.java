package com.aiyaschool.aiya.me.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.Img;
import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.me.mvpphotoAlbum.PhotoAlbumContract;
import com.aiyaschool.aiya.me.mvpphotoAlbum.PhotoAlbumPresenter;
import com.aiyaschool.aiya.widget.StringScrollPicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class PhotoAlbumActivity extends AppCompatActivity implements PhotoAlbumContract.View {

    private final static String TAG = "PhotoAlbumActivity";

    //今天的时间
    private String mDateNow;

    //是否在编辑
    private boolean isEdit;

    //是否从相册取了图片
    private boolean isFromFile;

    private TextView mTvBack, mTvPhotoEdit;
    private ImageView mExpandImageView;
    private RecyclerView mRvPhotoAlbum;
    private RvAlbumAdapter rvAlbumAdapter;
    private RvAlbumAdapter.GridViewAdapter gridViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;

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
    private int i, j;

    private String imgname = "";

    private int mPage = 0;
    private int lastVisibleItem;
    private boolean isNodata;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

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

        //初始化mSelectedNumber；
        mSelectedNumber = new ArrayList<>();

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
        mSelectedNumber.add(new ArrayList<Integer>());


        mPhotoAlbumPresenter = new PhotoAlbumPresenter(this);
        mPhotoAlbumPresenter.getImgUploadUrl();
        mPhotoAlbumPresenter.getMePhoto(Integer.toString(mPage++), "10");

        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    private void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mTvPhotoEdit = (TextView) findViewById(R.id.tv_photo_edit);
        mExpandImageView = (ImageView) findViewById(R.id.expanded_image);
        mRvPhotoAlbum = (RecyclerView) findViewById(R.id.rv_photo_album);
        rvAlbumAdapter = new RvAlbumAdapter();
        mRvPhotoAlbum.setAdapter(rvAlbumAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvPhotoAlbum.setLayoutManager(mLinearLayoutManager);
        mRvPhotoAlbum.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (rvAlbumAdapter != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == rvAlbumAdapter.getItemCount()) {
                        if (isNodata) {
                            Toast.makeText(PhotoAlbumActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        } else {
                            mPhotoAlbumPresenter.getMePhoto(Integer.toString(mPage++), "10");
                        }
                    }
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });


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
                if (isEdit == false) {
                    if (isFromFile) {
                        mPhotoAlbumPresenter.updateImagePathList("1", "9");
                        isFromFile = false;
                    }

                    clearSelected();
                    isEdit = true;
                    mTvPhotoEdit.setText("删除");
                } else {
                    String deleteImg = "";
                    for (int i = 0; i < mSelectedNumber.size(); i++) {
                        for (int j = 0; j < mSelectedNumber.get(i).size(); j++) {
                            int position = mSelectedNumber.get(i).get(j);
                            deleteImg += mImagePathList.get(i).getGalleryList().get(position).getImgid() + ",";
                            mImagePathList.get(i).getGalleryList().remove(position);
                            mImagePathList.get(i).getGalleryList().add(position, new Gallery("sss"));
                        }
                    }

                    for (int i = 0; i < mImagePathList.size(); i++) {
                        Iterator<Gallery> it = mImagePathList.get(i).GalleryList.iterator();
                        while (it.hasNext()) {
                            Gallery g = it.next();
                            if (!TextUtils.isEmpty(g.getCreatetime())) {
                                if (g.getCreatetime().equals("sss")) {
                                    it.remove();
                                }
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(deleteImg)) {
                        int length = deleteImg.length();
                        mPhotoAlbumPresenter.deletePhoto(deleteImg.substring(0, length - 1));
                    }
                    clearSelected();
                    isEdit = false;
                    mTvPhotoEdit.setText("编辑");
                }

                rvAlbumAdapter.notifyDataSetChanged();
            }
        });


    }


    private void clearSelected() {
        for (int i = 0; i < mSelectedNumber.size(); i++) {
            mSelectedNumber.get(i).clear();
        }
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            isEdit = false;
            mTvPhotoEdit.setText("编辑");
            clearSelected();
            rvAlbumAdapter.notifyDataSetChanged();
            return;
        }
        finish();
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
                    mSelectedNumber.add(new ArrayList<Integer>());
                }

            }
            rvAlbumAdapter.notifyDataSetChanged();

        } else {
            isNodata = true;
        }
    }

    public String parseDate(String date) {
        String timeString = date + "000";
        long timeLong = Long.valueOf(timeString);
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeLong));
    }


    @Override
    public void startPostPhotoImg() {
        j++;
        if (j == i) {
            int length = imgname.length();
            Log.d(TAG, "startPostPhotoImg: " + imgname);
            mPhotoAlbumPresenter.startPostPhotoImg(imgname.substring(0, length - 1));
            imgname = "";
            j = 0;
            i = 0;
        }

    }

    @Override
    public void updateImagePathList(ArrayList<Gallery> mGalleryList) {
        mImagePathList.get(0).getGalleryList().clear();
        for (Gallery gallery : mGalleryList) {
            mImagePathList.get(0).getCreateTime().equals(parseDate(gallery.getCreatetime()));
            mImagePathList.get(0).getGalleryList().add(0, gallery);
        }
        Gallery gallery = new Gallery();
        Img img = new Img();
        img.setThumb("R.drawable");
        gallery.setImg(img);
        mImagePathList.get(0).getGalleryList().add(gallery);
        rvAlbumAdapter.notifyDataSetChanged();
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
                        clearSelected();
                        isEdit = false;
                        mPhotoAlbumPresenter.getImgUploadUrl();
                        String choiceMode = "multi";
                        pickImage(choiceMode);

                    } else {
                        //如果isEdit是true，checkmark选中   如果isEdit是false 此时查看大图
                        ImageView checkmark = (ImageView) view.findViewById(R.id.checkmark);
                        if (isEdit) {
                            if (mSelectedNumber.get(position).contains(position1)) {
                                checkmark.setImageResource(R.drawable.mis_btn_unselected);
                                mSelectedNumber.get(position).remove(position1);
                            } else {
                                checkmark.setImageResource(R.drawable.mis_btn_selected);
                                mSelectedNumber.get(position).add(position1);
                            }
                        } else {
                            // 此时查看大图
                            List<Gallery> galleryList = mImagePathList.get(position).getGalleryList();
                            if (position == 0) {
                                int size = galleryList.size();
                                galleryList.remove(size - 1);
                            }
                            int childCount = parent.getChildCount();
                            ArrayList<Rect> rects = new ArrayList<>();
                            for (int i = 0; i < childCount; i++) {
                                Rect rect = new Rect();
                                View child = parent.getChildAt(i);
                                child.getGlobalVisibleRect(rect);
                                rects.add(rect);
                            }
                            Intent intent = new Intent(PhotoAlbumActivity.this, PhotoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("gallery", (Serializable) galleryList);
                            intent.putExtras(bundle);
                            intent.putExtra("index", position1);
                            intent.putExtra("bounds", rects);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
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

    //查看大图的动画
//    private void zoomImageFromThumb(final View thumbView, Gallery g) {
//        // If there's an animation in progress, cancel it
//        // immediately and proceed with this one.
//        if (mCurrentAnimator != null) {
//            mCurrentAnimator.cancel();
//        }
//
//        // Load the high-resolution "zoomed-in" image.
//        if (!TextUtils.isEmpty(g.getImgid())) {
//            Picasso.with(PhotoAlbumActivity.this)
//                    .load(g.getImg().getNormal())
//                    .placeholder(R.drawable.mis_default_error)
//                    .into(mExpandImageView);
//        } else {
//            File file = new File(g.getImg().getThumb());
//            Picasso.with(PhotoAlbumActivity.this)
//                    .load(file)
//                    .placeholder(R.drawable.mis_default_error)
//                    .into(mExpandImageView);
//        }
//
//
//        // Calculate the starting and ending bounds for the zoomed-in image.
//        // This step involves lots of math. Yay, math.
//        final Rect startBounds = new Rect();
//        final Rect finalBounds = new Rect();
//        final Point globalOffset = new Point();
//
//        // The start bounds are the global visible rectangle of the thumbnail,
//        // and the final bounds are the global visible rectangle of the container
//        // view. Also set the container view's offset as the origin for the
//        // bounds, since that's the origin for the positioning animation
//        // properties (X, Y).
//        thumbView.getGlobalVisibleRect(startBounds);
//        findViewById(R.id.container)
//                .getGlobalVisibleRect(finalBounds, globalOffset);
//        startBounds.offset(-globalOffset.x, -globalOffset.y);
//        finalBounds.offset(-globalOffset.x, -globalOffset.y);
//
//        // Adjust the start bounds to be the same aspect ratio as the final
//        // bounds using the "center crop" technique. This prevents undesirable
//        // stretching during the animation. Also calculate the start scaling
//        // factor (the end scaling factor is always 1.0).
//        float startScale;
//        if ((float) finalBounds.width() / finalBounds.height()
//                > (float) startBounds.width() / startBounds.height()) {
//            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
//        } else {
//            // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height();
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.bottom += deltaHeight;
//        }
//
//        // Hide the thumbnail and show the zoomed-in view. When the animation
//        // begins, it will position the zoomed-in view in the place of the
//        // thumbnail.
//        thumbView.setAlpha(0f);
//        mExpandImageView.setVisibility(View.VISIBLE);
//
//        // Set the pivot point for SCALE_X and SCALE_Y transformations
//        // to the top-left corner of the zoomed-in view (the default
//        // is the center of the view).
//        mExpandImageView.setPivotX(0f);
//        mExpandImageView.setPivotY(0f);
//
//        // Construct and run the parallel animation of the four translation and
//        // scale properties (X, Y, SCALE_X, and SCALE_Y).
//        AnimatorSet set = new AnimatorSet();
//        set.play(ObjectAnimator.ofFloat(mExpandImageView, View.X,
//                startBounds.left, finalBounds.left))
//                .with(ObjectAnimator.ofFloat(mExpandImageView, View.Y,
//                        startBounds.top, finalBounds.top))
//                .with(ObjectAnimator.ofFloat(mExpandImageView, View.SCALE_X,
//                        startScale, 1f)).with(ObjectAnimator.ofFloat(mExpandImageView,
//                View.SCALE_Y, startScale, 1f));
//        set.setDuration(mShortAnimationDuration);
//        set.setInterpolator(new DecelerateInterpolator());
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mCurrentAnimator = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                mCurrentAnimator = null;
//            }
//        });
//        set.start();
//        mCurrentAnimator = set;
//
//        // Upon clicking the zoomed-in image, it should zoom back down
//        // to the original bounds and show the thumbnail instead of
//        // the expanded image.
//        final float startScaleFinal = startScale;
//        mExpandImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurrentAnimator != null) {
//                    mCurrentAnimator.cancel();
//                }
//
//                // Animate the four positioning/sizing properties in parallel,
//                // back to their original values.
//                AnimatorSet set = new AnimatorSet();
//                set.play(ObjectAnimator
//                        .ofFloat(mExpandImageView, View.X, startBounds.left))
//                        .with(ObjectAnimator
//                                .ofFloat(mExpandImageView,
//                                        View.Y, startBounds.top))
//                        .with(ObjectAnimator
//                                .ofFloat(mExpandImageView,
//                                        View.SCALE_X, startScaleFinal))
//                        .with(ObjectAnimator
//                                .ofFloat(mExpandImageView,
//                                        View.SCALE_Y, startScaleFinal));
//                set.setDuration(mShortAnimationDuration);
//                set.setInterpolator(new DecelerateInterpolator());
//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        mExpandImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        mExpandImageView.setVisibility(View.GONE);
//                        mCurrentAnimator = null;
//                    }
//                });
//                set.start();
//                mCurrentAnimator = set;
//            }
//        });
//    }

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
                    isFromFile = true;
                    for (String p : mSelectPath) {
                        if (mImagePathList.size() == 10) {
                            break;
                        }
                        Gallery gallery = new Gallery();
                        Img img = new Img();
                        img.setThumb(p);
                        gallery.setImg(img);
                        int length = mImagePathList.get(0).getGalleryList().size();
                        mImagePathList.get(0).getGalleryList().add(length - 1, gallery);
                        if (mUploadUrlList.size() != 0) {
                            Log.d(TAG, "onActivityResult: " + new File(p).length());
                            Compressor.getDefault(this)
                                    .compressToFileAsObservable(new File(p))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<File>() {
                                        @Override
                                        public void call(File file) {
                                            Log.d(TAG, "call: " + file.length());
                                            mPhotoAlbumPresenter.submitAvatar(mUploadUrlList.get(i).getUpurl(), file);
                                            imgname += mUploadUrlList.get(i).getImgname() + ",";
                                            i++;
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {

                                        }
                                    });

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
