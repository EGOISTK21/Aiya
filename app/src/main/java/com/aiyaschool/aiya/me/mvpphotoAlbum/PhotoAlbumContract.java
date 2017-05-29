package com.aiyaschool.aiya.me.mvpphotoAlbum;

import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.UploadUrl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wewarriors on 2017/5/28.
 */

public interface PhotoAlbumContract {
    interface Model {

        void submitAvatar(String url, RequestBody img, Observer<ResponseBody> observer);

        void startPostPhotoImg(String img, Observer<HttpResult> observer);

        void getImgUploadUrl(Observer<HttpResult<ArrayList<UploadUrl>>> observer);

        void getMePhoto(String page, String lines, Observer<HttpResult<ArrayList<Gallery>>> observer);
    }

    interface View {
        void showImgUploadUrl(List<UploadUrl> uploadUrlList);

        void startPostPhotoImg();
    }

    interface Presenter {

        void getImgUploadUrl();

        void submitAvatar(String url, File file);

        void startPostPhotoImg(String img);

        void getMePhoto(String page, String lines);

        void detach();
    }
}
