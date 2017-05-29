package com.aiyaschool.aiya.me.mvpphotoAlbum;

import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.util.APIUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wewarriors on 2017/5/28.
 */

public class PhotoAlbumModel implements PhotoAlbumContract.Model {
    @Override
    public void submitAvatar(String url, RequestBody img, Observer<ResponseBody> observer) {
        APIUtil.getIMGApi()
                .submitIMG(url, img)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void startPostPhotoImg(String img, Observer<HttpResult> observer) {
        APIUtil.getPostPhotoImgApi()
                .startPostPhotoImg(img)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void getImgUploadUrl(Observer<HttpResult<ArrayList<UploadUrl>>> observer) {
        APIUtil.getImgUploadUrlApi()
                .startGetImgUploadUrl()
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    @Override
    public void getMePhoto(String page, String lines, Observer<HttpResult<ArrayList<Gallery>>> observer) {
        APIUtil.getMePhotoApi()
                .startGetMePhoto(page, lines)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
