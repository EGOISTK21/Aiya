package com.aiyaschool.aiya.me.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.me.activity.PhotoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Woody on 2016/5/12.
 */
public class PhotoFragment extends Fragment {

    private SmoothImageView ivPhoto;
    private ProgressBar progressBar;
    private Rect startBounds;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, null);
        activity = getActivity();
        ivPhoto = (SmoothImageView) rootView.findViewById(R.id.iv_photo);
        ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ivPhoto.setDrawingCacheEnabled(true);
                        Bitmap imageBitmap = ivPhoto.getDrawingCache();
                        if (imageBitmap != null) {
                            new SaveImageTask().execute(imageBitmap);
                        }
                    }
                });
                builder.show();

                return true;
            }
        });
        ivPhoto.setMinimumScale(0.5f);
        ivPhoto.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ((PhotoActivity) activity).transformOut();
            }
        });
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        Bundle args = getArguments();
        if (args != null && args.containsKey("img")) {
            Gallery gallery = (Gallery) args.getSerializable("img");
            startBounds = args.getParcelable("startBounds");
            if (!TextUtils.isEmpty(gallery.getImgid())) {
                File file = DiskCacheUtils.findInCache(gallery.getImg().getThumb(), ImageLoader.getInstance().getDiskCache());
                final boolean showLoading = file == null || !file.exists();
                ivPhoto.setTransformEnabled(!showLoading);
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .resetViewBeforeLoading(false)  // default
                        .cacheOnDisk(true) // default
                        .showImageOnLoading(null) // resource or drawable
                        .showImageForEmptyUri(R.drawable.mis_default_error) // resource or drawable
                        .showImageOnFail(R.drawable.mis_default_error)
                        .build();
                ImageLoader.getInstance().displayImage(gallery.getImg().getNormal(), ivPhoto, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        if (showLoading) {
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        progressBar.setVisibility(View.GONE);
                        ivPhoto.setTransformEnabled(true);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        progressBar.setVisibility(View.GONE);
                        ivPhoto.setTransformEnabled(true);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        progressBar.setVisibility(View.GONE);
                        ivPhoto.setTransformEnabled(true);
                    }
                });
            } else {
                File file = new File(gallery.getImg().getThumb());
                Picasso.with(getActivity())
                        .load(file)
                        .placeholder(R.drawable.mis_default_error)
                        .into(ivPhoto);
            }

        }
        return rootView;
    }


    public void transformIn(SmoothImageView.onTransformListener listener) {
        ivPhoto.transformIn(startBounds, listener);

    }

    public void transformOut(SmoothImageView.onTransformListener listener) {
        ivPhoto.transformOut(startBounds, listener);
    }


    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {

        @Override
        protected String doInBackground(Bitmap... params) {
            String result = getResources().getString(R.string.save_picture_failed);
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();

                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }

                File imageFile = new File(file.getAbsolutePath(), new Date().getTime() + ".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = getResources().getString(R.string.save_picture_success, file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

            ivPhoto.setDrawingCacheEnabled(false);
        }
    }
}
