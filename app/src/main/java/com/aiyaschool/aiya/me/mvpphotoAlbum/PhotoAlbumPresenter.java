package com.aiyaschool.aiya.me.mvpphotoAlbum;

import android.util.Log;

import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataContract;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataModel;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.maybe.MaybeMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wewarriors on 2017/5/28.
 */

public class PhotoAlbumPresenter implements PhotoAlbumContract.Presenter {

    private static final String TAG = "PhotoAlbumPresenter";

    private PhotoAlbumContract.Model mModel;
    private PhotoAlbumContract.View mView;


    public PhotoAlbumPresenter(PhotoAlbumContract.View view) {
        attach(view);
    }

    private void attach(PhotoAlbumContract.View view) {
        mModel = new PhotoAlbumModel();
        mView = view;
    }


    @Override
    public void getImgUploadUrl() {
        mModel.getImgUploadUrl(new Observer<HttpResult<ArrayList<UploadUrl>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull HttpResult<ArrayList<UploadUrl>> arrayListHttpResult) {
                Log.d(TAG, "getImgUploadUrl()onNext: " + arrayListHttpResult);
                if (arrayListHttpResult.getState().equals("2000")) {
                    Log.d(TAG, "getImgUploadUrl()onNext: " + arrayListHttpResult.getData().size());
                    mView.showImgUploadUrl(arrayListHttpResult.getData());
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void submitAvatar(String url, File file) {
        mModel.submitAvatar(url, RequestBody.create(MediaType.parse("image/jpeg"), file), new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: submitAvatar");
            }

            @Override
            public void onNext(@NonNull ResponseBody responseBody) {

                Log.d(TAG, "onNext: submitAvatar");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: submitAvatar" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: submitAvatar");
                mView.startPostPhotoImg();

            }
        });
    }

    @Override
    public void startPostPhotoImg(String img) {
        mModel.startPostPhotoImg(img, new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: startPostPhotoImg");
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.d(TAG, "onNext: startPostPhotoImg" + httpResult.getState());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: startPostPhotoImg" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: startPostPhotoImg" + "complete");

            }
        });
    }

    @Override
    public void getMePhoto(final String page, String lines) {
        mModel.getMePhoto(page, lines, new Observer<HttpResult<ArrayList<Gallery>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: getMePhoto" + page);
            }

            @Override
            public void onNext(@NonNull HttpResult<ArrayList<Gallery>> arrayListHttpResult) {
                Log.d(TAG, "onNext: getMePhoto" + arrayListHttpResult.getState());
                Log.d(TAG, "onNext: getMePhoto" + arrayListHttpResult.getData().size());
                for (Gallery gallery : arrayListHttpResult.getData()) {
                    Log.d(TAG, "onNext: getMePhoto" + gallery.getCreatetime());
                    Log.d(TAG, "onNext: getMePhotoid" + gallery.getImgid());
                    Log.d(TAG, "onNext: getMePhoto" + gallery.getImg().getThumb());
                    Log.d(TAG, "onNext: getMePhoto" + gallery.getImg().getNormal());
                }

                if (arrayListHttpResult.getState().equals("2000")) {
                    mView.showGetMePhoto(arrayListHttpResult.getData());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: getMePhoto" + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void updateImagePathList(String page, String lines) {
        mModel.getMePhoto(page, lines, new Observer<HttpResult<ArrayList<Gallery>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: updateImagePathList");
            }

            @Override
            public void onNext(@NonNull HttpResult<ArrayList<Gallery>> arrayListHttpResult) {
                Log.d(TAG, "onNext: updateImagePathList" + arrayListHttpResult.getState());
                Log.d(TAG, "onNext: updateImagePathList" + arrayListHttpResult.getData().size());
                for (Gallery gallery : arrayListHttpResult.getData()) {
                    Log.d(TAG, "onNext: updateImagePathList" + gallery.getCreatetime());
                    Log.d(TAG, "onNext: updateImagePathList" + gallery.getImgid());
                    Log.d(TAG, "onNext: updateImagePathList" + gallery.getImg().getThumb());
                    Log.d(TAG, "onNext: updateImagePathList" + gallery.getImg().getNormal());
                }

                if (arrayListHttpResult.getState().equals("2000")) {
                    mView.updateImagePathList(arrayListHttpResult.getData());
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: getMePhoto" + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void deletePhoto(final String imgId) {
        mModel.deletePhoto(imgId, new Observer<HttpResult>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: deletePhoto" + imgId);
            }

            @Override
            public void onNext(@NonNull HttpResult httpResult) {
                Log.d(TAG, "onNext: deletePhoto" + httpResult.getState());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: deletePhoto");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: deletePhoto");
            }
        });
    }

    @Override
    public void detach() {
        mView = null;
        mModel = null;
    }
}
